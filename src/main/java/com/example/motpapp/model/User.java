package com.example.motpapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userTable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long userId;
    String username;
    String password;
    String secretKey;
    int validationCode;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<ScratchCode> scratchCodes;
};