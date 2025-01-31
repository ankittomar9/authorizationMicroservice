package com.company.authorizationMicroservice.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 30;
    @Value("${jwt.secret}")
    private String secret;

    public void setSecret(String key) {
        this.secret = key;
    }

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}


/*
* Sure! Here's an explanation of the code:

This code defines a utility class called `JWTUtil` for handling JSON Web Tokens (JWT) in your authorization microservice. This class includes methods for generating, validating, and parsing JWT tokens. Let's break it down:

1. **Package and Imports**:
   ```java
   package com.authorizationMicroservice.util;

   import java.util.Date;
   import java.util.HashMap;
   import java.util.Map;
   import java.util.function.Function;

   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.security.core.userdetails.UserDetails;
   import org.springframework.stereotype.Service;

   import io.jsonwebtoken.Claims;
   import io.jsonwebtoken.Jwts;
   import io.jsonwebtoken.SignatureAlgorithm;
   ```
   The necessary classes for JWT handling, Spring annotations, and Java utilities are imported.

2. **Class Annotation**:
   ```java
   @Service
   public class JWTUtil {
   ```
   The `@Service` annotation indicates that this class is a Spring service.

3. **Fields**:
   ```java
   private static final long serialVersionUID = -2550185165626007488L;
   public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 30;

   @Value("${jwt.secret}")
   private String secret;
   ```
   - `serialVersionUID` is used for serialization.
   - `JWT_TOKEN_VALIDITY` defines the token validity period (30 minutes).
   - `secret` is injected from application properties for signing the JWT.

4. **Secret Setter**:
   ```java
   public void setSecret(String key) {
       this.secret = key;
   }
   ```

5. **Retrieving Username from Token**:
   ```java
   public String getUsernameFromToken(String token) {
       return getClaimFromToken(token, Claims::getSubject);
   }
   ```

6. **Retrieving Expiration Date from Token**:
   ```java
   public Date getExpirationDateFromToken(String token) {
       return getClaimFromToken(token, Claims::getExpiration);
   }
   ```

7. **Retrieving Claims from Token**:
   ```java
   public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
       final Claims claims = getAllClaimsFromToken(token);
       return claimsResolver.apply(claims);
   }
   ```

8. **Getting All Claims from Token**:
   ```java
   private Claims getAllClaimsFromToken(String token) {
       return Jwts.parser().setSigningKey(secret).parse
* */




















