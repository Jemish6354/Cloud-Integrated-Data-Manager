package com.example.scm.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.scm.Dao.UserRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // apne user ko load karo
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with emial: " + username));
    }

    
}
