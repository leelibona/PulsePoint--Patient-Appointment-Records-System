package com.pulsepoint.models;

import java.time.LocalDateTime;

/**
 * Represents an SMS notification in the PulsePoint system.
 * Triggered by an Appointment and sent to a Patient via Twilio.
 */
public class SMSNotification {

    private String notificationId;
    private String appointmentId;
    private String recipientPhone;
    private String message;
    private String status; // Queued, Sending, Delivered, Failed, Retrying, PermanentlyFailed
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private int retryCount;

    public SMSNotification(String notificationId, String appointmentId,
                           String recipientPhone, String message) {
        this.notificationId = notificationId;
        this.appointmentId = appointmentId;
        this.recipientPhone = recipientPhone;
        this.message = message;
        this.status = "Queued";
        this.scheduledAt = LocalDateTime.now();
        this.retryCount = 0;
    }

    public boolean send() {
        this.status = "Sending";
        System.out.println("Sending SMS to " + recipientPhone + ": " + message);

        // Simulate SMS sending via Twilio
        boolean success = simulateTwilioSend();
        if (success) {
            this.status = "Delivered";
            this.sentAt = LocalDateTime.now();
            System.out.println("SMS delivered successfully to " + recipientPhone);
            return true;
        } else {
            this.status = "Failed";
            System.out.println("SMS delivery failed for " + recipientPhone);
            return false;
        }
    }

    public boolean retry() {
        if (retryCount >= 1) {
            this.status = "PermanentlyFailed";
            System.out.println("SMS permanently failed after retry for " + recipientPhone);
            return false;
        }
        this.retryCount++;
        this.status = "Retrying";
        System.out.println("Retrying SMS delivery to " + recipientPhone + " (attempt " + retryCount + ")");
        return send();
    }

    public void logDeliveryStatus(String status) {
        this.status = status;
        System.out.println("SMS " + notificationId + " status logged: " + status);
    }

    private boolean simulateTwilioSend() {
        // Simulates a successful delivery — in production this calls the Twilio API
        return true;
    }

    // Getters
    public String getNotificationId() { return notificationId; }
    public String getAppointmentId() { return appointmentId; }
    public String getRecipientPhone() { return recipientPhone; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public LocalDateTime getSentAt() { return sentAt; }
    public int getRetryCount() { return retryCount; }

    @Override
    public String toString() {
        return "SMSNotification{notificationId='" + notificationId + "', recipientPhone='" +
               recipientPhone + "', status='" + status + "', retryCount=" + retryCount + "}";
    }
}