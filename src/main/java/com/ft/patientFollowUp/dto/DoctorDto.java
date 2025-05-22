package com.ft.patientFollowUp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    /** Örneğin branş (cardiology, dermatology vs.) */
    @NotBlank
    private String specialization;


}