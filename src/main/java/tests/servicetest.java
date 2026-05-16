package com.pulsepoint.tests.services;

import com.pulsepoint.models.*;
import com.pulsepoint.repositories.inmemory.*;
import com.pulsepoint.services.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Unit Tests for Service Layer — PatientService, DoctorService, AppointmentService.
 * Uses in-memory repositories — no external dependencies needed.
 * Total Tests: 24
 */
public class ServiceLayerTest {

    private PatientService patientService;
    private DoctorService doctorService;
    private AppointmentService appointmentService;

    private InMemoryPatientRepository patientRepo;
    private InMemoryDoctorRepository doctorRepo;
    private InMemoryAppointmentRepository appointmentRepo;

    @BeforeEach
    public void setUp() {
        patientRepo = new InMemoryPatientRepository();
        doctorRepo = new InMemoryDoctorRepository();
        appointmentRepo = new InMemoryAppointmentRepository();

        patientService = new PatientService(patientRepo);
        doctorService = new DoctorService(doctorRepo);
        appointmentService = new AppointmentService(
            appointmentRepo, patientRepo, doctorRepo
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PATIENT SERVICE TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("PatientService — registers a new patient successfully")
    public void testPatientService_RegisterSuccess() {
        Patient patient = patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        assertNotNull(patient);
        assertEquals("P001", patient.getUserId());
        assertEquals("John Doe", patient.getFullName());
        assertEquals(1, patientService.getTotalPatients());
    }

    @Test
    @DisplayName("PatientService — throws exception for duplicate email")
    public void testPatientService_DuplicateEmailThrowsException() {
        patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        assertThrows(IllegalStateException.class, () ->
            patientService.registerPatient(
                "P002", "Jane Doe", "john@email.com",
                "hash456", "0829876543",
                LocalDate.of(1992, 3, 20), "Female"
            )
        );
    }

    @Test
    @DisplayName("PatientService — throws exception for future date of birth")
    public void testPatientService_FutureDateOfBirthThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            patientService.registerPatient(
                "P003", "Future Person", "future@email.com",
                "hash789", "0800000000",
                LocalDate.now().plusDays(1), "Male"
            )
        );
    }

