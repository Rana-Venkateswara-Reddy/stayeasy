package com.stayeasy.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new Random();

    public String generateOtp(String email) {
        String otp = String.valueOf(100000 + random.nextInt(900000)); // 6-digit OTP
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
}

