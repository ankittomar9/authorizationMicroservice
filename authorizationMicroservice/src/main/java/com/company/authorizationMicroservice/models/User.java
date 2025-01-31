package com.company.authorizationMicroservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity

public class User {

    @Id
    private int id;
    private String username;
    private String password;

}