    @Test
    @DisplayName("PatientService — throws exception for missing required fields")
    public void testPatientService_MissingFieldsThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            patientService.registerPatient(
                "P004", "", "test@email.com",
                "hash", "0800000001",
                LocalDate.of(1995, 1, 1), "Female"
            )
        );
    }

    @Test
    @DisplayName("PatientService — retrieves patient by ID successfully")
    public void testPatientService_GetById() {
        patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        Patient found = patientService.getPatientById("P001");
        assertNotNull(found);
        assertEquals("john@email.com", found.getEmail());
    }

    @Test
    @DisplayName("PatientService — throws exception for unknown patient ID")
    public void testPatientService_GetById_NotFound() {
        assertThrows(IllegalArgumentException.class, () ->
            patientService.getPatientById("UNKNOWN")
        );
    }

    @Test
    @DisplayName("PatientService — deactivates a patient account")
    public void testPatientService_Deactivate() {
        patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        patientService.deactivatePatient("P001");
        Patient patient = patientService.getPatientById("P001");
        assertFalse(patient.isActive());
    }

    @Test
    @DisplayName("PatientService — deletes a patient successfully")
    public void testPatientService_Delete() {
        patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        patientService.deletePatient("P001");
        assertEquals(0, patientService.getTotalPatients());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DOCTOR SERVICE TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("DoctorService — creates a new doctor successfully")
    public void testDoctorService_CreateSuccess() {
        Doctor doctor = doctorService.createDoctor(
            "D001", "Jane Smith", "jane@clinic.com",
            "hash456", "0839876543",
            "General Practice", "MBChB"
        );

        assertNotNull(doctor);
        assertEquals("D001", doctor.getUserId());
        assertEquals("General Practice", doctor.getSpecialisation());
    }

    @Test
    @DisplayName("DoctorService — throws exception for duplicate email")
    public void testDoctorService_DuplicateEmailThrowsException() {
        doctorService.createDoctor(
            "D001", "Jane Smith", "jane@clinic.com",
            "hash456", "0839876543", "General Practice", "MBChB"
        );

        assertThrows(IllegalStateException.class, () ->
            doctorService.createDoctor(
                "D002", "Bob Jones", "jane@clinic.com",
                "hash789", "0812345678", "Cardiology", "MBChB"
            )
        );
    }

    @Test
    @DisplayName("DoctorService — throws exception for missing specialisation")
    public void testDoctorService_MissingSpecialisationThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            doctorService.createDoctor(
                "D003", "Test Doctor", "test@clinic.com",
                "hash", "0800000000", "", "MBChB"
            )
        );
    }

    @Test
    @DisplayName("DoctorService — retrieves doctors by specialisation")
    public void testDoctorService_GetBySpecialisation() {
        doctorService.createDoctor(
            "D001", "Jane Smith", "jane@clinic.com",
            "hash456", "0839876543", "General Practice", "MBChB"
        );
        doctorService.createDoctor(
            "D002", "Bob Jones", "bob@clinic.com",
            "hash789", "0812345678", "Cardiology", "MBChB"
        );

        var gpDoctors = doctorService.getDoctorsBySpecialisation("General Practice");
        assertEquals(1, gpDoctors.size());
        assertEquals("D001", gpDoctors.get(0).getUserId());
    }

    @Test
    @DisplayName("DoctorService — deactivates a doctor account")
    public void testDoctorService_Deactivate() {
        doctorService.createDoctor(
            "D001", "Jane Smith", "jane@clinic.com",
            "hash456", "0839876543", "General Practice", "MBChB"
        );

        doctorService.deactivateDoctor("D001");
        Doctor doctor = doctorService.getDoctorById("D001");
        assertFalse(doctor.isActive());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // APPOINTMENT SERVICE TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @BeforeEach
    public void setUpAppointmentData() {
        // Pre-register patient and doctor for appointment tests
    }

    private void createTestPatientAndDoctor() {
        patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );
        doctorService.createDoctor(
            "D001", "Jane Smith", "jane@clinic.com",
            "hash456", "0839876543", "General Practice", "MBChB"
        );
    }

    @Test
    @DisplayName("AppointmentService — books an appointment successfully")
    public void testAppointmentService_BookSuccess() {
        createTestPatientAndDoctor();

        Appointment appointment = appointmentService.bookAppointment(
            "APT001", "P001", "D001", "SLOT001"
        );

        assertNotNull(appointment);
        assertEquals("APT001", appointment.getAppointmentId());
        assertEquals("Confirmed", appointment.getStatus());
    }

    @Test
    @DisplayName("AppointmentService — throws exception for non-existent patient")
    public void testAppointmentService_PatientNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
            appointmentService.bookAppointment(
                "APT001", "UNKNOWN_PATIENT", "D001", "SLOT001"
            )
        );
    }

    @Test
    @DisplayName("AppointmentService — throws exception for non-existent doctor")
    public void testAppointmentService_DoctorNotFound() {
        patientService.registerPatient(
            "P001", "John Doe", "john@email.com",
            "hash123", "0821234567",
            LocalDate.of(1990, 5, 15), "Male"
        );

        assertThrows(IllegalArgumentException.class, () ->
            appointmentService.bookAppointment(
                "APT001", "P001", "UNKNOWN_DOCTOR", "SLOT001"
            )
        );
    }

    @Test
    @DisplayName("AppointmentService — prevents double booking of same slot")
    public void testAppointmentService_PreventDoubleBooking() {
        createTestPatientAndDoctor();

        // Book slot first time
        appointmentService.bookAppointment("APT001", "P001", "D001", "SLOT001");

        // Attempt to book same slot again
        assertThrows(IllegalStateException.class, () ->
            appointmentService.bookAppointment("APT002", "P001", "D001", "SLOT001")
        );
    }

    @Test
    @DisplayName("AppointmentService — cancels appointment within allowed time")
    public void testAppointmentService_CancelSuccess() {
        createTestPatientAndDoctor();
        appointmentService.bookAppointment("APT001", "P001", "D001", "SLOT001");

        // Cancel with appointment time far in the future (allowed)
        Appointment cancelled = appointmentService.cancelAppointment(
            "APT001", LocalDateTime.now().plusDays(1)
        );

        assertEquals("Cancelled", cancelled.getStatus());
    }

    @Test
    @DisplayName("AppointmentService — blocks cancellation within 2 hours")
    public void testAppointmentService_CancelWithin2Hours() {
        createTestPatientAndDoctor();
        appointmentService.bookAppointment("APT001", "P001", "D001", "SLOT001");

        // Try to cancel with appointment time within 2 hours
        assertThrows(IllegalStateException.class, () ->
            appointmentService.cancelAppointment(
                "APT001", LocalDateTime.now().plusMinutes(30)
            )
        );
    }

    @Test
    @DisplayName("AppointmentService — reschedules appointment successfully")
    public void testAppointmentService_RescheduleSuccess() {
        createTestPatientAndDoctor();
        appointmentService.bookAppointment("APT001", "P001", "D001", "SLOT001");

        Appointment rescheduled = appointmentService.rescheduleAppointment(
            "APT001", "SLOT002", LocalDateTime.now().plusDays(2)
        );

        assertEquals("Rescheduled", rescheduled.getStatus());
        assertEquals("SLOT002", rescheduled.getSlotId());
    }

    @Test
    @DisplayName("AppointmentService — retrieves appointments by patient ID")
    public void testAppointmentService_GetByPatient() {
        createTestPatientAndDoctor();
        appointmentService.bookAppointment("APT001", "P001", "D001", "SLOT001");
        appointmentService.bookAppointment("APT002", "P001", "D001", "SLOT002");

        var appointments = appointmentService.getAppointmentsByPatient("P001");
        assertEquals(2, appointments.size());
    }

    @Test
    @DisplayName("AppointmentService — retrieves appointments by status")
    public void testAppointmentService_GetByStatus() {
        createTestPatientAndDoctor();
        appointmentService.bookAppointment("APT001", "P001", "D001", "SLOT001");

        var confirmed = appointmentService.getAppointmentsByStatus("Confirmed");
        assertEquals(1, confirmed.size());
    }
}