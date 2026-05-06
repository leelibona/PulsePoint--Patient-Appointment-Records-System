package com.pulsepoint.repositories.inmemory;

import com.pulsepoint.models.Appointment;
import com.pulsepoint.repositories.AppointmentRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In-Memory implementation of AppointmentRepository.
 * Uses a HashMap to store Appointment objects during application runtime.
 */
public class InMemoryAppointmentRepository implements AppointmentRepository {

    private final Map<String, Appointment> storage = new HashMap<>();

    @Override
    public void save(Appointment appointment) {
        if (appointment == null || appointment.getAppointmentId() == null) {
            throw new IllegalArgumentException("Appointment and Appointment ID cannot be null.");
        }
        storage.put(appointment.getAppointmentId(), appointment);
        System.out.println("Appointment saved: " + appointment.getAppointmentId());
    }

    @Override
    public Optional<Appointment> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(String id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("Appointment not found with ID: " + id);
        }
        storage.remove(id);
        System.out.println("Appointment deleted with ID: " + id);
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
    public List<Appointment> findByPatientId(String patientId) {
        return storage.values().stream()
            .filter(a -> a.getPatientId().equals(patientId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByDoctorId(String doctorId) {
        return storage.values().stream()
            .filter(a -> a.getDoctorId().equals(doctorId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByStatus(String status) {
        return storage.values().stream()
            .filter(a -> a.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findBySlotId(String slotId) {
        return storage.values().stream()
            .filter(a -> a.getSlotId().equals(slotId))
            .collect(Collectors.toList());
    }
}