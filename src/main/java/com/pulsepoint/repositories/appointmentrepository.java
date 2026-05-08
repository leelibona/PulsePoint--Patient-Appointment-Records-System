package com.pulsepoint.repositories;

import com.pulsepoint.models.Appointment;
import java.util.List;

/**
 * Appointment-specific repository interface.
 * Extends the generic Repository with Appointment-specific query methods.
 */
public interface AppointmentRepository extends Repository<Appointment, String> {

    /**
     * Finds all appointments for a specific patient.
     * @param patientId The ID of the patient
     * @return List of appointments for that patient
     */
    List<Appointment> findByPatientId(String patientId);

    /**
     * Finds all appointments for a specific doctor.
     * @param doctorId The ID of the doctor
     * @return List of appointments for that doctor
     */
    List<Appointment> findByDoctorId(String doctorId);

    /**
     * Finds all appointments with a specific status.
     * @param status e.g. "Confirmed", "Pending", "Cancelled"
     * @return List of appointments with the given status
     */
    List<Appointment> findByStatus(String status);

    /**
     * Finds all appointments linked to a specific time slot.
     * @param slotId The ID of the time slot
     * @return List of appointments for that slot
     */
    List<Appointment> findBySlotId(String slotId);
}