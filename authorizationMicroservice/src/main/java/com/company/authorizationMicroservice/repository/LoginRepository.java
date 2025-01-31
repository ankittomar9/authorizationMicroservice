package com.company.authorizationMicroservice.repository;

import com.company.authorizationMicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}

//Repository Annotation:
//@Repository
//This annotation indicates that the interface is a Spring Data repository, which will be managed by the Spring container.

//Interface Declaration:
//public interface LoginRepository extends JpaRepository<User, Integer> {
//This declares the LoginRepository interface, which extends JpaRepository.
// The generic parameters <User, Integer> specify that this repository manages User entities with Integer as the type of their primary key.
//Custom Query Method:
//User findByUsername(String username);
//This method declaration allows Spring Data JPA to automatically generate a query to find a User entity by its username field.
//In summary, the LoginRepository interface provides CRUD operations and a custom method to find users by their username,
// leveraging Spring Data JPA's capabilities. This repository will be used by your services to interact with the database seamlessly