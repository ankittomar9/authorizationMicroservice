package com.company.authorizationMicroservice.controller;

import com.company.authorizationMicroservice.models.User;
import com.company.authorizationMicroservice.service.CustomUserDetailsService;
import com.company.authorizationMicroservice.util.JWTUtil;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    private User   userDetails;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody User authenticationRequest) throws Exception {

        // to be deleted
        System.out.println(authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        } catch (UsernameNotFoundException e) {
            //e.printStackTrace();
            throw new Exception("Incorrect username or password", e);
        } catch (BadCredentialsException e) {
            //e.printStackTrace();
            throw new Exception("Incorrect username or password", e);
        }

        userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        // to be deleted
        System.out.println("JWT Token: " + token);

        return ResponseEntity.ok(new JWTResponse(token));
    }

    @PostMapping("/validate")
    public Boolean validateToken(@RequestBody String token) {
        return jwtUtil.validateToken(token, userDetails);
    }

}

/*
* Sure! Here's a detailed explanation of your `JWTController` class.

### Package and Imports
- `package com.company.authorizationMicroservice.controller;`: Defines the package for the class.
- `import` statements: Imports necessary classes for handling user models, services, JWT utilities, authentication, and Spring REST functionality.

### Class Definition
- `@RestController`: Marks the class as a REST controller, which means it's ready for use by Spring MVC to handle web requests.

### Autowired Components
- `@Autowired CustomUserDetailsService customUserDetailsService;`: Injects the `CustomUserDetailsService` which loads user details.
- `@Autowired private JWTUtil jwtUtil;`: Injects the `JWTUtil` component for handling JWT-related operations.
- `@Autowired AuthenticationManager authenticationManager;`: Injects the `AuthenticationManager` for performing authentication operations.

### Instance Variables
- `private User userDetails;`: An instance variable to store user details.

### generateToken Method
This method handles the generation of JWT tokens:
- `@PostMapping("/authenticate")`: Maps HTTP POST requests to the `/authenticate` endpoint.
- `public ResponseEntity<?> generateToken(@RequestBody User authenticationRequest) throws Exception`: This method accepts a `User` object containing the authentication request and returns a `ResponseEntity` with a JWT token.

1. **Logging User Credentials:**
   ```java
   System.out.println(authenticationRequest.getUsername() + " " + authenticationRequest.getPassword());
   ```
   Logs the username and password (this should be removed in production for security reasons).

2. **Authenticate User:**
   ```java
   try {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
           authenticationRequest.getUsername(), authenticationRequest.getPassword()));
   } catch (UsernameNotFoundException e) {
       throw new Exception("Incorrect username or password", e);
   } catch (BadCredentialsException e) {
       throw new Exception("Incorrect username or password", e);
   }
   ```
   Attempts to authenticate the user using the provided credentials. Throws an exception if authentication fails.

3. **Load User Details:**
   ```java
   userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
   ```
   Loads user details from the `CustomUserDetailsService`.

4. **Generate JWT Token:**
   ```java
   String token = jwtUtil.generateToken(userDetails);
   System.out.println("JWT Token: " + token);
   ```
   Generates a JWT token for the authenticated user and logs it (again, should be removed in production).

5. **Return JWT Token:**
   ```java
   return ResponseEntity.ok(new JWTResponse(token));
   ```
   Returns the generated JWT token in the response.

### validateToken Method
This method handles the validation of JWT tokens:
- `@PostMapping("/validate")`: Maps HTTP POST requests to the `/validate` endpoint.
- `public Boolean validateToken(@RequestBody String token)`: This method accepts a JWT token as a request body and returns a Boolean indicating whether the token is valid.

```java
return jwtUtil.validateToken(token, userDetails);
```
Validates the provided token using the `JWTUtil` and the stored `userDetails`.

### Summary
- The `generateToken` method authenticates the user and generates a JWT token.
- The `validateToken` method validates the provided JWT token.

Let me know if you have any specific questions or need further details!*/