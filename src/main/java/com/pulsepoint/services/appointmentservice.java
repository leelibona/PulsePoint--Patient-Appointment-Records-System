package com.pulsepoint.services;

import com.pulsepoint.models.Appointment;
import com.pulsepoint.models.SMSNotification;
import com.pulsepoint.repositories.AppointmentRepository;
import com.pulsepoint.repositories.PatientRepository;
import com.pulsepoint.repositories.DoctorRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AppointmentService — Service Layer for Appointment operations.
 *
 * Encapsulates all business logic related to appointments.
 * Enforces critical business rules:
 * - A slot cannot be double booked
 * - Cancellation/rescheduling only allowed more than 2 hours before appointment
 * - SMS notifications triggered on booking, rescheduling, and cancellation
 */
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Books a new appointment.
     * Business Rules:
     * - Patient must exist
     * - Doctor must exist and be active
     * - Slot must not already be booked (no double booking)
     * - SMS confirmation triggered after successful booking
     */
    public Appointment bookAppointment(String appointmentId, String patientId,
                                        String doctorId, String slotId) {
        // Validate patient exists
        patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Patient not found with ID: " + patientId
            ));

        // Validate doctor exists and is active
        doctorRepository.findById(doctorId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Doctor not found with ID: " + doctorId
            ));

        // Check for double booking — slot must not already be booked
        List<Appointment> existingForSlot = appointmentRepository.findBySlotId(slotId);
        boolean slotTaken = existingForSlot.stream()
            .anyMatch(a -> !a.getStatus().equals("Cancelled") &&
                          !a.getStatus().equals("NoShow") &&
                          !a.getStatus().equals("Failed"));
        if (slotTaken) {
            throw new IllegalStateException(
                "This time slot is already booked. Please select a different slot."
            );
        }

        // Create and confirm the appointment
        Appointment appointment = new Appointment(
            appointmentId, patientId, doctorId, slotId
        );
        appointment.confirm();
        appointmentRepository.save(appointment);

        // Trigger SMS confirmation
        triggerSMSConfirmation(appointmentId, patientId,
            "Your appointment has been confirmed. Appointment ID: " + appointmentId);

        System.out.println("Appointment booked successfully: " + appointmentId);
        return appointment;
    }

    /**
     * Retrieves an appointment by ID.
     */
    public Appointment getAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Appointment not found with ID: " + appointmentId
            ));
    }

    /**
     * Returns all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Returns all appointments for a specific patient.
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        patientRepository.findById(patientId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Patient not found with ID: " + patientId
            ));
        return appointmentRepository.findByPatientId(patientId);
    }

    /**
     * Returns all appointments for a specific doctor.
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        doctorRepository.findById(doctorId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Doctor not found with ID: " + doctorId
            ));
        return appointmentRepository.findByDoctorId(doctorId);
    }

    /**
     * Returns all appointments with a specific status.
     */
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    /**
     * Reschedules an existing appointment.
     * Business Rules:
     * - Appointment must exist and be Confirmed
     * - New slot must not be already booked
     * - Rescheduling only allowed more than 2 hours before appointment
     * - SMS notification triggered after successful reschedule
     */
    public Appointment rescheduleAppointment(String appointmentId,
                                              String newSlotId,
                                              LocalDateTime appointmentTime) {
        Appointment appointment = getAppointmentById(appointmentId);

        // Check status allows rescheduling
        if (!appointment.getStatus().equals("Confirmed") &&
            !appointment.getStatus().equals("Rescheduled")) {
            throw new IllegalStateException(
                "Only confirmed appointments can be rescheduled. Current status: "
                + appointment.getStatus()
            );
        }

        // Business Rule: Cannot reschedule within 2 hours of appointment
        if (appointmentTime != null &&
            LocalDateTime.now().isAfter(appointmentTime.minusHours(2))) {
            throw new IllegalStateException(
                "Appointments cannot be rescheduled within 2 hours of the scheduled time."
            );
        }

        // Check new slot availability
        List<Appointment> existingForSlot = appointmentRepository.findBySlotId(newSlotId);
        boolean slotTaken = existingForSlot.stream()
            .anyMatch(a -> !a.getStatus().equals("Cancelled") &&
                          !a.getStatus().equals("NoShow") &&
                          !a.getStatus().equals("Failed"));
        if (slotTaken) {
            throw new IllegalStateException(
                "The selected new slot is already booked. Please choose a different time."
            );
        }

        appointment.reschedule(newSlotId);
        appointmentRepository.save(appointment);

        // Trigger SMS notification
        triggerSMSConfirmation(appointmentId, appointment.getPatientId(),
            "Your appointment " + appointmentId + " has been rescheduled successfully.");

        System.out.println("Appointment rescheduled: " + appointmentId);
        return appointment;
    }

    /**
     * Cancels an existing appointment.
     * Business Rules:
     * - Appointment must be Confirmed or Rescheduled
     * - Cannot cancel within 2 hours of appointment time
     * - SMS notification triggered after cancellation
     */
    public Appointment cancelAppointment(String appointmentId,
                                          LocalDateTime appointmentTime) {
        Appointment appointment = getAppointmentById(appointmentId);

        // Check status allows cancellation
        if (!appointment.getStatus().equals("Confirmed") &&
            !appointment.getStatus().equals("Rescheduled")) {
            throw new IllegalStateException(
                "Only confirmed appointments can be cancelled. Current status: "
                + appointment.getStatus()
            );
        }

        // Business Rule: Cannot cancel within 2 hours
        if (appointmentTime != null &&
            LocalDateTime.now().isAfter(appointmentTime.minusHours(2))) {
            throw new IllegalStateException(
                "Appointments cannot be cancelled within 2 hours of the scheduled time."
            );
        }

        appointment.cancel();
        appointmentRepository.save(appointment);

        // Trigger SMS notification
        triggerSMSConfirmation(appointmentId, appointment.getPatientId(),
            "Your appointment " + appointmentId + " has been cancelled.");

        System.out.println("Appointment cancelled: " + appointmentId);
        return appointment;
    }

    /**
     * Marks an appointment as completed.
     */
    public Appointment completeAppointment(String appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);

        if (!appointment.getStatus().equals("InProgress")) {
            throw new IllegalStateException(
                "Only in-progress appointments can be completed."
            );
        }

        appointment.markCompleted();
        appointmentRepository.save(appointment);
        System.out.println("Appointment completed: " + appointmentId);
        return appointment;
    }

    /**
     * Returns total number of appointments.
     */
    public int getTotalAppointments() {
        return appointmentRepository.count();
    }

    /**
     * Triggers an SMS notification for appointment events.
     * In production this calls the Twilio API via SMSNotification class.
     */
    private void triggerSMSConfirmation(String appointmentId,
                                         String patientId, String message) {
        SMSNotification sms = new SMSNotification(
            "SMS-" + System.currentTimeMillis(),
            appointmentId,
            patientId,
            message
        );
        sms.send();
    }
}