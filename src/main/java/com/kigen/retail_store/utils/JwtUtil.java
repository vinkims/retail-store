package com.kigen.retail_store.utils;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.services.auth.IBlacklist;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    
    @Value(value = "${default.value.security.secret-key}")
    private String SECRET_KEY;

    @Value(value = "${default.value.security.token-valid-duration}")
    private Integer TOKEN_VALID_PERIOD;

    @Value(value = "${default.value.security.token-valid-secondary-duration}")
    private Integer TOKEN_VALID_SEC_PERIOD;

    @Value(value = "${default.value.user.api-client-name}")
    private String userApiClientName;

    @Autowired
    private IBlacklist sBlacklist;

    private String createToken(Map<String, Object> claims, String subject) {
        Integer token_validity_period = claims.getOrDefault("userRole", "").equals(userApiClientName)
            ? TOKEN_VALID_SEC_PERIOD : TOKEN_VALID_PERIOD;

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * 60L * token_validity_period))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return createToken(claims, userDetails.getUsername());
    }

    private Boolean isTokenBlacklisted(String token) {
        return sBlacklist.checkExistsByToken(sBlacklist.getTokenHash(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }
}
