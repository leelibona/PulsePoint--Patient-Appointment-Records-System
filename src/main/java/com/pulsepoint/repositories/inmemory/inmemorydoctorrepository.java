package com.pulsepoint.repositories.inmemory;

import com.pulsepoint.models.Doctor;
import com.pulsepoint.repositories.DoctorRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In-Memory implementation of DoctorRepository.
 * Uses a HashMap to store Doctor objects during application runtime.
 */
public class InMemoryDoctorRepository implements DoctorRepository {

    private final Map<String, Doctor> storage = new HashMap<>();

    @Override
    public void save(Doctor doctor) {
        if (doctor == null || doctor.getUserId() == null) {
            throw new IllegalArgumentException("Doctor and Doctor ID cannot be null.");
        }
        storage.put(doctor.getUserId(), doctor);
        System.out.println("Doctor saved: Dr. " + doctor.getFullName());
    }

    @Override
    public Optional<Doctor> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Doctor> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(String id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("Doctor not found with ID: " + id);
        }
        storage.remove(id);
        System.out.println("Doctor deleted with ID: " + id);
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
    public Optional<Doctor> findByEmail(String email) {
        return storage.values().stream()
            .filter(d -> d.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    @Override
    public List<Doctor> findBySpecialisation(String specialisation) {
        return storage.values().stream()
            .filter(d -> d.getSpecialisation().equalsIgnoreCase(specialisation))
            .collect(Collectors.toList());
    }

    @Override
    public List<Doctor> findAllActive() {
        return storage.values().stream()
            .filter(Doctor::isActive)
            .collect(Collectors.toList());
    }
}