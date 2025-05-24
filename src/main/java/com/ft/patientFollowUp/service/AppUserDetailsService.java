package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * JWT filtresi veya controller’lar tarafından,
     * SecurityContext’ten şu anki oturum açmış AppUser’ı almak için:
     */
    public AppUser currentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof AppUser) {
            return (AppUser) principal;
        } else {
            throw new UsernameNotFoundException("Current user is not an AppUser");
        }
    }
}