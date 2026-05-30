package com.pulsepoint.api.controllers;

import com.pulsepoint.models.Doctor;
import com.pulsepoint.services.DoctorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * DoctorController — REST API for Doctor operations.
 *
 * Base URL: /api/doctors
 *
 * Endpoints:
 * GET    /api/doctors                        — Get all doctors
 * GET    /api/doctors/{id}                   — Get doctor by ID
 * GET    /api/doctors?specialisation={spec}  — Get doctors by specialisation
 * POST   /api/doctors                        — Create a new doctor
 * PUT    /api/doctors/{id}                   — Update doctor details
 * DELETE /api/doctors/{id}                   — Delete a doctor
 * PATCH  /api/doctors/{id}/deactivate        — Deactivate a doctor
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * GET /api/doctors
     * Returns all doctors, optionally filtered by specialisation.
     *
     * @param specialisation Optional query parameter to filter by specialisation
     * @return 200 OK with list of doctors
     */
    @GetMapping
    public ResponseEntity<?> getAllDoctors(
            @RequestParam(required = false) String specialisation) {
        try {
            if (specialisation != null && !specialisation.isEmpty()) {
                List<Doctor> doctors = doctorService.getDoctorsBySpecialisation(specialisation);
                return ResponseEntity.ok(doctors);
            }
            return ResponseEntity.ok(doctorService.getAllDoctors());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/doctors/{id}
     * Returns a single doctor by ID.
     *
     * @return 200 OK with doctor, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable String id) {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.ok(doctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/doctors
     * Creates a new doctor account.
     *
     * Request body:
     * {
     *   "doctorId": "D001",
     *   "fullName": "Jane Smith",
     *   "email": "jane@clinic.com",
     *   "passwordHash": "hashed_password",
     *   "phone": "0839876543",
     *   "specialisation": "General Practice",
     *   "qualifications": "MBChB"
     * }
     *
     * @return 201 Created with new doctor, or 400/409 on error
     */
    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody Map<String, String> body) {
        try {
            Doctor doctor = doctorService.createDoctor(
                body.get("doctorId"),
                body.get("fullName"),
                body.get("email"),
                body.get("passwordHash"),
                body.get("phone"),
                body.get("specialisation"),
                body.get("qualifications")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PUT /api/doctors/{id}
     * Updates a doctor's details.
     *
     * @return 200 OK with updated doctor, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable String id,
                                           @RequestBody Map<String, String> body) {
        try {
            Doctor updated = doctorService.updateDoctor(
                id,
                body.get("phone"),
                body.get("specialisation"),
                body.get("qualifications")
            );
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PATCH /api/doctors/{id}/deactivate
     * Deactivates a doctor account.
     *
     * @return 200 OK with success message, or 404 if not found
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateDoctor(@PathVariable String id) {
        try {
            doctorService.deactivateDoctor(id);
            return ResponseEntity.ok(
                Map.of("message", "Doctor account deactivated successfully.")
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/doctors/{id}
     * Permanently deletes a doctor account.
     *
     * @return 204 No Content, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/doctors/count
     * Returns total number of registered doctors.
     *
     * @return 200 OK with count
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getDoctorCount() {
        return ResponseEntity.ok(
            Map.of("totalDoctors", doctorService.getTotalDoctors())
        );
    }
}