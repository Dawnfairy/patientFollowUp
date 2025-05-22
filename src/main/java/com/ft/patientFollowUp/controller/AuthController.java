package com.ft.patientFollowUp.controller;


import com.ft.patientFollowUp.dto.*;
import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Doctor;
import com.ft.patientFollowUp.model.Patient;
import com.ft.patientFollowUp.model.Role;
import com.ft.patientFollowUp.repository.AppUserRepository;
import com.ft.patientFollowUp.repository.DoctorRepository;
import com.ft.patientFollowUp.repository.PatientRepository;
import com.ft.patientFollowUp.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @Autowired PatientRepository patientRepo;
    @Autowired
    DoctorRepository doctorRepo;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Bu kullanıcı adı zaten alınmış");
        }

        Role role;
        try {
            role = Role.valueOf(req.getRole());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Rol ya ROLE_PATIENT ya da ROLE_DOCTOR olmalı");
        }

        // 1) AppUser kaydı
        AppUser user = AppUser.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();
        userRepo.save(user);

        // 2) Eğer hasta rolündeyse, patientInfo’dan verileri alıp kaydet
        if (role == Role.ROLE_PATIENT) {
            var info = req.getPatientInfo();
            if (info == null) {
                return ResponseEntity.badRequest()
                        .body("Hasta rolü için patientInfo boş olamaz");
            }
            Patient patient = Patient.builder()
                    .user(user)
                    .firstName(info.getFirstName())
                    .lastName(info.getLastName())
                    .email(info.getEmail())
                    .phone(info.getPhone())
                    .build();
            patientRepo.save(patient);
        }

        if (role == Role.ROLE_DOCTOR) {
            var info = req.getDoctorInfo();
            if (info == null) {
                return ResponseEntity.badRequest()
                        .body("Doktor rolü için doctorInfo girilmeli");
            }
            Doctor doc = Doctor.builder()
                    .user(user)
                    .firstName(info.getFirstName())
                    .lastName(info.getLastName())
                    .specialization(info.getSpecialization())

                    .build();
            doctorRepo.save(doc);
        }

        return ResponseEntity.ok("Kullanıcı ve hasta kaydı başarıyla oluşturuldu");
    }

    // Mevcut /login endpoint’iniz burada durabilir...
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        authManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        req.getUsername(), req.getPassword()
                )
        );
        String token = jwtUtil.generateToken(req.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}