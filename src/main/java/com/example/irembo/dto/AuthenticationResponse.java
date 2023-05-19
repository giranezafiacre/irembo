package com.example.irembo.dto;

import java.time.LocalDate;

import com.example.irembo.domain.Gender;
import com.example.irembo.domain.MaritalStatus;
import com.example.irembo.domain.Role;
import com.example.irembo.domain.VerificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String firstname;
    private String lastname;
    private String profilePhoto;
    private MaritalStatus maritalStatus;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String nationality;
    private Boolean isEnabled;
    private String phoneNumber;
    private Role role;
    private String email;
    private VerificationStatus verificationStatus;
    private String token;

}
