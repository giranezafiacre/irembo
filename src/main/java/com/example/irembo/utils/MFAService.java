package com.example.irembo.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class MFAService {
    public String generateMFACode() {
        return String.valueOf(new Random().nextInt(9999) + 1000);
    }
}
