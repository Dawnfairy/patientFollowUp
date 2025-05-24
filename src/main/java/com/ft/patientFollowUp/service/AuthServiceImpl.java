package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.dto.*;
import com.ft.patientFollowUp.model.*;
import com.ft.patientFollowUp.repository.AppUserRepository;
import com.ft.patientFollowUp.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentGenerationService generationService;

    @Override
    public void register(RegisterRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten alınmış");
        }

        Role role;
        try {
            role = Role.valueOf(req.getRole());
        } catch (Exception e) {
            throw new IllegalArgumentException("Rol ya ROLE_PATIENT ya da ROLE_DOCTOR olmalı");
        }

        AppUser user = AppUser.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();
        userRepo.save(user);

        if (role == Role.ROLE_PATIENT) {
            patientService.createPatient(user, req.getPatientInfo());
        } else if (role == Role.ROLE_DOCTOR) {
            Doctor doc = doctorService.createDoctor(user, req.getDoctorInfo());
            // doktor kayıtlandıysa default appointment’ları üret
            generationService.generateDefaultAppointmentsForDoctor(doc);
        }
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        // AuthenticationManager’dan geçmezse exception fırlatır
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String token = jwtUtil.generateToken(req.getUsername());
        return new LoginResponse(token);
    }
}
