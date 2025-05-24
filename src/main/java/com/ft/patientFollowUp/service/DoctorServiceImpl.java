package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.dto.DoctorDto;
import com.ft.patientFollowUp.model.*;
import com.ft.patientFollowUp.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepo;

    @Override
    public Doctor createDoctor(AppUser user, DoctorDto info) {
        if (info == null) {
            throw new IllegalArgumentException("Doktor rolü için doctorInfo gerekli");
        }
        Doctor d = Doctor.builder()
                .user(user)
                .firstName(info.getFirstName())
                .lastName(info.getLastName())
                .specialization(info.getSpecialization())
                .email(info.getEmail())
                .phone(info.getPhone())
                .build();
        return doctorRepo.save(d);
    }
}
