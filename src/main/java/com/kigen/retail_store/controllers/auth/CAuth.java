package com.kigen.retail_store.controllers.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.auth.AuthDTO;
import com.kigen.retail_store.dtos.auth.SignoutDTO;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.auth.IAuth;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class CAuth {
    
    @Autowired
    private IAuth sAuth;

    @PostMapping(path = "/signin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> authenticateUser(@Valid @RequestBody AuthDTO authDTO) {

        String token = sAuth.authenticateUser(authDTO);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully authenticated user", res));
    }

    @PostMapping(path = "/signout", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> singoutUser(@Valid @RequestBody SignoutDTO signoutDTO) {

        Boolean isSignout = sAuth.signoutUser(signoutDTO);

        Map<String, Object> content = new HashMap<>();
        content.put("signout", isSignout);
        content.put("userId", signoutDTO.getUserId());
        content.put("timestamp", new Date().toString());

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successful signout", content));
    }
}
