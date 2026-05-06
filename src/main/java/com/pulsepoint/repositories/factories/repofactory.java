package com.pulsepoint.factories;

import com.pulsepoint.repositories.*;
import com.pulsepoint.repositories.inmemory.*;
import com.pulsepoint.repositories.database.*;

/**
 * Repository Factory — Storage Abstraction Mechanism
 *
 * Purpose: Decouples business logic from storage implementation.
 * Returns the correct repository implementation based on the storage type.
 *
 * Supported storage types:
 * - "MEMORY" : In-memory HashMap (default, used for testing and development)
 * - "DATABASE": PostgreSQL database (production implementation — future)
 *
 * Usage:
 *   PatientRepository repo = RepositoryFactory.getPatientRepository("MEMORY");
 *   repo.save(patient);
 *
 * To switch to a database in production, simply change "MEMORY" to "DATABASE"
 * without changing any business logic code.
 */
public class RepositoryFactory {

    // ─── Patient Repository ───────────────────────────────────────────────────

    public static PatientRepository getPatientRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryPatientRepository();
            case "DATABASE":
                return new DatabasePatientRepository();
            default:
                throw new IllegalArgumentException(
                    "Unknown storage type: " + storageType +
                    ". Supported types: MEMORY, DATABASE"
                );
        }
    }

    // ─── Doctor Repository ────────────────────────────────────────────────────

    public static DoctorRepository getDoctorRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryDoctorRepository();
            case "DATABASE":
                return new DatabaseDoctorRepository();
            default:
                throw new IllegalArgumentException(
                    "Unknown storage type: " + storageType
                );
        }
    }

    // ─── Appointment Repository ───────────────────────────────────────────────

    public static AppointmentRepository getAppointmentRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryAppointmentRepository();
            case "DATABASE":
                return new DatabaseAppointmentRepository();
            default:
                throw new IllegalArgumentException(
                    "Unknown storage type: " + storageType
                );
        }
    }

    // ─── Medical Record Repository ────────────────────────────────────────────

    public static MedicalRecordRepository getMedicalRecordRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryMedicalRecordRepository();
            case "DATABASE":
                return new DatabaseMedicalRecordRepository();
            default:
                throw new IllegalArgumentException(
                    "Unknown storage type: " + storageType
                );
        }
    }

    // ─── Audit Log Repository ─────────────────────────────────────────────────

    public static AuditLogRepository getAuditLogRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryAuditLogRepository();
            case "DATABASE":
                return new DatabaseAuditLogRepository();
            default:
                throw new IllegalArgumentException(
                    "Unknown storage type: " + storageType
                );
        }
    }
}