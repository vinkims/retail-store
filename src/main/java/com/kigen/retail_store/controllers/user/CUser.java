package com.kigen.retail_store.controllers.user;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.user.UserDTO;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.responses.SuccessPaginatedResponse;
import com.kigen.retail_store.responses.SuccessResponse;
import com.kigen.retail_store.services.user.IUser;

@RestController
@RequestMapping(path ="/user")
public class CUser {
    
    @Autowired
    private IUser sUser;

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {

        EUser user = sUser.create(userDTO);

        return ResponseEntity
            .created(new URI("/" + user.getId()))
            .body(new SuccessResponse(200, "successfully created user", new UserDTO(user)));
    }

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<SuccessPaginatedResponse> getUsersList(@RequestParam Map<String, Object> params) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            NoSuchMethodException, SecurityException {

        PageDTO pageDTO = new PageDTO(params);

        List<String> allowedFields = new ArrayList<>(Arrays.asList("accountNumber", "firstName", "client.id", 
            "createdOn", "lastActiveOn", "middleName", "modifiedOn", "status.id"));

        Page<EUser> users = sUser.getPaginatedList(pageDTO, allowedFields);

        return ResponseEntity
            .ok()
            .body(new SuccessPaginatedResponse(200, "successfully fetched users", users, 
                UserDTO.class, EUser.class));
    }

    @GetMapping(path = "/{userValue}", produces = "application/json")
    public ResponseEntity<SuccessResponse> getUser(@PathVariable String userValue) {

        EUser user = sUser.getByIdOrContactValue(userValue, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully fetched user", new UserDTO(user)));
    }

    @PatchMapping(path = "/{userValue}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SuccessResponse> updateUser(@PathVariable String userValue, @RequestBody UserDTO userDTO) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EUser user = sUser.getByIdOrContactValue(userValue, true);

        return ResponseEntity
            .ok()
            .body(new SuccessResponse(200, "successfully updated user", new UserDTO(user)));
    }
}
