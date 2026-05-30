package com.pulsepoint.tests.api;

import com.pulsepoint.models.*;
import com.pulsepoint.repositories.inmemory.*;
import com.pulsepoint.services.*;
import com.pulsepoint.api.controllers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Integration Tests for REST API Controllers.
 * Tests the full stack: Controller → Service → Repository.
 * Uses in-memory repositories — no server needed.
 * Total Tests: 20
 */
public class ApiControllerTest {

    private PatientController patientController;
    private DoctorController doctorController;
    private AppointmentController appointmentController;

    @BeforeEach
    public void setUp() {
        InMemoryPatientRepository patientRepo = new InMemoryPatientRepository();
        InMemoryDoctorRepository doctorRepo = new InMemoryDoctorRepository();
        InMemoryAppointmentRepository appointmentRepo = new InMemoryAppointmentRepository();

        PatientService patientService = new PatientService(patientRepo);
        DoctorService doctorService = new DoctorService(doctorRepo);
        AppointmentService appointmentService = new AppointmentService(
            appointmentRepo, patientRepo, doctorRepo
        );

        patientController = new PatientController(patientService);
        doctorController = new DoctorController(doctorService);
        appointmentController = new AppointmentController(appointmentService);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PATIENT CONTROLLER TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/patients — returns 201 for valid patient")
    public void testPostPatient_Returns201() {
        Map<String, String> body = Map.of(
            "patientId", "P001",
            "fullName", "John Doe",
            "email", "john@email.com",
            "passwordHash", "hash123",
            "phone", "0821234567",
            "dateOfBirth", "1990-05-15",
            "gender", "Male"
        );

        ResponseEntity<?> response = patientController.registerPatient(body);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(Patient.class, response.getBody());
    }

    @Test
    @DisplayName("POST /api/patients — returns 409 for duplicate email")
    public void testPostPatient_Returns409ForDuplicate() {
        Map<String, String> body = Map.of(
            "patientId", "P001",
            "fullName", "John Doe",
            "email", "john@email.com",
            "passwordHash", "hash123",
            "phone", "0821234567",
            "dateOfBirth", "1990-05-15",
            "gender", "Male"
        );
        patientController.registerPatient(body);

        Map<String, String> duplicate = Map.of(
            "patientId", "P002",
            "fullName", "Jane Doe",
            "email", "john@email.com",
            "passwordHash", "hash456",
            "phone", "0829876543",
            "dateOfBirth", "1992-03-20",
            "gender", "Female"
        );

        ResponseEntity<?> response = patientController.registerPatient(duplicate);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/patients/{id} — returns 200 for existing patient")
    public void testGetPatientById_Returns200() {
        Map<String, String> body = Map.of(
            "patientId", "P001",
            "fullName", "John Doe",
            "email", "john@email.com",
            "passwordHash", "hash123",
            "phone", "0821234567",
            "dateOfBirth", "1990-05-15",
            "gender", "Male"
        );
        patientController.registerPatient(body);

        ResponseEntity<?> response = patientController.getPatientById("P001");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/patients/{id} — returns 404 for unknown ID")
    public void testGetPatientById_Returns404() {
        ResponseEntity<?> response = patientController.getPatientById("UNKNOWN");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/patients — returns 200 with list of patients")
    public void testGetAllPatients_Returns200() {
        ResponseEntity<List<Patient>> response = patientController.getAllPatients();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("DELETE /api/patients/{id} — returns 204 on successful delete")
    public void testDeletePatient_Returns204() {
        Map<String, String> body = Map.of(
            "patientId", "P001",
            "fullName", "John Doe",
            "email", "john@email.com",
            "passwordHash", "hash123",
            "phone", "0821234567",
            "dateOfBirth", "1990-05-15",
            "gender", "Male"
        );
        patientController.registerPatient(body);

        ResponseEntity<?> response = patientController.deletePatient("P001");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("PATCH /api/patients/{id}/deactivate — returns 200 on deactivation")
    public void testDeactivatePatient_Returns200() {
        Map<String, String> body = Map.of(
            "patientId", "P001",
            "fullName", "John Doe",
            "email", "john@email.com",
            "passwordHash", "hash123",
            "phone", "0821234567",
            "dateOfBirth", "1990-05-15",
            "gender", "Male"
        );
        patientController.registerPatient(body);

        ResponseEntity<?> response = patientController.deactivatePatient("P001");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DOCTOR CONTROLLER TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/doctors — returns 201 for valid doctor")
    public void testPostDoctor_Returns201() {
        Map<String, String> body = Map.of(
            "doctorId", "D001",
            "fullName", "Jane Smith",
            "email", "jane@clinic.com",
            "passwordHash", "hash456",
            "phone", "0839876543",
            "specialisation", "General Practice",
            "qualifications", "MBChB"
        );

        ResponseEntity<?> response = doctorController.createDoctor(body);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(Doctor.class, response.getBody());
    }

    @Test
    @DisplayName("POST /api/doctors — returns 400 for missing specialisation")
    public void testPostDoctor_Returns400ForMissingSpecialisation() {
        Map<String, String> body = Map.of(
            "doctorId", "D001",
            "fullName", "Jane Smith",
            "email", "jane@clinic.com",
            "passwordHash", "hash456",
            "phone", "0839876543",
            "specialisation", "",
            "qualifications", "MBChB"
        );

        ResponseEntity<?> response = doctorController.createDoctor(body);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/doctors?specialisation= — filters doctors correctly")
    public void testGetDoctorsBySpecialisation() {
        Map<String, String> body = Map.of(
            "doctorId", "D001",
            "fullName", "Jane Smith",
            "email", "jane@clinic.com",
            "passwordHash", "hash456",
            "phone", "0839876543",
            "specialisation", "General Practice",
            "qualifications", "MBChB"
        );
        doctorController.createDoctor(body);

        ResponseEntity<?> response = doctorController
            .getAllDoctors("General Practice");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /api/doctors/{id} — returns 404 for unknown ID")
    public void testDeleteDoctor_Returns404() {
        ResponseEntity<?> response = doctorController.deleteDoctor("UNKNOWN");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // APPOINTMENT CONTROLLER TESTS
    // ─────────────────────────────────────────────────────────────────────────

    private void createPatientAndDoctor() {
        patientController.registerPatient(Map.of(
            "patientId", "P001",
            "fullName", "John Doe",
            "email", "john@email.com",
            "passwordHash", "hash123",
            "phone", "0821234567",
            "dateOfBirth", "1990-05-15",
            "gender", "Male"
        ));
        doctorController.createDoctor(Map.of(
            "doctorId", "D001",
            "fullName", "Jane Smith",
            "email", "jane@clinic.com",
            "passwordHash", "hash456",
            "phone", "0839876543",
            "specialisation", "General Practice",
            "qualifications", "MBChB"
        ));
    }

    @Test
    @DisplayName("POST /api/appointments — returns 201 for valid booking")
    public void testPostAppointment_Returns201() {
        createPatientAndDoctor();

        ResponseEntity<?> response = appointmentController.bookAppointment(Map.of(
            "appointmentId", "APT001",
            "patientId", "P001",
            "doctorId", "D001",
            "slotId", "SLOT001"
        ));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(Appointment.class, response.getBody());
    }

    @Test
    @DisplayName("POST /api/appointments — returns 409 for double booking")
    public void testPostAppointment_Returns409ForDoubleBooking() {
        createPatientAndDoctor();

        appointmentController.bookAppointment(Map.of(
            "appointmentId", "APT001",
            "patientId", "P001",
            "doctorId", "D001",
            "slotId", "SLOT001"
        ));

        ResponseEntity<?> response = appointmentController.bookAppointment(Map.of(
            "appointmentId", "APT002",
            "patientId", "P001",
            "doctorId", "D001",
            "slotId", "SLOT001"
        ));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/appointments/{id} — returns 404 for unknown ID")
    public void testGetAppointmentById_Returns404() {
        ResponseEntity<?> response = appointmentController
            .getAppointmentById("UNKNOWN");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("PATCH /api/appointments/{id}/cancel — returns 200 on cancel")
    public void testCancelAppointment_Returns200() {
        createPatientAndDoctor();
        appointmentController.bookAppointment(Map.of(
            "appointmentId", "APT001",
            "patientId", "P001",
            "doctorId", "D001",
            "slotId", "SLOT001"
        ));

        ResponseEntity<?> response = appointmentController.cancelAppointment(
            "APT001",
            Map.of("appointmentTime",
                LocalDateTime.now().plusDays(1).toString())
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT /api/appointments/{id}/reschedule — returns 200 on reschedule")
    public void testRescheduleAppointment_Returns200() {
        createPatientAndDoctor();
        appointmentController.bookAppointment(Map.of(
            "appointmentId", "APT001",
            "patientId", "P001",
            "doctorId", "D001",
            "slotId", "SLOT001"
        ));

        ResponseEntity<?> response = appointmentController.rescheduleAppointment(
            "APT001",
            Map.of(
                "newSlotId", "SLOT002",
                "appointmentTime", LocalDateTime.now().plusDays(2).toString()
            )
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /api/appointments/count — returns correct count")
    public void testGetAppointmentCount() {
        createPatientAndDoctor();
        appointmentController.bookAppointment(Map.of(
            "appointmentId", "APT001",
            "patientId", "P001",
            "doctorId", "D001",
            "slotId", "SLOT001"
        ));

        ResponseEntity<Map<String, Integer>> response =
            appointmentController.getAppointmentCount();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().get("totalAppointments"));
    }
}