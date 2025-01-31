package com.company.authorizationMicroservice.filter;
import com.company.authorizationMicroservice.util.JWTUtil;
import com.company.authorizationMicroservice.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //get jwt  // Bearer // Validate
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            }catch (Exception e) {
                e.printStackTrace();
            }
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            // security
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                System.out.println("Token is not validated !!");
            }

        }

        filterChain.doFilter(request, response);
    }

}
/*
* Sure! Here's a breakdown of what your `JWTAuthenticationFilter` class does:
### Package and Imports
- `package com.company.authorizationMicroservice.filter;`: Defines the package for the class.
- `import` statements: Imports necessary classes, including those for handling JWTs, security, and HTTP requests.

### Class Definition
- `@Component`: Marks the class as a Spring component, making it a Spring-managed bean.
- `public class JWTAuthenticationFilter extends OncePerRequestFilter`: Extends `OncePerRequestFilter` to ensure the filter is executed once per request.

### Autowired Components
- `@Autowired private JWTUtil jwtUtil;`: Injects the `JWTUtil` component, which is responsible for handling JWT-related operations.
- `@Autowired private CustomUserDetailsService customUserDetailsService;`: Injects the `CustomUserDetailsService`, which loads user details.

### doFilterInternal Method
This method is overridden to provide the core logic of the filter:

1. **Retrieve JWT from Request Header:**
   ```java
   String requestTokenHeader = request.getHeader("Authorization");
   ```
   Retrieves the JWT from the `Authorization` header.

2. **Validate JWT:**
   ```java
   if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
       jwtToken = requestTokenHeader.substring(7);
       try {
           username = jwtUtil.getUsernameFromToken(jwtToken);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   ```
   Checks if the header contains a JWT and validates it by extracting the username.

3. **Load User Details:**
   ```java
   UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
   ```
   Loads user details using the username extracted from the JWT.

4. **Set Authentication:**
   ```java
   if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
           userDetails, null, userDetails.getAuthorities());
       usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
       SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
   } else {
       System.out.println("Token is not validated !!");
   }
   ```
   If the user is not authenticated, it creates an `UsernamePasswordAuthenticationToken` and sets it in the security context.

5. **Continue Filter Chain:**
   ```java
   filterChain.doFilter(request, response);
   ```
   Proceeds with the filter chain, allowing the request to continue to the next filter or endpoint.

In essence, this filter checks for a JWT token in the request header, validates it, loads the associated user details, and sets the authentication context if the token is valid. This allows secured endpoints to recognize the user based on the JWT.

If you have any specific questions or need more details, feel free to ask!*/