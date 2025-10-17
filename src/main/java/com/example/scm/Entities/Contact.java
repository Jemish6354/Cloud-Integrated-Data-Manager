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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    
    private String name;
    private String secondName;
    private String email;
    private String work;
    private String phone;
    private String image;

    @Column(length = 500)
    private String description;


    @ManyToOne
    private User user;
    
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contact", orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();

    
}
