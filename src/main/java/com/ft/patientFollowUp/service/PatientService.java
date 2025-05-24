package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.dto.PatientDto;
import com.ft.patientFollowUp.model.AppUser;

public interface PatientService {
    void createPatient(AppUser user, PatientDto info);
}
