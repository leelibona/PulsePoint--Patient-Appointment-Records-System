package com.pulsepoint.models;

import java.time.LocalDateTime;

/**
 * Base class for all users in the PulsePoint system.
 * Patient, Doctor, and Administrator all extend this class.
 */
public abstract class User {

    private String userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private String phone;
    private String role;
    private String status; // Registered, Active, Inactive, Deactivated, Deleted
    private LocalDateTime createdAt;
    private boolean isActive;

    public User(String userId, String fullName, String email, String passwordHash, String phone, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.role = role;
        this.status = "Registered";
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    public boolean login(String email, String passwordHash) {
        if (!isActive) {
            System.out.println("Account is deactivated. Cannot log in.");
            return false;
        }
        if (this.email.equals(email) && this.passwordHash.equals(passwordHash)) {
            this.status = "Active";
            System.out.println(fullName + " logged in successfully.");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public void logout() {
        this.status = "Inactive";
        System.out.println(fullName + " logged out.");
    }

    public void resetPassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        System.out.println("Password reset successfully for " + fullName);
    }

    public void deactivateAccount() {
        this.isActive = false;
        this.status = "Deactivated";
        System.out.println("Account deactivated for " + fullName);
    }

    public void reactivateAccount() {
        this.isActive = true;
        this.status = "Active";
        System.out.println("Account reactivated for " + fullName);
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return isActive; }

    public void setStatus(String status) { this.status = status; }
    public void setActive(boolean active) { this.isActive = active; }

    @Override
    public String toString() {
        return "User{userId='" + userId + "', fullName='" + fullName +
               "', email='" + email + "', role='" + role + "', status='" + status + "'}";
    }
}