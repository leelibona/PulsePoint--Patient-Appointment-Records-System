package com.pulsepoint.models;

import java.time.LocalDateTime;

/**
 * Represents an available time slot for a doctor in PulsePoint.
 * A TimeSlot is owned by a Doctor and occupied by an Appointment when booked.
 */
public class TimeSlot implements Cloneable {

    private String slotId;
    private String doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAvailable;
    private String status; // Available, Reserved, Booked, Completed, NoShow

    public TimeSlot(String slotId, String doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        this.slotId = slotId;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true;
        this.status = "Available";
    }

    public void reserve() {
        if (!isAvailable) {
            System.out.println("Slot " + slotId + " is not available.");
            return;
        }
        this.isAvailable = false;
        this.status = "Reserved";
        System.out.println("Slot " + slotId + " reserved.");
    }

    public void book() {
        this.isAvailable = false;
        this.status = "Booked";
        System.out.println("Slot " + slotId + " booked.");
    }

    public void release() {
        this.isAvailable = true;
        this.status = "Available";
        System.out.println("Slot " + slotId + " released back to available.");
    }

    public void markCompleted() {
        this.status = "Completed";
        System.out.println("Slot " + slotId + " marked as completed.");
    }

    public boolean checkAvailability() {
        return isAvailable;
    }

    // Prototype pattern support — clone a TimeSlot
    @Override
    public TimeSlot clone() {
        try {
            TimeSlot cloned = (TimeSlot) super.clone();
            cloned.slotId = "SLOT-" + System.currentTimeMillis();
            cloned.isAvailable = true;
            cloned.status = "Available";
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    // Getters and Setters
    public String getSlotId() { return slotId; }
    public String getDoctorId() { return doctorId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isAvailable() { return isAvailable; }
    public String getStatus() { return status; }
    public void setSlotId(String slotId) { this.slotId = slotId; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "TimeSlot{slotId='" + slotId + "', doctorId='" + doctorId +
               "', startTime=" + startTime + ", status='" + status + "'}";
    }
}