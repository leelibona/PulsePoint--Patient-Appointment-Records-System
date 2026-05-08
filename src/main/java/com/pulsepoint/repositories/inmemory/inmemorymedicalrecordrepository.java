package com.pulsepoint.repositories.inmemory;

import com.pulsepoint.models.MedicalRecord;
import com.pulsepoint.repositories.MedicalRecordRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In-Memory implementation of MedicalRecordRepository.
 */
public class InMemoryMedicalRecordRepository implements MedicalRecordRepository {

    private final Map<String, MedicalRecord> storage = new HashMap<>();

    @Override
    public void save(MedicalRecord record) {
        if (record == null || record.getRecordId() == null) {
            throw new IllegalArgumentException("MedicalRecord and Record ID cannot be null.");
        }
        storage.put(record.getRecordId(), record);
        System.out.println("Medical record saved: " + record.getRecordId());
    }

    @Override
    public Optional<MedicalRecord> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<MedicalRecord> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(String id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("Medical record not found with ID: " + id);
        }
        storage.remove(id);
        System.out.println("Medical record deleted: " + id);
    }

    @Override
    public int count() { return storage.size(); }

    @Override
    public boolean existsById(String id) { return storage.containsKey(id); }

    @Override
    public List<MedicalRecord> findByPatientId(String patientId) {
        return storage.values().stream()
            .filter(r -> patientId.equals(r.getPatientId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecord> findByDoctorId(String doctorId) {
        return storage.values().stream()
            .filter(r -> doctorId.equals(r.getDoctorId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecord> findByAppointmentId(String appointmentId) {
        return storage.values().stream()
            .filter(r -> appointmentId.equals(r.getAppointmentId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecord> findByStatus(String status) {
        return storage.values().stream()
            .filter(r -> r.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }
}