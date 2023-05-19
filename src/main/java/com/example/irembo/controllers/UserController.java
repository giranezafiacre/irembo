package com.example.irembo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.irembo.domain.Verification;
import com.example.irembo.dto.AuthenticationRequest;
import com.example.irembo.dto.AuthenticationResponse;
import com.example.irembo.dto.DocumentDTO;
import com.example.irembo.dto.LoginRequest;
import com.example.irembo.dto.OTPRequest;
import com.example.irembo.dto.ResponseLogin;
import com.example.irembo.dto.VerifyUserDto;
import com.example.irembo.services.UserService;
import com.example.irembo.services.VerificationService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RestController
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/auth/create")
    public AuthenticationResponse createUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return userService.createUser(authenticationRequest);
    }

    @PostMapping("/auth/login")
    public ResponseLogin authenticate(@RequestBody LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }

    @PostMapping("/auth/otp")
    public AuthenticationResponse verifyOTP(@RequestBody OTPRequest otpRequest) {
        return userService.verifyOTP(otpRequest.getEmail(), otpRequest.getOtp());
    }

    @PostMapping("/verification/documents")
    public Verification submitDocuments(@RequestBody DocumentDTO documentDTO) {
        return verificationService.submitDocuments(documentDTO.getNationalID(), documentDTO.getDocumentPhoto());
    }

    @GetMapping("/verification/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Verification> getAllDocumentsSubmitted() {
        return verificationService.findAllPendingDocuments();
    }

    @PostMapping("/verification/verify")
    @PreAuthorize("hasAuthority('ADMIN')")
    public AuthenticationResponse verifyUser(@RequestBody VerifyUserDto userDto) {
        return userService.verifyUser(userDto.getUserID());
    }

    @PostMapping("/auth/admin")
    public AuthenticationResponse createAdmin(@RequestBody AuthenticationRequest
                                                      authenticationRequest) {
        return userService.createAdmin(authenticationRequest);
    }
}
