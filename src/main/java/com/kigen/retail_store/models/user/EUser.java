package com.kigen.retail_store.models.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kigen.retail_store.models.client.EClient;
import com.kigen.retail_store.models.status.EStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class EUser implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "account_number")
    private String accountNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EContact> contacts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private EClient client;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "first_name")
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "last_active_on")
    private LocalDateTime lastActiveOn;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EUserRole> userRoles;

    public void setPassword(String passcode) {
        if (passcode != null) {
            this.password = new BCryptPasswordEncoder().encode(passcode);
        }
    }
}
