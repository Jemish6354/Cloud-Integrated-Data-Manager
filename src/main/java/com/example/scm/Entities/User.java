package com.example.scm.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
// import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotBlank(message = "Name field is required")
    @Size(min=3, max=20, message = "character limit is between 3 to 20 (both include)")
    private String name;

    @Column(unique = true)
    private String email;
    private String emailVarifird;
    private String password;
    private String role;
    private boolean enabled;
    private String imageUrl;

    private Providers provider;
    private String providerUserId;

    @Column(length = 500)
    private String about;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();





    
}
