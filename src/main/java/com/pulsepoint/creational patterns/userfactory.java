package com.pulsepoint.creational_patterns.simple_factory;

import com.pulsepoint.models.User;
import com.pulsepoint.models.Patient;
import com.pulsepoint.models.Doctor;
import com.pulsepoint.models.Administrator;
import java.time.LocalDate;

/**
 * Simple Factory Pattern — PulsePoint User Factory
 *
 * Purpose: Centralises the creation of User objects (Patient, Doctor, Administrator).
 * Instead of calling constructors directly throughout the codebase, all user creation
 * goes through this single factory class.
 *
 * Use Case in PulsePoint: When a new user registers or is created by an admin,
 * the factory determines which type of User object to instantiate based on the role.
 */
public class UserFactory {

    /**
     * Creates and returns a User object based on the specified role.
     *
     * @param role     The role of the user: "Patient", "Doctor", or "Administrator"
     * @param userId   Unique identifier for the user
     * @param fullName Full name of the user
     * @param email    Email address of the user
     * @param password Password hash of the user
     * @param phone    Phone number of the user
     * @return A User object of the appropriate subtype
     */
    public static User createUser(String role, String userId, String fullName,
                                   String email, String password, String phone) {
        switch (role) {
            case "Patient":
                return new Patient(
                    userId, fullName, email, password, phone,
                    LocalDate.of(1990, 1, 1), "Unknown"
                );
            case "Doctor":
                return new Doctor(
                    userId, fullName, email, password, phone,
                    "General Practice", "MBChB"
                );
            case "Administrator":
                return new Administrator(
                    userId, fullName, email, password, phone
                );
            default:
                throw new IllegalArgumentException("Unknown user role: " + role);
        }
    }
}