package com.example.scm.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
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
public class User implements UserDetails  {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    
    private String name;

    // @Column(unique = true)
    private String email;
    @Getter(value = AccessLevel.NONE)
    private String password;

    @Column(length = 500)
    private String about;
    private String profilePic;
    private String phoneNumber;
    
   // Information
    @Builder.Default
    @Getter(value = AccessLevel.NONE) // no auto have to do manual
    private boolean enabled = true;
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



    // interface methods

    @ElementCollection (fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //list  of roles[USER, ADMIN]
        //collection of simpleGrantedAuthority[roles{ADMIN, USER}]

        Collection<SimpleGrantedAuthority> rolls = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        
        return rolls;
    }

    //emaill is username for this project
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isEnabled(){
        return this.enabled;
    }







    
}
