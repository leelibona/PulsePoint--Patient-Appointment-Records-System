package com.pulsepoint.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient in the PulsePoint system.
 * Extends User with patient-specific attributes and methods.
 */
public class Patient extends User {

    private LocalDate dateOfBirth;
    private String gender;
    private String profileStatus; // Incomplete, Complete, Updated, Deactivated, Archived
    private List<Appointment> appointments;
    private List<MedicalRecord> medicalRecords;

    public Patient(String userId, String fullName, String email, String passwordHash,
                   String phone, LocalDate dateOfBirth, String gender) {
        super(userId, fullName, email, passwordHash, phone, "Patient");
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.profileStatus = "Incomplete";
        this.appointments = new ArrayList<>();
        this.medicalRecords = new ArrayList<>();
    }

    public void updateProfile(String phone, String gender) {
        this.gender = gender;
        this.profileStatus = "Complete";
        System.out.println("Profile updated for patient: " + getFullName());
    }

    public List<MedicalRecord> viewMedicalRecords() {
        System.out.println("Retrieving medical records for: " + getFullName());
        return medicalRecords;
    }

    public List<Doctor> searchDoctors(String query, List<Doctor> availableDoctors) {
        List<Doctor> results = new ArrayList<>();
        for (Doctor doctor : availableDoctors) {
            if (doctor.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                doctor.getSpecialisation().toLowerCase().contains(query.toLowerCase())) {
                results.add(doctor);
            }
        }
        return results;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void addMedicalRecord(MedicalRecord record) {
        this.medicalRecords.add(record);
    }

    // Getters
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getProfileStatus() { return profileStatus; }
    public List<Appointment> getAppointments() { return appointments; }

    @Override
    public String toString() {
        return "Patient{userId='" + getUserId() + "', fullName='" + getFullName() +
               "', dateOfBirth=" + dateOfBirth + ", gender='" + gender +
               "', profileStatus='" + profileStatus + "'}";
    }
}