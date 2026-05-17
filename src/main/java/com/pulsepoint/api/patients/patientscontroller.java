package com.pulsepoint.api.controllers;

import com.pulsepoint.models.Patient;
import com.pulsepoint.services.PatientService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * PatientController — REST API for Patient operations.
 *
 * Base URL: /api/patients
 *
 * Endpoints:
 * GET    /api/patients           — Get all patients
 * GET    /api/patients/{id}      — Get patient by ID
 * POST   /api/patients           — Register a new patient
 * PUT    /api/patients/{id}      — Update patient profile
 * DELETE /api/patients/{id}      — Delete a patient
 * PATCH  /api/patients/{id}/deactivate — Deactivate a patient
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    // Constructor injection
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * GET /api/patients
     * Returns all registered patients.
     *
     * @return 200 OK with list of patients
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * GET /api/patients/{id}
     * Returns a single patient by ID.
     *
     * @param id Patient ID
     * @return 200 OK with patient, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable String id) {
        try {
            Patient patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/patients
     * Registers a new patient.
     *
     * Request body:
     * {
     *   "patientId": "P001",
     *   "fullName": "John Doe",
     *   "email": "john@email.com",
     *   "passwordHash": "hashed_password",
     *   "phone": "0821234567",
     *   "dateOfBirth": "1990-05-15",
     *   "gender": "Male"
     * }
     *
     * @return 201 Created with new patient, or 400/409 on error
     */
    @PostMapping
    public ResponseEntity<?> registerPatient(@RequestBody Map<String, String> body) {
        try {
            Patient patient = patientService.registerPatient(
                body.get("patientId"),
                body.get("fullName"),
                body.get("email"),
                body.get("passwordHash"),
                body.get("phone"),
                LocalDate.parse(body.get("dateOfBirth")),
                body.get("gender")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(patient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PUT /api/patients/{id}
     * Updates a patient's profile information.
     *
     * Request body:
     * {
     *   "phone": "0829876543",
     *   "gender": "Male"
     * }
     *
     * @return 200 OK with updated patient, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable String id,
                                            @RequestBody Map<String, String> body) {
        try {
            Patient updated = patientService.updatePatient(
                id,
                body.get("phone"),
                body.get("gender")
            );
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PATCH /api/patients/{id}/deactivate
     * Deactivates a patient account.
     *
     * @return 200 OK with success message, or 404 if not found
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivatePatient(@PathVariable String id) {
        try {
            patientService.deactivatePatient(id);
            return ResponseEntity.ok(
                Map.of("message", "Patient account deactivated successfully.")
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/patients/{id}
     * Permanently deletes a patient record.
     *
     * @return 204 No Content, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable String id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/patients/count
     * Returns total number of registered patients.
     *
     * @return 200 OK with count
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getPatientCount() {
        return ResponseEntity.ok(
            Map.of("totalPatients", patientService.getTotalPatients())
        );
    }
}