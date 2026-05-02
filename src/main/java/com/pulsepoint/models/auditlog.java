package com.pulsepoint.models;

import java.time.LocalDateTime;

/**
 * Represents an audit log entry in the PulsePoint system.
 * Created whenever a medical record is accessed or modified.
 * Immutable — cannot be modified or deleted once created.
 */
public class AuditLog {

    private final String logId;
    private final String userId;
    private final String action;
    private final String affectedRecordId;
    private final LocalDateTime timestamp;
    private final String ipAddress;
    private String status; // Created, Stored, UnderReview, Cleared, Flagged, Escalated, Retained, Archived

    public AuditLog(String logId, String userId, String action,
                    String affectedRecordId, String ipAddress) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.affectedRecordId = affectedRecordId;
        this.timestamp = LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.status = "Created";
    }

    public void store() {
        this.status = "Stored";
        System.out.println("Audit log " + logId + " stored successfully.");
    }

    public void archive() {
        this.status = "Archived";
        System.out.println("Audit log " + logId + " archived after 5-year retention period.");
    }

    public void flag(String reason) {
        this.status = "Flagged";
        System.out.println("Audit log " + logId + " flagged: " + reason);
    }

    public void escalate() {
        this.status = "Escalated";
        System.out.println("Audit log " + logId + " escalated for investigation.");
    }

    public void markRetained() {
        this.status = "Retained";
        System.out.println("Audit log " + logId + " marked as retained.");
    }

    // Getters — no setters to enforce immutability
    public String getLogId() { return logId; }
    public String getUserId() { return userId; }
    public String getAction() { return action; }
    public String getAffectedRecordId() { return affectedRecordId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getIpAddress() { return ipAddress; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "AuditLog{logId='" + logId + "', userId='" + userId +
               "', action='" + action + "', affectedRecordId='" + affectedRecordId +
               "', timestamp=" + timestamp + ", status='" + status + "'}";
    }
}