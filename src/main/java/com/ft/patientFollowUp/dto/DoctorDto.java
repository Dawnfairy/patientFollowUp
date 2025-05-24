package com.ft.patientFollowUp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDto {
    private String firstName;
    private String lastName;
    private String specialization;
    private String email;
    private String phone;
}