package com.example.scm.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    
    private String name;

    // @Column(unique = true)
    private String email;
    private String password;

    @Column(length = 500)
    private String about;
    private String profilePic;
    private String phoneNumber;
    
   // Information
   @Builder.Default
    private boolean enabled = false;
    @Builder.Default
    private boolean emailVarified = false;
    @Builder.Default
    private boolean phoneVarified = false;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    // SELF, GOOGLE, FACEBOOK,  TWITTER, LINKEDIN, GITHUB
    private Providers provider = Providers.SELF;
    private String providerUserId;


    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();





    
}
