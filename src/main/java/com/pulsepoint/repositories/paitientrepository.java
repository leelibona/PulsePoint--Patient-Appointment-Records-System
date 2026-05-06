package com.pulsepoint.repositories;

import com.pulsepoint.models.Patient;
import java.util.List;
import java.util.Optional;

/**
 * Patient-specific repository interface.
 * Extends the generic Repository with Patient-specific query methods.
 */
public interface PatientRepository extends Repository<Patient, String> {

    /**
     * Finds a patient by their email address.
     * @param email The email address to search for
     * @return An Optional containing the patient if found
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Returns all active (non-deactivated) patients.
     * @return List of active patients
     */
    List<Patient> findAllActive();

    /**
     * Finds patients by their profile status.
     * @param status e.g. "Complete", "Incomplete", "Archived"
     * @return List of patients with the given status
     */
    List<Patient> findByProfileStatus(String status);
}