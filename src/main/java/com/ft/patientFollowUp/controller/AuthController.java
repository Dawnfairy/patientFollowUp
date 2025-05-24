package com.ft.patientFollowUp.controller;


import com.ft.patientFollowUp.dto.*;
import com.ft.patientFollowUp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary     = "Yeni kullanıcı kaydı",
            description = "Hasta veya doktor rolünde yeni bir kullanıcı oluşturur."
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("Kayıt başarılı");
    }

    @Operation(
            summary     = "Kullanıcı girişi",
            description = "Geçerli kullanıcı adı/şifre ile giriş yapar ve JWT döner."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        LoginResponse resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }
}