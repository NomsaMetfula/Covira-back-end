package com.covira.backend.service;

import com.covira.backend.dto.EmployerProfileResponse;
import com.covira.backend.dto.EmployerProfileUpdateRequest;
import com.covira.backend.entity.User;
import com.covira.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {

    private final UserRepository userRepository;

    public EmployerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public EmployerProfileResponse getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Employer not found."));

        return new EmployerProfileResponse(
                user.getCompanyName(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    public EmployerProfileResponse updateProfile(
            Long userId,
            EmployerProfileUpdateRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Employer not found."));

        user.setCompanyName(request.getCompanyName());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(user);

        return new EmployerProfileResponse(
                user.getCompanyName(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}