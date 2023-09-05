package com.kigen.retail_store.services.user;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kigen.retail_store.dtos.contact.ContactDTO;
import com.kigen.retail_store.dtos.general.PageDTO;
import com.kigen.retail_store.dtos.user.UserAddressDTO;
import com.kigen.retail_store.dtos.user.UserDTO;
import com.kigen.retail_store.dtos.user.UserRoleDTO;
import com.kigen.retail_store.exceptions.NotFoundException;
import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.status.EStatus;
import com.kigen.retail_store.models.user.EContact;
import com.kigen.retail_store.models.user.ERole;
import com.kigen.retail_store.models.user.EUser;
import com.kigen.retail_store.models.user.EUserAddress;
import com.kigen.retail_store.models.user.EUserRole;
import com.kigen.retail_store.repositories.user.UserDAO;
import com.kigen.retail_store.services.client.IClient;
import com.kigen.retail_store.services.contact.IContact;
import com.kigen.retail_store.services.role.IRole;
import com.kigen.retail_store.services.status.IStatus;
import com.kigen.retail_store.specifications.SpecBuilder;
import com.kigen.retail_store.specifications.SpecFactory;

@Service
public class SUser implements IUser {

    Logger logger = LoggerFactory.getLogger(SUser.class);

    @Value(value = "${default.value.status.active-id}")
    private Integer activeStatusId;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private IClient sClient;

    @Autowired
    private IContact sContact;

    @Autowired
    private IRole sRole;

    @Autowired
    private IStatus sStatus;

    @Autowired
    private IUserAddress sUserAddress;

    @Autowired
    private IUserRole sUserRole;

    @Autowired
    private SpecFactory specFactory;

    @Override
    public EUser create(UserDTO userDTO) {
        EUser user = new EUser();
        user.setAccountNumber(generateAccountNumber());
        setClient(user, userDTO.getClientId());
        user.setCreatedOn(LocalDateTime.now());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setPassword(userDTO.getPassword());

        Integer statusId = userDTO.getStatusId() == null ? activeStatusId : userDTO.getStatusId();
        setStatus(user, statusId);

        save(user);

        setContacts(user, userDTO.getContacts());
        setUserAddresses(user, userDTO.getUserAddresses());
        setUserRoles(user, userDTO.getUserRoles());

        return user;
    }

    private String generateAccountNumber() {
        return String.format("%s", String.valueOf((int) (Math.random() * 1_000_000 - 100_000 + 1) + 100_000));
    }

    @Override
    public Optional<EUser> getByContactValue(String contactValue) {
        return userDAO.findByContactValue(contactValue);
    }

    @Override
    public Optional<EUser> getById(Integer userId) {
        return userDAO.findById(userId);
    }

    @Override
    public Optional<EUser> getByIdOrContactValue(String userValue) {

        Integer userId;
        try {
            userId = Integer.valueOf(userValue);
        } catch (NumberFormatException ex) {
            userId = (Integer) null;
            logger.info("\nERROR: [SUser.getByUserIdOrContactValue]\n[MSG] {}", ex.getMessage());
            return getByContactValue(userValue);
        }

        return userDAO.findByIdOrContactValue(userId, userValue);
    }

    @Override
    public EUser getByIdOrContactValue(String userValue, Boolean handleException) {
        Optional<EUser> user = getByIdOrContactValue(userValue);
        if (!user.isPresent() && handleException) {
            throw new NotFoundException("user with specified id or contact value not found", "userValue");
        }
        return user.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<EUser> getPaginatedList(PageDTO pageDTO, List<String> allowedFields) {

        String search = pageDTO.getSearch();

        SpecBuilder<EUser> specBuilder = new SpecBuilder<>();

        specBuilder = (SpecBuilder<EUser>) specFactory.generateSpecification(search, specBuilder, allowedFields);

        Specification<EUser> spec = specBuilder.build();

        PageRequest pageRequest = PageRequest.of(pageDTO.getPageNumber(), pageDTO.getPageSize(), 
            Sort.by(pageDTO.getDirection(), pageDTO.getSortVal()));

        return userDAO.findAll(spec, pageRequest);
    }

    @Override
    public void save(EUser user) {
        userDAO.save(user);
    }

    private void setClient(EUser user, Integer clientId) {
        if (clientId != null) {
            EClient client = sClient.getById(clientId, true);
            user.setClient(client);
        }
    }

    private void setContacts(EUser user, List<ContactDTO> contactList) {
        
        if (contactList != null || !contactList.isEmpty()) {
            List<EContact> contacts = new ArrayList<>();
            for (ContactDTO contactDTO : contactList) {
                EContact contact = sContact.create(user, contactDTO);
                contacts.add(contact);
            }
            user.setContacts(contacts);
        }
    }

    private void setStatus(EUser user, Integer statusId) {
        
        if (statusId != null) {
            EStatus status = sStatus.getById(statusId, true);
            user.setStatus(status);
        }
    }

    private void setUserAddresses(EUser user, List<UserAddressDTO> userAddresses) {

        if (userAddresses != null) {
            List<EUserAddress> userAddressesList = new ArrayList<>();
            for (UserAddressDTO userAddressDTO : userAddresses) {
                EUserAddress userAddress = sUserAddress.create(user, userAddressDTO);
                // If the address is marked as default remove the previous default address
                if (userAddressDTO.getIsDefault()) {
                    sUserAddress.removeDefault(user.getId());
                }
                userAddressesList.add(userAddress);
            }
            user.setUserAddresses(userAddressesList);
        }
    }

    private void setUserRoles(EUser user, List<UserRoleDTO> userRoles) {
        
        if (userRoles != null) {
            List<EUserRole> userRolesList = new ArrayList<>();
            for (UserRoleDTO userRoleDTO : userRoles) {
                ERole role = sRole.getById(userRoleDTO.getRoleId(), true);
                EUserRole userRole = sUserRole.create(user, role);
                userRolesList.add(userRole);
            }
            user.setUserRoles(userRolesList);
        }
    }

    @Override
    public EUser update(String userValue, UserDTO userDTO) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        EUser user = getByIdOrContactValue(userValue, true);

        String[] fields = {"FirstName", "LastName", "MiddleName", "Password"};
        for (String field : fields) {
            Method getField = UserDTO.class.getMethod(String.format("get%s", field));
            Object fieldValue = getField.invoke(userDTO);

            if (fieldValue != null) {
                fieldValue = fieldValue.getClass().equals(String.class) ? ((String) fieldValue).trim() : fieldValue;
                EUser.class.getMethod("set" + field, fieldValue.getClass()).invoke(user, fieldValue);
            }
        }

        setClient(user, userDTO.getClientId());
        setContacts(user, userDTO.getContacts());
        user.setModifiedOn(LocalDateTime.now());
        setStatus(user, userDTO.getStatusId());
        setUserAddresses(user, userDTO.getUserAddresses());
        setUserRoles(user, userDTO.getUserRoles());

        save(user);
        return user;
    }
    
}
