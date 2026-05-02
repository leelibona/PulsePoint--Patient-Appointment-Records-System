package com.pulsepoint.creational_patterns.factory_method;

/**
 * Factory Method Pattern — Notification Sender
 *
 * Purpose: Defines an interface for creating notification objects,
 * but lets subclasses decide which type of notification to create.
 *
 * Use Case in PulsePoint: The system supports different types of notifications
 * (SMS, Email). Each notification type has its own factory that handles creation.
 */
public interface NotificationSender {

    /**
     * Factory method — subclasses implement this to create specific notification types.
     */
    Notification createNotification(String appointmentId, String recipientContact, String message);

    /**
     * Sends the notification using the created notification object.
     */
    default void sendNotification(String appointmentId, String recipientContact, String message) {
        Notification notification = createNotification(appointmentId, recipientContact, message);
        notification.send();
    }
}