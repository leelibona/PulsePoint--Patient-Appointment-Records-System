package com.pulsepoint.repositories;

import com.pulsepoint.models.TimeSlot;
import java.util.List;

/**
 * TimeSlot-specific repository interface.
 */
public interface TimeSlotRepository extends Repository<TimeSlot, String> {

    /**
     * Finds all time slots for a specific doctor.
     */
    List<TimeSlot> findByDoctorId(String doctorId);

    /**
     * Finds all available time slots for a specific doctor.
     */
    List<TimeSlot> findAvailableByDoctorId(String doctorId);

    /**
     * Finds all time slots with a specific status.
     */
    List<TimeSlot> findByStatus(String status);
}