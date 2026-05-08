package com.pulsepoint.repositories;

import com.pulsepoint.models.Doctor;
import java.util.List;
import java.util.Optional;

/**
 * Doctor-specific repository interface.
 * Extends the generic Repository with Doctor-specific query methods.
 */
public interface DoctorRepository extends Repository<Doctor, String> {

    /**
     * Finds a doctor by their email address.
     * @param email The email address to search for
     * @return An Optional containing the doctor if found
     */
    Optional<Doctor> findByEmail(String email);

    /**
     * Finds all doctors with a specific specialisation.
     * @param specialisation e.g. "General Practice", "Cardiology"
     * @return List of doctors with the given specialisation
     */
    List<Doctor> findBySpecialisation(String specialisation);

    /**
     * Returns all active doctors.
     * @return List of active doctors
     */
    List<Doctor> findAllActive();
}