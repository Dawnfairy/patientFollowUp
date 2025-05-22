package com.ft.patientFollowUp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    /**
     * "ROLE_PATIENT" veya "ROLE_DOCTOR" olarak gönderilmeli.
     */
    @NotBlank
    private String role;

    @Valid
    private PatientDto patientInfo;

    @Valid
    private DoctorDto doctorInfo;
}