package com.quickbite.auth_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data   // Lombok generates getters, setters, toString automatically
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment ID
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    @Column(unique = true)   // no two users with same email
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String role = "CUSTOMER";   // default role
}