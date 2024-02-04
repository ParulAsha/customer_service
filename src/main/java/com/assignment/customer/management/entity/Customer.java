package com.assignment.customer.management.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
//@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String companyName;
    private String industryType;
    private String customerStatus;
    private String accountManager;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public Customer() {

    }


    public Customer(String name, String email, String phone, String address, String companyName,
                    String industryType, String customerStatus, String accountManager) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.companyName = companyName;
        this.industryType = industryType;
        this.customerStatus = customerStatus;
        this.accountManager = accountManager;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


}
