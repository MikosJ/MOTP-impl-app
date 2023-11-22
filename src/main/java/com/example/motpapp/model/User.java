package com.example.motpapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String username;
    String secretKey;
    int validationCode;
    List<Integer> scratchCodes;
};