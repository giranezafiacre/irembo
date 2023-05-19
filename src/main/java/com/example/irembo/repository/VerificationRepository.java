package com.example.irembo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.irembo.domain.Verification;

public interface VerificationRepository extends JpaRepository<Verification, UUID> {
}
