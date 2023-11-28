package com.example.motpapp.model;


import lombok.*;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
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
    String username;
    String password;
    String secretKey;
    int validationCode;
};