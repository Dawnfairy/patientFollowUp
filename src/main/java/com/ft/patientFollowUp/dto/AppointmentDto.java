package com.ft.patientFollowUp.dto;

import com.ft.patientFollowUp.model.Appointment;
import com.ft.patientFollowUp.model.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDto {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;

    public static AppointmentDto from(Appointment ap) {
        return AppointmentDto.builder()
                .id(ap.getId())
                .doctorId(ap.getDoctor().getId())
                .patientId(ap.getPatient() != null ? ap.getPatient().getId() : null)
                .appointmentTime(ap.getAppointmentTime())
                .status(ap.getStatus())
                .build();
    }
}

