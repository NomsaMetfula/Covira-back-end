package com.covira.backend.dto;

public class EmployerProfileResponse {

    private String companyName;
    private String fullName;
    private String email;
    private String phoneNumber;

    public EmployerProfileResponse() {
    }

    public EmployerProfileResponse(
            String companyName,
            String fullName,
            String email,
            String phoneNumber
    ) {
        this.companyName = companyName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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