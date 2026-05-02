package com.pulsepoint.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a doctor in the PulsePoint system.
 * Extends User with doctor-specific attributes and methods.
 */
public class Doctor extends User {

    private String specialisation;
    private String qualifications;
    private List<Appointment> appointments;
    private List<TimeSlot> availableSlots;

    public Doctor(String userId, String fullName, String email, String passwordHash,
                  String phone, String specialisation, String qualifications) {
        super(userId, fullName, email, passwordHash, phone, "Doctor");
        this.specialisation = specialisation;
        this.qualifications = qualifications;
        this.appointments = new ArrayList<>();
        this.availableSlots = new ArrayList<>();
    }

    public List<Appointment> viewSchedule() {
        System.out.println("Retrieving schedule for Dr. " + getFullName());
        return appointments;
    }

    public void markAppointmentStatus(String appointmentId, String status) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId().equals(appointmentId)) {
                appointment.setStatus(status);
                System.out.println("Appointment " + appointmentId + " marked as: " + status);
                return;
            }
        }
        System.out.println("Appointment not found: " + appointmentId);
    }

    public MedicalRecord createMedicalRecord(String appointmentId, String diagnosis,
                                              String notes, String prescription) {
        MedicalRecord record = new MedicalRecord(
            "REC-" + System.currentTimeMillis(),
            appointmentId,
            getUserId(),
            diagnosis,
            notes,
            prescription
        );
        System.out.println("Medical record created by Dr. " + getFullName());
        return record;
    }

    public void updateAvailability(List<TimeSlot> slots) {
        this.availableSlots = slots;
        System.out.println("Availability updated for Dr. " + getFullName());
    }

    public List<MedicalRecord> viewPatientHistory(String patientId, List<MedicalRecord> allRecords) {
        List<MedicalRecord> patientRecords = new ArrayList<>();
        for (MedicalRecord record : allRecords) {
            if (record.getPatientId().equals(patientId) && record.getDoctorId().equals(getUserId())) {
                patientRecords.add(record);
            }
        }
        return patientRecords;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public void addTimeSlot(TimeSlot slot) {
        this.availableSlots.add(slot);
    }

    // Getters
    public String getSpecialisation() { return specialisation; }
    public String getQualifications() { return qualifications; }
    public List<TimeSlot> getAvailableSlots() { return availableSlots; }

    @Override
    public String toString() {
        return "Doctor{userId='" + getUserId() + "', fullName='Dr. " + getFullName() +
               "', specialisation='" + specialisation + "'}";
    }
}