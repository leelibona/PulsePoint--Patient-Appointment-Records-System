package com.pulsepoint.models;

import java.time.LocalDateTime;

/**
 * Represents a medical record in the PulsePoint system.
 * Created by a Doctor after a consultation and linked to an Appointment.
 */
public class MedicalRecord {

    private String recordId;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String consultationNotes;
    private String prescription;
    private String status; // Draft, Saved, Updated, UnderReview, Reviewed, Flagged, Discarded
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MedicalRecord(String recordId, String appointmentId, String doctorId,
                         String diagnosis, String consultationNotes, String prescription) {
        this.recordId = recordId;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.consultationNotes = consultationNotes;
        this.prescription = prescription;
        this.status = "Draft";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void save() {
        if (diagnosis == null || diagnosis.isEmpty() ||
            consultationNotes == null || consultationNotes.isEmpty()) {
            System.out.println("Cannot save: All required fields must be completed.");
            return;
        }
        this.status = "Saved";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Medical record " + recordId + " saved successfully.");
    }

    public void update(String diagnosis, String notes, String prescription) {
        this.status = "Updated";
        this.diagnosis = diagnosis;
        this.consultationNotes = notes;
        this.prescription = prescription;
        this.updatedAt = LocalDateTime.now();
        System.out.println("Medical record " + recordId + " updated.");
    }

    public void flagForReview(String reason) {
        this.status = "Flagged";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Medical record " + recordId + " flagged for review: " + reason);
    }

    public void resolveFlag() {
        this.status = "Reviewed";
        this.updatedAt = LocalDateTime.now();
        System.out.println("Medical record " + recordId + " flag resolved.");
    }

    // Getters and Setters
    public String getRecordId() { return recordId; }
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public String getConsultationNotes() { return consultationNotes; }
    public String getPrescription() { return prescription; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    @Override
    public String toString() {
        return "MedicalRecord{recordId='" + recordId + "', patientId='" + patientId +
               "', diagnosis='" + diagnosis + "', status='" + status + "'}";
    }
}