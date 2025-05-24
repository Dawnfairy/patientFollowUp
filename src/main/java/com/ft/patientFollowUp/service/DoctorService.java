package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.dto.DoctorDto;
import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Doctor;

public interface DoctorService {
    Doctor createDoctor(AppUser user, DoctorDto info);
}
