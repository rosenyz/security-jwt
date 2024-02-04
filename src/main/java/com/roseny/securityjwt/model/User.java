package com.roseny.securityjwt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "email_confirmed")
    private Boolean emailConfirmed;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @PrePersist
    private void init() {
        this.emailConfirmed = false;
    }
}
