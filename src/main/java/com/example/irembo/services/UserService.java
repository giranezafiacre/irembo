package com.example.irembo.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.irembo.domain.Role;
import com.example.irembo.domain.User;
import com.example.irembo.domain.VerificationStatus;
import com.example.irembo.dto.AuthenticationRequest;
import com.example.irembo.dto.AuthenticationResponse;
import com.example.irembo.dto.LoginRequest;
import com.example.irembo.dto.ResponseLogin;
import com.example.irembo.repository.UserRepository;
import com.example.irembo.utils.JWTService;
import com.example.irembo.utils.MFAService;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MFAService mfaService;

    @Autowired
    private EmailService emailService;

    public AuthenticationResponse createUser(AuthenticationRequest authenticationRequest) {
        var user = User.builder().firstname(authenticationRequest.getFirstname())
                .lastname(authenticationRequest.getLastname()).gender(authenticationRequest.getGender())
                .dateOfBirth(authenticationRequest.getDateOfBirth()).email(authenticationRequest.getEmail())
                .password(passwordEncoder.encode(authenticationRequest.getPassword())).isEnabled(true)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .maritalStatus(authenticationRequest.getMaritalStatus())
                .nationality(authenticationRequest.getNationality()).role(Role.USER)
                .profilePhoto(authenticationRequest.getProfilePhoto())
                .phoneNumber(authenticationRequest.getPhoneNumber()).build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).email(user.getEmail()).firstname(user.getFirstname())
                .lastname(user.getLastname()).isEnabled(user.getIsEnabled())
                .verificationStatus(user.getVerificationStatus()).maritalStatus(user.getMaritalStatus())
                .gender(user.getGender()).nationality(user.getNationality()).role(user.getRole())
                .nationality(user.getNationality()).profilePhoto(user.getProfilePhoto())
                .dateOfBirth(user.getDateOfBirth()).phoneNumber(user.getPhoneNumber()).build();
    }

    public ResponseLogin authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var user = userRepository.findByEmail(loginRequest.getEmail()).get();
        String mfaCode = mfaService.generateMFACode();
        System.out.println(mfaCode);
        user.setMfaCode(mfaCode);
        userRepository.save(user);
        emailService.send2FASMSCode(user.getPhoneNumber(), mfaCode);
        return new ResponseLogin(true, "Code sent to the respective Phone number " + user.getPhoneNumber());

    }

    public AuthenticationResponse verifyOTP(String email, String otp) {
        var user = userRepository.findByEmail(email).get();
        if (user.getMfaCode().equals(otp)) {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).email(user.getEmail())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname()).isEnabled(user.getIsEnabled())
                    .verificationStatus(user.getVerificationStatus()).maritalStatus(user.getMaritalStatus())
                    .gender(user.getGender()).nationality(user.getNationality()).role(user.getRole())
                    .nationality(user.getNationality()).profilePhoto(user.getProfilePhoto())
                    .dateOfBirth(user.getDateOfBirth()).phoneNumber(user.getPhoneNumber()).build();
        } else {
            return new AuthenticationResponse();
        }
    }

    public AuthenticationResponse verifyUser(UUID userId) {
        var user = userRepository.findById(userId).get();
        user.setVerificationStatus(VerificationStatus.VERIFIED);
        userRepository.save(user);
        return AuthenticationResponse.builder().email(user.getEmail()).firstname(user.getFirstname())
                .lastname(user.getLastname()).isEnabled(user.getIsEnabled())
                .verificationStatus(user.getVerificationStatus()).maritalStatus(user.getMaritalStatus())
                .gender(user.getGender()).nationality(user.getNationality()).role(user.getRole())
                .nationality(user.getNationality()).profilePhoto(user.getProfilePhoto())
                .dateOfBirth(user.getDateOfBirth()).phoneNumber(user.getPhoneNumber()).build();
    }

    public AuthenticationResponse createAdmin(AuthenticationRequest authenticationRequest) {
        var user = User.builder().firstname(authenticationRequest.getFirstname())
                .lastname(authenticationRequest.getLastname()).gender(authenticationRequest.getGender())
                .dateOfBirth(authenticationRequest.getDateOfBirth()).email(authenticationRequest.getEmail())
                .password(passwordEncoder.encode(authenticationRequest.getPassword())).isEnabled(true)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .maritalStatus(authenticationRequest.getMaritalStatus())
                .nationality(authenticationRequest.getNationality()).role(Role.ADMIN)
                .profilePhoto(authenticationRequest.getProfilePhoto())
                .phoneNumber(authenticationRequest.getPhoneNumber()).build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).email(user.getEmail()).firstname(user.getFirstname())
                .lastname(user.getLastname()).isEnabled(user.getIsEnabled())
                .verificationStatus(user.getVerificationStatus()).maritalStatus(user.getMaritalStatus())
                .gender(user.getGender()).nationality(user.getNationality()).role(user.getRole())
                .nationality(user.getNationality()).profilePhoto(user.getProfilePhoto())
                .dateOfBirth(user.getDateOfBirth()).phoneNumber(user.getPhoneNumber()).build();
    }
}