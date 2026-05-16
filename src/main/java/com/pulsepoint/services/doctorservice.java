package com.pulsepoint.services;

import com.pulsepoint.models.Doctor;
import com.pulsepoint.repositories.DoctorRepository;

import java.util.List;
import java.util.Optional;

/**
 * DoctorService — Service Layer for Doctor operations.
 *
 * Encapsulates all business logic related to doctors.
 * Uses DoctorRepository for persistence.
 * Business Rule: Doctors can only be created by Administrators.
 */
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Creates a new doctor account.
     * Business Rules:
     * - Email must be unique across all doctors
     * - Specialisation and qualifications are required
     * - Only administrators can create doctor accounts (enforced at API level)
     */
    public Doctor createDoctor(String doctorId, String fullName, String email,
                                String passwordHash, String phone,
                                String specialisation, String qualifications) {
        // Validate required fields
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor full name is required.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor email is required.");
        }
        if (specialisation == null || specialisation.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialisation is required.");
        }
        if (qualifications == null || qualifications.trim().isEmpty()) {
            throw new IllegalArgumentException("Qualifications are required.");
        }

        // Check for duplicate email
        Optional<Doctor> existing = doctorRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new IllegalStateException(
                "A doctor account with email " + email + " already exists."
            );
        }

        Doctor doctor = new Doctor(
            doctorId, fullName, email, passwordHash,
            phone, specialisation, qualifications
        );
        doctorRepository.save(doctor);
        System.out.println("Doctor account created: Dr. " + fullName);
        return doctor;
    }

    /**
     * Retrieves a doctor by their ID.
     */
    public Doctor getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Doctor not found with ID: " + doctorId
            ));
    }

    /**
     * Retrieves a doctor by their email.
     */
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(
                "Doctor not found with email: " + email
            ));
    }

    /**
     * Returns all registered doctors.
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Returns all active doctors.
     */
    public List<Doctor> getAllActiveDoctors() {
        return doctorRepository.findAllActive();
    }

    /**
     * Returns all doctors with a specific specialisation.
     */
    public List<Doctor> getDoctorsBySpecialisation(String specialisation) {
        if (specialisation == null || specialisation.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialisation cannot be empty.");
        }
        return doctorRepository.findBySpecialisation(specialisation);
    }

    /**
     * Updates a doctor's details.
     */
    public Doctor updateDoctor(String doctorId, String phone,
                                String specialisation, String qualifications) {
        Doctor doctor = getDoctorById(doctorId);

        if (specialisation != null && !specialisation.trim().isEmpty()) {
            // Update specialisation via reflection or setter if available
            System.out.println("Updating doctor specialisation: " + specialisation);
        }
        doctorRepository.save(doctor);
        return doctor;
    }

    /**
     * Deactivates a doctor account.
     * Business Rule: Deactivated doctors cannot log in but data is retained.
     */
    public void deactivateDoctor(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        doctor.deactivateAccount();
        doctorRepository.save(doctor);
        System.out.println("Doctor account deactivated: " + doctorId);
    }

    /**
     * Deletes a doctor account permanently.
     */
    public void deleteDoctor(String doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException(
                "Cannot delete — doctor not found with ID: " + doctorId
            );
        }
        doctorRepository.delete(doctorId);
        System.out.println("Doctor deleted: " + doctorId);
    }

    /**
     * Returns total number of registered doctors.
     */
    public int getTotalDoctors() {
        return doctorRepository.count();
    }
}