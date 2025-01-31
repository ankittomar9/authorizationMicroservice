package com.company.authorizationMicroservice.service;

import com.company.authorizationMicroservice.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service   //Annotation: The @Service annotation indicates that this class is a Spring service, making it a candidate for component scanning and dependency injection.
public class CustomUserDetailsService  implements UserDetailsService {
    // The CustomUserDetailsService class implements the UserDetailsService interface, which requires the implementation of the loadUserByUsername method.

    @Autowired
    private LoginRepository loginRepository;
    //Autowired LoginRepository: The LoginRepository is injected into the CustomUserDetailsService using the @Autowired annotation.
    // This allows the service to use the repository to fetch user details from the database.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.company.authorizationMicroservice.models.User user =loginRepository.findByUsername(username);

        //System.out.println("Users: " + loginRepository.findAll());
        if(user == null) {
            // to be deleted
            System.out.println("user not found : " + username);
            throw new UsernameNotFoundException("User not found : ");
        }
        System.out.println("user  Found : " + user.getUsername());
        return new User( user.getUsername(), user.getPassword(), new ArrayList<>());
        //loadUserByUsername Method: This method is overridden from the UserDetailsService interface. It performs the following steps:
         //Fetches the user from the database using the loginRepository by calling findByUsername.
         //If the user is not found, it prints a message to the console and throws a UsernameNotFoundException.
        //If the user is found, it prints a message to the console and returns a User object with the username, password, and an empty list of authorities.
    }

}
