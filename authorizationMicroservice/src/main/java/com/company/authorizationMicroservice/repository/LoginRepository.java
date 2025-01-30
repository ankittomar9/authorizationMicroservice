package com.company.authorizationMicroservice.repository;

import com.company.authorizationMicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
