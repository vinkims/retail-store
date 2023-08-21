package com.kigen.retail_store.dtos.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kigen.retail_store.dtos.client.ClientDTO;
import com.kigen.retail_store.dtos.contact.ContactDTO;
import com.kigen.retail_store.dtos.status.StatusDTO;
import com.kigen.retail_store.models.user.EContact;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {
    
    private Integer id;

    private String accountNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    private List<ContactDTO> contacts;

    private ClientDTO client;

    private Integer clientId;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    private LocalDateTime lastActiveOn;

    private List<UserRoleDTO> userRoles;

    private String password;

    private StatusDTO status;

    private Integer statusId;

    public UserDTO(EUser user) {
        setAccountNumber(user.getAccountNumber());
        if (user.getClient() != null) {
            setClient(new ClientDTO(user.getClient()));
        }
        setContactsData(user.getContacts());
        setCreatedOn(user.getCreatedOn());
        setFirstName(user.getFirstName());
        setId(user.getId());
        setLastActiveOn(user.getLastActiveOn());
        setLastName(user.getLastName());
        setMiddleName(user.getMiddleName());
        setModifiedOn(user.getModifiedOn());
        if (user.getStatus() != null) {
            setStatus(new StatusDTO(user.getStatus()));
        }
        setUserRolesData(user.getUserRoles());
    }

    private void setContactsData(List<EContact> contactList) {
        if (contactList == null || contactList.isEmpty()) { return; }

        contacts = new ArrayList<>();
        for (EContact contact : contactList) {
            contacts.add(new ContactDTO(contact));
        }
    }

    private void setUserRolesData(List<EUserRole> userRolesList) {
        if (userRolesList == null || userRolesList.isEmpty()) { return; }

        userRoles = new ArrayList<>();
        for (EUserRole userRole : userRolesList) {
            userRoles.add(new UserRoleDTO(userRole));
        }
    } 
}
