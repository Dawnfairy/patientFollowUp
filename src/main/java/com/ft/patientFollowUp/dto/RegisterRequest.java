package com.ft.patientFollowUp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private PatientDto patientInfo;
    private DoctorDto doctorInfo;
}