package com.pulsepoint.models;

import java.time.LocalDateTime;

/**
 * Represents an appointment in the PulsePoint system.
 * An appointment links a Patient to a Doctor at a specific TimeSlot.
 */
public class Appointment {

    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String slotId;
    private String status; // Pending, Confirmed, Rescheduled, InProgress, Completed, Cancelled, NoShow, Failed
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MedicalRecord medicalRecord;

    public Appointment(String appointmentId, String patientId, String doctorId, String slotId) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.status = "Pending";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        this.status = "Confirmed";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Appointment " + appointmentId + " confirmed.");
    }

    public void reschedule(String newSlotId) {
        this.slotId = newSlotId;
        this.status = "Rescheduled";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Appointment " + appointmentId + " rescheduled.");
    }

    public void cancel() {
        this.status = "Cancelled";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Appointment " + appointmentId + " cancelled.");
    }

    public void markInProgress() {
        this.status = "InProgress";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Appointment " + appointmentId + " is in progress.");
    }

    public void markCompleted() {
        this.status = "Completed";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Appointment " + appointmentId + " completed.");
    }

    public void markNoShow() {
        this.status = "NoShow";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Appointment " + appointmentId + " marked as no-show.");
    }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getSlotId() { return slotId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public MedicalRecord getMedicalRecord() { return medicalRecord; }
    public void setMedicalRecord(MedicalRecord medicalRecord) { this.medicalRecord = medicalRecord; }

    @Override
    public String toString() {
        return "Appointment{appointmentId='" + appointmentId + "', patientId='" + patientId +
               "', doctorId='" + doctorId + "', status='" + status + "'}";
    }
}