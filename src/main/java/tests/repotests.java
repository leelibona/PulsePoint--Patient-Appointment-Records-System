package com.pulsepoint;

import com.pulsepoint.factories.RepositoryFactory;
import com.pulsepoint.models.*;
import com.pulsepoint.repositories.*;
import com.pulsepoint.repositories.inmemory.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

/**
 * Unit Tests for all In-Memory Repository implementations.
 *
 * Tests verify:
 * - CRUD operations work correctly for each repository
 * - Edge cases (null inputs, missing entities, immutable audit logs)
 * - Repository Factory returns correct implementations
 *
 * Total Tests: 30
 */
public class RepositoryTest {

    // Repositories under test
    private PatientRepository patientRepo;
    private DoctorRepository doctorRepo;
    private AppointmentRepository appointmentRepo;
    private MedicalRecordRepository medicalRecordRepo;
    private AuditLogRepository auditLogRepo;

    // Test data
    private Patient testPatient;
    private Doctor testDoctor;
    private Appointment testAppointment;
    private MedicalRecord testRecord;
    private AuditLog testAuditLog;

    @BeforeEach
    public void setUp() {
        // Fresh repositories for each test
        patientRepo = new InMemoryPatientRepository();
        doctorRepo = new InMemoryDoctorRepository();
        appointmentRepo = new InMemoryAppointmentRepository();
        medicalRecordRepo = new InMemoryMedicalRecordRepository();
        auditLogRepo = new InMemoryAuditLogRepository();

        // Test data
        testPatient = new Patient(
            "P001", "John Doe", "john@email.com",
            "hashedpass", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        testDoctor = new Doctor(
            "D001", "Jane Smith", "jane@clinic.com",
            "hashedpass", "0839876543",
            "General Practice", "MBChB"
        );

        testAppointment = new Appointment("APT001", "P001", "D001", "SLOT001");

        testRecord = new MedicalRecord(
            "REC001", "APT001", "D001",
            "Hypertension", "Patient has high blood pressure", "Amlodipine 5mg"
        );
        testRecord.setPatientId("P001");

        testAuditLog = new AuditLog(
            "LOG001", "P001", "VIEW_RECORD", "REC001", "192.168.1.1"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PATIENT REPOSITORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("PatientRepository — save() stores patient correctly")
    public void testPatientRepo_Save() {
        patientRepo.save(testPatient);
        assertEquals(1, patientRepo.count());
    }

    @Test
    @DisplayName("PatientRepository — findById() returns correct patient")
    public void testPatientRepo_FindById() {
        patientRepo.save(testPatient);
        Optional<Patient> found = patientRepo.findById("P001");

        assertTrue(found.isPresent());
        assertEquals("John Doe", found.get().getFullName());
    }

    @Test
    @DisplayName("PatientRepository — findById() returns empty for unknown ID")
    public void testPatientRepo_FindById_NotFound() {
        Optional<Patient> found = patientRepo.findById("UNKNOWN");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("PatientRepository — findAll() returns all saved patients")
    public void testPatientRepo_FindAll() {
        Patient patient2 = new Patient(
            "P002", "Jane Doe", "jane@email.com",
            "pass", "0800000001", LocalDate.of(1985, 3, 20), "Female"
        );
        patientRepo.save(testPatient);
        patientRepo.save(patient2);

        List<Patient> all = patientRepo.findAll();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("PatientRepository — delete() removes patient correctly")
    public void testPatientRepo_Delete() {
        patientRepo.save(testPatient);
        patientRepo.delete("P001");

        assertEquals(0, patientRepo.count());
        assertFalse(patientRepo.existsById("P001"));
    }

    @Test
    @DisplayName("PatientRepository — delete() throws exception for unknown ID")
    public void testPatientRepo_Delete_NotFound() {
        assertThrows(NoSuchElementException.class, () ->
            patientRepo.delete("UNKNOWN")
        );
    }

    @Test
    @DisplayName("PatientRepository — findByEmail() returns correct patient")
    public void testPatientRepo_FindByEmail() {
        patientRepo.save(testPatient);
        Optional<Patient> found = patientRepo.findByEmail("john@email.com");

        assertTrue(found.isPresent());
        assertEquals("P001", found.get().getUserId());
    }

    @Test
    @DisplayName("PatientRepository — save() throws exception for null patient")
    public void testPatientRepo_Save_NullThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            patientRepo.save(null)
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DOCTOR REPOSITORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("DoctorRepository — save() and findById() work correctly")
    public void testDoctorRepo_SaveAndFind() {
        doctorRepo.save(testDoctor);
        Optional<Doctor> found = doctorRepo.findById("D001");

        assertTrue(found.isPresent());
        assertEquals("General Practice", found.get().getSpecialisation());
    }

    @Test
    @DisplayName("DoctorRepository — findBySpecialisation() returns correct doctors")
    public void testDoctorRepo_FindBySpecialisation() {
        doctorRepo.save(testDoctor);
        Doctor doctor2 = new Doctor(
            "D002", "Bob Jones", "bob@clinic.com",
            "pass", "0812345678", "Cardiology", "MBChB"
        );
        doctorRepo.save(doctor2);

        List<Doctor> gpDoctors = doctorRepo.findBySpecialisation("General Practice");
        assertEquals(1, gpDoctors.size());
        assertEquals("D001", gpDoctors.get(0).getUserId());
    }

    @Test
    @DisplayName("DoctorRepository — existsById() returns true for saved doctor")
    public void testDoctorRepo_ExistsById() {
        doctorRepo.save(testDoctor);
        assertTrue(doctorRepo.existsById("D001"));
        assertFalse(doctorRepo.existsById("D999"));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // APPOINTMENT REPOSITORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("AppointmentRepository — save() stores appointment correctly")
    public void testAppointmentRepo_Save() {
        appointmentRepo.save(testAppointment);
        assertEquals(1, appointmentRepo.count());
    }

    @Test
    @DisplayName("AppointmentRepository — findByPatientId() returns correct appointments")
    public void testAppointmentRepo_FindByPatientId() {
        appointmentRepo.save(testAppointment);
        Appointment apt2 = new Appointment("APT002", "P001", "D002", "SLOT002");
        appointmentRepo.save(apt2);

        List<Appointment> patientApts = appointmentRepo.findByPatientId("P001");
        assertEquals(2, patientApts.size());
    }

    @Test
    @DisplayName("AppointmentRepository — findByStatus() returns correct appointments")
    public void testAppointmentRepo_FindByStatus() {
        appointmentRepo.save(testAppointment);
        testAppointment.confirm();
        appointmentRepo.save(testAppointment);

        List<Appointment> confirmed = appointmentRepo.findByStatus("Confirmed");
        assertEquals(1, confirmed.size());
    }

    @Test
    @DisplayName("AppointmentRepository — findByDoctorId() returns correct appointments")
    public void testAppointmentRepo_FindByDoctorId() {
        appointmentRepo.save(testAppointment);
        List<Appointment> doctorApts = appointmentRepo.findByDoctorId("D001");
        assertEquals(1, doctorApts.size());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MEDICAL RECORD REPOSITORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("MedicalRecordRepository — save() and findById() work correctly")
    public void testMedicalRecordRepo_SaveAndFind() {
        medicalRecordRepo.save(testRecord);
        Optional<MedicalRecord> found = medicalRecordRepo.findById("REC001");

        assertTrue(found.isPresent());
        assertEquals("Hypertension", found.get().getDiagnosis());
    }

    @Test
    @DisplayName("MedicalRecordRepository — findByPatientId() returns correct records")
    public void testMedicalRecordRepo_FindByPatientId() {
        medicalRecordRepo.save(testRecord);
        List<MedicalRecord> records = medicalRecordRepo.findByPatientId("P001");
        assertEquals(1, records.size());
    }

    @Test
    @DisplayName("MedicalRecordRepository — findByStatus() returns correct records")
    public void testMedicalRecordRepo_FindByStatus() {
        testRecord.save();
        medicalRecordRepo.save(testRecord);

        List<MedicalRecord> saved = medicalRecordRepo.findByStatus("Saved");
        assertEquals(1, saved.size());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // AUDIT LOG REPOSITORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("AuditLogRepository — save() stores log correctly")
    public void testAuditLogRepo_Save() {
        auditLogRepo.save(testAuditLog);
        assertEquals(1, auditLogRepo.count());
    }

    @Test
    @DisplayName("AuditLogRepository — delete() throws UnsupportedOperationException")
    public void testAuditLogRepo_DeleteThrowsException() {
        auditLogRepo.save(testAuditLog);
        assertThrows(UnsupportedOperationException.class, () ->
            auditLogRepo.delete("LOG001")
        );
    }

    @Test
    @DisplayName("AuditLogRepository — findByUserId() returns correct logs")
    public void testAuditLogRepo_FindByUserId() {
        auditLogRepo.save(testAuditLog);
        List<AuditLog> logs = auditLogRepo.findByUserId("P001");
        assertEquals(1, logs.size());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // REPOSITORY FACTORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("RepositoryFactory — returns InMemoryPatientRepository for MEMORY type")
    public void testFactory_ReturnsInMemoryPatientRepo() {
        PatientRepository repo = RepositoryFactory.getPatientRepository("MEMORY");
        assertNotNull(repo);
        assertInstanceOf(InMemoryPatientRepository.class, repo);
    }

    @Test
    @DisplayName("RepositoryFactory — returns InMemoryDoctorRepository for MEMORY type")
    public void testFactory_ReturnsInMemoryDoctorRepo() {
        DoctorRepository repo = RepositoryFactory.getDoctorRepository("MEMORY");
        assertNotNull(repo);
        assertInstanceOf(InMemoryDoctorRepository.class, repo);
    }

    @Test
    @DisplayName("RepositoryFactory — returns InMemoryAppointmentRepository for MEMORY type")
    public void testFactory_ReturnsInMemoryAppointmentRepo() {
        AppointmentRepository repo = RepositoryFactory.getAppointmentRepository("MEMORY");
        assertNotNull(repo);
        assertInstanceOf(InMemoryAppointmentRepository.class, repo);
    }

    @Test
    @DisplayName("RepositoryFactory — throws exception for unknown storage type")
    public void testFactory_ThrowsExceptionForUnknownType() {
        assertThrows(IllegalArgumentException.class, () ->
            RepositoryFactory.getPatientRepository("REDIS")
        );
    }

    @Test
    @DisplayName("RepositoryFactory — is case insensitive for storage type")
    public void testFactory_CaseInsensitive() {
        PatientRepository repo = RepositoryFactory.getPatientRepository("memory");
        assertNotNull(repo);
    }
}