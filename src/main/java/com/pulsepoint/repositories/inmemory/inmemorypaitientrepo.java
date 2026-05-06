package com.pulsepoint.repositories.inmemory;

import com.pulsepoint.models.Patient;
import com.pulsepoint.repositories.PatientRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In-Memory implementation of PatientRepository.
 * Uses a HashMap to store Patient objects during application runtime.
 * Data is not persisted between application restarts.
 *
 * This implementation is ideal for:
 * - Unit testing without external dependencies
 * - Development and prototyping
 * - Demonstrating repository pattern before connecting a real database
 */
public class InMemoryPatientRepository implements PatientRepository {

    private final Map<String, Patient> storage = new HashMap<>();

    @Override
    public void save(Patient patient) {
        if (patient == null || patient.getUserId() == null) {
            throw new IllegalArgumentException("Patient and Patient ID cannot be null.");
        }
        storage.put(patient.getUserId(), patient);
        System.out.println("Patient saved: " + patient.getFullName());
    }

    @Override
    public Optional<Patient> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(String id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("Patient not found with ID: " + id);
        }
        storage.remove(id);
        System.out.println("Patient deleted with ID: " + id);
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return storage.values().stream()
            .filter(p -> p.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    @Override
    public List<Patient> findAllActive() {
        return storage.values().stream()
            .filter(Patient::isActive)
            .collect(Collectors.toList());
    }

    @Override
    public List<Patient> findByProfileStatus(String status) {
        return storage.values().stream()
            .filter(p -> p.getProfileStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }
}