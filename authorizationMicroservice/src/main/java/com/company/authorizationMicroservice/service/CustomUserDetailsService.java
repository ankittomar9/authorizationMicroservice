package com.company.authorizationMicroservice.service;

import com.company.authorizationMicroservice.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.authorizationMicroservice.model.User user =loginRepository.findByUsername(username);

        if(user == null) {
            // to be deleted
            System.out.println("user not found : " + username);
            throw new UsernameNotFoundException("User not found : ");

        }
        System.out.println("user  Found : " + user.getUsername());
        return new User( user.getUsername(), user.getPassword(), new ArrayList<>());
    }

}
