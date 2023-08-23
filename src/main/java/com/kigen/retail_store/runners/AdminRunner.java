package com.kigen.retail_store.runners;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kigen.retail_store.dtos.contact.ContactDTO;
import com.kigen.retail_store.dtos.user.UserDTO;
import com.kigen.retail_store.dtos.user.UserRoleDTO;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.services.user.IUser;

@Component
public class AdminRunner implements CommandLineRunner {

    @Value(value = "${default.value.user.admin-email}")
    private String adminEmail;

    @Value(value = "${default.value.user.admin-password}")
    private String adminPassword;

    @Value(value = "${default.value.contact.email-type-id}")
    private Integer emailContactTypeId;

    @Value(value = "${default.value.role.system-admin-id}")
    private Integer systemAdminRoleId;

    @Autowired
    private IUser sUser;

    @Override
    public void run(String... args) throws Exception {

        String firstName = "system";
        String lastName = "admin";

        log.info("\n>>> check if admin user exists")

        Optional<EUser> user = sUser.getByContactValue(adminEmail);
        if (user.isPresent()) {
            log.info("\n<<< Admin user exists");
            return;
        }

        log.info("\n<<< Creating admin user");

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactTypeId(emailContactTypeId);
        contactDTO.setValue(adminEmail);
        List<ContactDTO> contacts = new ArrayList<>();
        contacts.add(contactDTO);

        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRoleId(systemAdminRoleId);
        List<UserRoleDTO> userRoles = new ArrayList<>();
        userRoles.add(userRoleDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setContacts(contacts);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setPassword(adminPassword);
        userDTO.setUserRoles(userRoles);

        EUser admin = sUser.create(userDTO);

        log.info("\nSystem Admin created: id=>{}, name=>{}, email=>{}", 
            admin.getId(),
            String.format("%s %s", firstName, lastName),
            adminEmail);
    }
    
}
