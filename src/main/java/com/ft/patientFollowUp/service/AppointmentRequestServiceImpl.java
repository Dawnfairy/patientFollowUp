package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Appointment;
import com.ft.patientFollowUp.model.AppointmentStatus;
import com.ft.patientFollowUp.model.Patient;
import com.ft.patientFollowUp.repository.AppointmentRepository;
import com.ft.patientFollowUp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentRequestServiceImpl implements AppointmentRequestService {
    private final AppointmentRepository repo;
    private final PatientRepository patientRepo;

    @Override
    public Appointment requestAppointment(Long id, AppUser user) {
        Appointment ap = repo.findById(id).orElseThrow();
        if (ap.getStatus() != AppointmentStatus.AVAILABLE) {
            throw new IllegalStateException("Appointment not available");
        }
        Patient p = patientRepo.findByUser(user).orElseThrow();
        ap.setPatient(p);
        ap.setStatus(AppointmentStatus.PENDING);
        return repo.save(ap);
    }
}
