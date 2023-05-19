package com.example.irembo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.irembo.domain.User;
import com.example.irembo.domain.Verification;
import com.example.irembo.domain.VerificationStatus;
import com.example.irembo.repository.UserRepository;
import com.example.irembo.repository.VerificationRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private UserRepository userRepository;

    public Verification submitDocuments(String nationalID, String documentPhoto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getName());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(userDetails.getUsername()).get();

            Verification verification = Verification.builder().documentPhoto(documentPhoto).nationalID(nationalID)
                    .user(user)
                    .build();
            user.setVerificationStatus(VerificationStatus.PENDING_VERFICATION);
            userRepository.save(user);

            return verificationRepository.save(verification);
        }
        return new Verification();

    }

    public List<Verification> findAllPendingDocuments() {
        return verificationRepository.findAll();
    }

}
