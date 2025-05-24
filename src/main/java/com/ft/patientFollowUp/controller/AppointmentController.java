package com.ft.patientFollowUp.controller;

import com.ft.patientFollowUp.dto.AppointmentDto;
import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Doctor;
import com.ft.patientFollowUp.repository.DoctorRepository;
import com.ft.patientFollowUp.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentGenerationService genService;
    private final AppointmentQueryService queryService;
    private final AppointmentRequestService requestService;
    private final AppointmentApprovalService approvalService;
    private final AppUserDetailsService userDetailsService;
    private final DoctorRepository doctorRepo;

    // Doktor kaydolurken AuthController'da çağır:
    // genService.generateDefaultAppointmentsForDoctor(savedDoctor);

    @Operation(
            summary     = "Boş randevuları listele",
            description = "HASTA rolündeki kullanıcıların erişebileceği, tüm AÇIK statüsündeki randevuları döner."
    )
    @GetMapping("/available")
    @PreAuthorize("hasRole('PATIENT')")
    public List<AppointmentDto> available() {
        return queryService.getAvailableAppointments().stream()
                .map(AppointmentDto::from).toList();
    }

    @Operation(
            summary     = "Randevu talebi oluştur",
            description = "HASTA rolündeki kullanıcıların randevu talep ettiği endpoint. " +
                    "ID'si verilen AÇIK randevuyu BEKLEMEDE statüsüne çeker."
    )
    @PostMapping("/{id}/request")
    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentDto request(@PathVariable Long id) {
        AppUser user = userDetailsService.currentUser();
        return AppointmentDto.from(requestService.requestAppointment(id, user));
    }

    @Operation(
            summary     = "Kendi talep edilen randevularını listele",
            description = "HASTA rolündeki kullanıcı, kendi BEKLEMEDE ve ONAYLI randevu taleplerini görebilir."
    )
    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('PATIENT')")
    public List<AppointmentDto> myRequests() {
        AppUser user = userDetailsService.currentUser();
        return queryService.getMyRequestedAppointments(user).stream()
                .map(AppointmentDto::from).toList();
    }

    @Operation(
            summary     = "Doktorun bekleyen taleplerini listele",
            description = "DOKTOR rolündeki kullanıcı için: mevcut doktorun BEKLEMEDE statüsündeki tüm taleplerini döner."
    )
    @GetMapping("/pending")
    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentDto> pending() {
        AppUser user = userDetailsService.currentUser();
        Doctor d = doctorRepo.findByUser(user).orElseThrow();
        return queryService.getPendingRequestsForDoctor(d).stream()
                .map(AppointmentDto::from).toList();
    }

    @Operation(
            summary     = "Doktorun tüm randevularını listele",
            description = "DOKTOR rolündeki kullanıcı: onaylanmış, reddedilmiş veya bekleyen tüm randevularını döner."
    )
    @GetMapping("/mine")
    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentDto> mine() {
        AppUser user = userDetailsService.currentUser();
        Doctor d = doctorRepo.findByUser(user).orElseThrow();
        return queryService.getAllAppointmentsForDoctor(d).stream()
                .map(AppointmentDto::from).toList();
    }

    @Operation(
            summary     = "Talebi onayla",
            description = "DOKTOR rolündeki kullanıcı: ID’si verilen randevuyu ONAYLI olarak işaretler."
    )
    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentDto approve(@PathVariable Long id) {
        return AppointmentDto.from(approvalService.approve(id));
    }

    @Operation(
            summary     = "Talebi reddet",
            description = "DOKTOR rolündeki kullanıcı: ID’si verilen randevuyu RED olarak işaretler."
    )
    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentDto reject(@PathVariable Long id) {
        return AppointmentDto.from(approvalService.reject(id));
    }
}

