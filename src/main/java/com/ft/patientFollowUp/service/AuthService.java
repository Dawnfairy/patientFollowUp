package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.dto.LoginRequest;
import com.ft.patientFollowUp.dto.LoginResponse;
import com.ft.patientFollowUp.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest req);
    LoginResponse login(LoginRequest req);
}
