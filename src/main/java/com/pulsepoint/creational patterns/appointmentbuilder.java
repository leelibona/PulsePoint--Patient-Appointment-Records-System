package com.pulsepoint.creational_patterns.builder;

import java.time.LocalDateTime;

/**
 * Builder Pattern — Appointment Builder
 *
 * Purpose: Constructs a complex Appointment object step by step.
 * Appointments have many required and optional fields — the Builder
 * pattern makes construction readable and prevents invalid partial objects.
 *
 * Use Case in PulsePoint: When a patient books an appointment, the system
 * needs to set the patient, doctor, time slot, notes, and status in a
 * controlled, step-by-step process.
 */
public class AppointmentBuilder {

    // Required fields
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String slotId;

    // Optional fields
    private String status;
    private String notes;
    private LocalDateTime appointmentDate;
    private boolean smsReminderEnabled;
    private String consultationType; // InPerson, Virtual

    public AppointmentBuilder(String appointmentId, String patientId,
                               String doctorId, String slotId) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        // Default values
        this.status = "Pending";
        this.smsReminderEnabled = true;
        this.consultationType = "InPerson";
    }

    public AppointmentBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public AppointmentBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public AppointmentBuilder withAppointmentDate(LocalDateTime date) {
        this.appointmentDate = date;
        return this;
    }

    public AppointmentBuilder withSmsReminder(boolean enabled) {
        this.smsReminderEnabled = enabled;
        return this;
    }

    public AppointmentBuilder withConsultationType(String type) {
        this.consultationType = type;
        return this;
    }

    public AppointmentProduct build() {
        // Validate required fields
        if (appointmentId == null || patientId == null ||
            doctorId == null || slotId == null) {
            throw new IllegalStateException("Appointment requires appointmentId, patientId, doctorId, and slotId.");
        }
        return new AppointmentProduct(this);
    }

    // Getters for AppointmentProduct
    public String getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getSlotId() { return slotId; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public boolean isSmsReminderEnabled() { return smsReminderEnabled; }
    public String getConsultationType() { return consultationType; }
}