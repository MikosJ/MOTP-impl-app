package com.example.motpapp.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    String username;
    String password;
    String secretKey;
    int validationCode;
}