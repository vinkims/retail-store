package com.kigen.retail_store.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.kigen.retail_store.services.auth.IUserDetails;
import com.kigen.retail_store.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value(value = "${default.value.logging.allowed-methods}")
    private List<String> allowedMethods;

    @Value(value = "${default.value.logging.request}")
    private Boolean logRequest;

    @Value(value = "${default.value.logging.response}")
    private Boolean logResponse;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserDetails sUserDetails;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");

        String contactValue = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            contactValue = jwtUtil.extractUsername(jwt);
        }

        // Removes authentication info for each request
        SecurityContextHolder.getContext().setAuthentication(null);

        if (contactValue != null) {

            UserDetails userDetails = sUserDetails.loadUserByUsername(contactValue);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequestResponse(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    public void logRequestResponse(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) 
            throws UnsupportedEncodingException {

        String method = requestWrapper.getMethod();
        if (logRequest && allowedMethods.contains(method)) {
            byte[] requestArray = requestWrapper.getContentAsByteArray();
            String requestStr = new String(requestArray, requestWrapper.getCharacterEncoding());
            log.info("REQUEST:\n[ENDPOINT] - {} {}\n[PAYLOAD] - {}", requestWrapper.getRequestURI(), requestWrapper.getMethod(), requestStr);
        }

        if (logResponse && allowedMethods.contains(method)) {
            byte[] responseArray = responseWrapper.getContentAsByteArray();
            String responseStr = new String(responseArray, responseWrapper.getCharacterEncoding());
            log.info("RESPONSE:\n[PAYLOAD] - {}", responseStr);
        }
    }
    
}
