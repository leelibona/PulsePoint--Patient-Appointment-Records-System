package com.pulsepoint.repositories.database;

import com.pulsepoint.models.*;
import com.pulsepoint.repositories.*;

import java.util.*;

/**
 * DATABASE STUB IMPLEMENTATIONS — Future-Proofing
 *
 * These are skeleton classes that demonstrate how the system can be
 * extended to support a real PostgreSQL database without changing
 * any business logic or repository interface code.
 *
 * In production, these classes would:
 * 1. Use JDBC or Hibernate to connect to PostgreSQL
 * 2. Execute SQL queries to persist and retrieve entities
 * 3. Handle connection pooling via the DatabaseConnection singleton
 *
 * To activate: Change "MEMORY" to "DATABASE" in RepositoryFactory calls.
 */

// ─── Patient Database Repository Stub ────────────────────────────────────────

class DatabasePatientRepository implements PatientRepository {
    @Override
    public void save(Patient patient) {
        // TODO: INSERT INTO patients VALUES (...)
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public Optional<Patient> findById(String id) {
        // TODO: SELECT * FROM patients WHERE patient_id = ?
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public List<Patient> findAll() {
        // TODO: SELECT * FROM patients
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public void delete(String id) {
        // TODO: DELETE FROM patients WHERE patient_id = ?
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public int count() {
        // TODO: SELECT COUNT(*) FROM patients
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public boolean existsById(String id) {
        // TODO: SELECT COUNT(*) FROM patients WHERE patient_id = ?
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        // TODO: SELECT * FROM patients WHERE email = ?
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public List<Patient> findAllActive() {
        // TODO: SELECT * FROM patients WHERE is_active = true
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }

    @Override
    public List<Patient> findByProfileStatus(String status) {
        // TODO: SELECT * FROM patients WHERE profile_status = ?
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
}

// ─── Doctor Database Repository Stub ─────────────────────────────────────────

class DatabaseDoctorRepository implements DoctorRepository {
    @Override
    public void save(Doctor doctor) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public Optional<Doctor> findById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Doctor> findAll() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public int count() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public Optional<Doctor> findByEmail(String email) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Doctor> findBySpecialisation(String specialisation) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Doctor> findAllActive() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
}

// ─── Appointment Database Repository Stub ────────────────────────────────────

class DatabaseAppointmentRepository implements AppointmentRepository {
    @Override
    public void save(Appointment appointment) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public Optional<Appointment> findById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Appointment> findAll() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public int count() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Appointment> findByPatientId(String patientId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Appointment> findByDoctorId(String doctorId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Appointment> findByStatus(String status) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<Appointment> findBySlotId(String slotId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
}

// ─── MedicalRecord Database Repository Stub ──────────────────────────────────

class DatabaseMedicalRecordRepository implements MedicalRecordRepository {
    @Override
    public void save(MedicalRecord record) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public Optional<MedicalRecord> findById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<MedicalRecord> findAll() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public int count() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<MedicalRecord> findByPatientId(String patientId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<MedicalRecord> findByDoctorId(String doctorId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<MedicalRecord> findByAppointmentId(String appointmentId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<MedicalRecord> findByStatus(String status) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
}

// ─── AuditLog Database Repository Stub ───────────────────────────────────────

class DatabaseAuditLogRepository implements AuditLogRepository {
    @Override
    public void save(AuditLog log) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public Optional<AuditLog> findById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<AuditLog> findAll() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public int count() {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<AuditLog> findByUserId(String userId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<AuditLog> findByAffectedRecordId(String recordId) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
    @Override
    public List<AuditLog> findByStatus(String status) {
        throw new UnsupportedOperationException("Database implementation coming soon.");
    }
}