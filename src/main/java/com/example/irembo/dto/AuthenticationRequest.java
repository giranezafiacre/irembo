package com.example.irembo.dto;

import java.time.LocalDate;

import com.example.irembo.domain.Gender;
import com.example.irembo.domain.MaritalStatus;
import com.example.irembo.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String profilePhoto;
    private MaritalStatus maritalStatus;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String nationality;
    private Role role;
    private String email;
    private String password;
}
