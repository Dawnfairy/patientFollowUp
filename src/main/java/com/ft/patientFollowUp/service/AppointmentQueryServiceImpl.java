package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.*;
import com.ft.patientFollowUp.repository.AppointmentRepository;
import com.ft.patientFollowUp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentQueryServiceImpl implements AppointmentQueryService{
    private final AppointmentRepository repo;
    private final PatientRepository patientRepo;

    @Override
    public List<Appointment> getAvailableAppointments() {
        return repo.findByStatus(AppointmentStatus.AVAILABLE);
    }

    @Override
    public List<Appointment> getMyRequestedAppointments(AppUser user) {
        Patient p = patientRepo.findByUser(user).orElseThrow();
        return repo.findByPatient(p);
    }

    @Override
    public List<Appointment> getPendingRequestsForDoctor(Doctor doctor) {
        return repo.findByDoctorAndStatus(doctor, AppointmentStatus.PENDING);
    }

    @Override
    public List<Appointment> getAllAppointmentsForDoctor(Doctor doctor) {
        return repo.findByDoctor(doctor);
    }
}
