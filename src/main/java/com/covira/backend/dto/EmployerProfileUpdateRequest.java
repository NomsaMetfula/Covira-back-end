package com.covira.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmployerProfileUpdateRequest {

    @NotBlank(message = "Company name is required.")
    @Size(max = 150, message = "Company name is too long.")
    private String companyName;

    @NotBlank(message = "Contact person is required.")
    @Size(max = 120, message = "Contact person name is too long.")
    private String fullName;

    @NotBlank(message = "Email address is required.")
    @Email(message = "Enter a valid email address.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^[0-9+()\\-\\s]{7,20}$",
            message = "Enter a valid phone number."
    )
    private String phoneNumber;

    public EmployerProfileUpdateRequest() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}