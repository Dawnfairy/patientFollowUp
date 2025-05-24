package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.Appointment;
import com.ft.patientFollowUp.model.AppointmentStatus;
import com.ft.patientFollowUp.model.Doctor;
import com.ft.patientFollowUp.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AppointmentGenerationServiceImpl implements AppointmentGenerationService {
    private final AppointmentRepository repo;

    @Override
    public void generateDefaultAppointmentsForDoctor(Doctor doctor) {
        LocalDate today = LocalDate.now();
        for (int d = 0; d < 5; d++) {
            LocalDate date = today.plusDays(d);
            for (int h = 9; h < 17; h++) {
                repo.save(Appointment.builder()
                        .doctor(doctor)
                        .appointmentTime(date.atTime(h, 0))
                        .status(AppointmentStatus.AVAILABLE)
                        .build());
            }
        }
    }
}
