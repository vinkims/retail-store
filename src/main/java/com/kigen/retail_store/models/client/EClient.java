package com.kigen.retail_store.models.client;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.kigen.retail_store.models.EStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "clients")
@Data
@NoArgsConstructor
public class EClient implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "client_code")
    private String clientCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_type_id", referencedColumnName = "id")
    private EClientType clientType;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private EStatus status;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
}
