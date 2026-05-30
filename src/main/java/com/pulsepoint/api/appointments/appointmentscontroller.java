package com.pulsepoint.api.controllers;

import com.pulsepoint.models.Appointment;
import com.pulsepoint.services.AppointmentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * AppointmentController — REST API for Appointment operations.
 *
 * Base URL: /api/appointments
 *
 * Endpoints:
 * GET    /api/appointments                      — Get all appointments
 * GET    /api/appointments/{id}                 — Get appointment by ID
 * GET    /api/appointments?patientId={id}       — Get by patient
 * GET    /api/appointments?doctorId={id}        — Get by doctor
 * GET    /api/appointments?status={status}      — Get by status
 * POST   /api/appointments                      — Book a new appointment
 * PUT    /api/appointments/{id}/reschedule      — Reschedule appointment
 * PATCH  /api/appointments/{id}/cancel         — Cancel appointment
 * PATCH  /api/appointments/{id}/complete       — Complete appointment
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * GET /api/appointments
     * Returns all appointments, with optional filters.
     *
     * Query params:
     * - patientId: filter by patient
     * - doctorId: filter by doctor
     * - status: filter by status (e.g. Confirmed, Cancelled)
     *
     * @return 200 OK with list of appointments
     */
    @GetMapping
    public ResponseEntity<?> getAllAppointments(
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false) String status) {
        try {
            if (patientId != null) {
                return ResponseEntity.ok(
                    appointmentService.getAppointmentsByPatient(patientId)
                );
            }
            if (doctorId != null) {
                return ResponseEntity.ok(
                    appointmentService.getAppointmentsByDoctor(doctorId)
                );
            }
            if (status != null) {
                return ResponseEntity.ok(
                    appointmentService.getAppointmentsByStatus(status)
                );
            }
            return ResponseEntity.ok(appointmentService.getAllAppointments());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/appointments/{id}
     * Returns a single appointment by ID.
     *
     * @return 200 OK with appointment, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable String id) {
        try {
            Appointment appointment = appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(appointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /api/appointments
     * Books a new appointment.
     *
     * Request body:
     * {
     *   "appointmentId": "APT001",
     *   "patientId": "P001",
     *   "doctorId": "D001",
     *   "slotId": "SLOT001"
     * }
     *
     * @return 201 Created, or 400 if invalid, 409 if slot taken
     */
    @PostMapping
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, String> body) {
        try {
            Appointment appointment = appointmentService.bookAppointment(
                body.get("appointmentId"),
                body.get("patientId"),
                body.get("doctorId"),
                body.get("slotId")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PUT /api/appointments/{id}/reschedule
     * Reschedules an existing appointment to a new slot.
     *
     * Request body:
     * {
     *   "newSlotId": "SLOT002",
     *   "appointmentTime": "2026-06-15T09:00:00"
     * }
     *
     * @return 200 OK with rescheduled appointment, or 400/404/409 on error
     */
    @PutMapping("/{id}/reschedule")
    public ResponseEntity<?> rescheduleAppointment(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        try {
            LocalDateTime appointmentTime = body.get("appointmentTime") != null
                ? LocalDateTime.parse(body.get("appointmentTime"))
                : null;

            Appointment rescheduled = appointmentService.rescheduleAppointment(
                id,
                body.get("newSlotId"),
                appointmentTime
            );
            return ResponseEntity.ok(rescheduled);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PATCH /api/appointments/{id}/cancel
     * Cancels an existing appointment.
     *
     * Request body:
     * {
     *   "appointmentTime": "2026-06-15T09:00:00"
     * }
     *
     * @return 200 OK with cancelled appointment, or 400/404/409 on error
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        try {
            LocalDateTime appointmentTime = body.get("appointmentTime") != null
                ? LocalDateTime.parse(body.get("appointmentTime"))
                : null;

            Appointment cancelled = appointmentService.cancelAppointment(
                id, appointmentTime
            );
            return ResponseEntity.ok(cancelled);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PATCH /api/appointments/{id}/complete
     * Marks an appointment as completed.
     *
     * @return 200 OK with completed appointment, or 400/404 on error
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(@PathVariable String id) {
        try {
            Appointment completed = appointmentService.completeAppointment(id);
            return ResponseEntity.ok(completed);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * GET /api/appointments/count
     * Returns total number of appointments.
     *
     * @return 200 OK with count
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getAppointmentCount() {
        return ResponseEntity.ok(
            Map.of("totalAppointments", appointmentService.getTotalAppointments())
        );
    }
}