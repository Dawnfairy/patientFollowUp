package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.dto.PatientDto;
import com.ft.patientFollowUp.model.*;
import com.ft.patientFollowUp.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepo;

    @Override
    public void createPatient(AppUser user, PatientDto info) {
        if (info == null) {
            throw new IllegalArgumentException("Hasta rolü için patientInfo gerekli");
        }
        Patient p = Patient.builder()
                .user(user)
                .firstName(info.getFirstName())
                .lastName(info.getLastName())
                .email(info.getEmail())
                .phone(info.getPhone())
                .build();
        patientRepo.save(p);
    }
}
