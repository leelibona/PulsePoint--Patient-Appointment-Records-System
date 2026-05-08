package com.pulsepoint.repositories;

import com.pulsepoint.models.MedicalRecord;
import java.util.List;

/**
 * MedicalRecord-specific repository interface.
 * Extends the generic Repository with MedicalRecord-specific query methods.
 */
public interface MedicalRecordRepository extends Repository<MedicalRecord, String> {

    /**
     * Finds all medical records for a specific patient.
     * @param patientId The ID of the patient
     * @return List of medical records for that patient
     */
    List<MedicalRecord> findByPatientId(String patientId);

    /**
     * Finds all medical records created by a specific doctor.
     * @param doctorId The ID of the doctor
     * @return List of medical records created by that doctor
     */
    List<MedicalRecord> findByDoctorId(String doctorId);

    /**
     * Finds all medical records linked to a specific appointment.
     * @param appointmentId The ID of the appointment
     * @return List of medical records for that appointment
     */
    List<MedicalRecord> findByAppointmentId(String appointmentId);

    /**
     * Finds all medical records with a specific status.
     * @param status e.g. "Saved", "Flagged", "UnderReview"
     * @return List of medical records with the given status
     */
    List<MedicalRecord> findByStatus(String status);
}