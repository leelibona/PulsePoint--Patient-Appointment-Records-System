package com.pulsepoint;

import com.pulsepoint.creational_patterns.simple_factory.UserFactory;
import com.pulsepoint.creational_patterns.factory_method.*;
import com.pulsepoint.creational_patterns.abstract_factory.*;
import com.pulsepoint.creational_patterns.builder.*;
import com.pulsepoint.creational_patterns.prototype.*;
import com.pulsepoint.creational_patterns.singleton.DatabaseConnection;
import com.pulsepoint.models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Unit Tests for all 6 Creational Design Patterns in PulsePoint.
 *
 * Tests verify:
 * - Correct object creation for each pattern
 * - Proper initialization of attributes
 * - Edge cases (invalid inputs, thread safety, missing fields)
 *
 * Test Framework: JUnit 5 (Jupiter)
 * Total Tests: 27
 */
public class CreationalPatternsTest {

    // ─────────────────────────────────────────────────────────────────────────
    // 1. SIMPLE FACTORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Simple Factory — Creates a Patient object with correct role")
    public void testSimpleFactory_CreatesPatient() {
        User user = UserFactory.createUser(
            "Patient", "P001", "John Doe",
            "john@example.com", "hashedpassword123", "0821234567"
        );

        assertNotNull(user, "User should not be null");
        assertInstanceOf(Patient.class, user, "User should be a Patient instance");
        assertEquals("John Doe", user.getFullName());
        assertEquals("Patient", user.getRole());
    }

    @Test
    @DisplayName("Simple Factory — Creates a Doctor object with correct role")
    public void testSimpleFactory_CreatesDoctor() {
        User user = UserFactory.createUser(
            "Doctor", "D001", "Dr. Smith",
            "smith@clinic.com", "hashedpassword456", "0839876543"
        );

        assertNotNull(user);
        assertInstanceOf(Doctor.class, user);
        assertEquals("Doctor", user.getRole());
        assertEquals("Dr. Smith", user.getFullName());
    }

    @Test
    @DisplayName("Simple Factory — Creates an Administrator object with correct role")
    public void testSimpleFactory_CreatesAdministrator() {
        User user = UserFactory.createUser(
            "Administrator", "A001", "Admin User",
            "admin@pulsepoint.com", "adminpass789", "0801112233"
        );

        assertNotNull(user);
        assertInstanceOf(Administrator.class, user);
        assertEquals("Administrator", user.getRole());
    }

    @Test
    @DisplayName("Simple Factory — Throws IllegalArgumentException for unknown role")
    public void testSimpleFactory_ThrowsExceptionForUnknownRole() {
        assertThrows(IllegalArgumentException.class, () ->
            UserFactory.createUser(
                "SuperAdmin", "X001", "Unknown",
                "unknown@test.com", "pass", "0800000000"
            ),
            "Should throw IllegalArgumentException for unknown role"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. FACTORY METHOD TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Factory Method — SMS sender creates notification of type SMS")
    public void testFactoryMethod_SMSSenderCreatesCorrectType() {
        NotificationSender sender = new SMSNotificationSender();
        Notification notification = sender.createNotification(
            "APT001", "0821234567", "Reminder: Appointment tomorrow at 9AM"
        );

        assertNotNull(notification);
        assertEquals("SMS", notification.getType());
    }

    @Test
    @DisplayName("Factory Method — Email sender creates notification of type Email")
    public void testFactoryMethod_EmailSenderCreatesCorrectType() {
        NotificationSender sender = new EmailNotificationSender();
        Notification notification = sender.createNotification(
            "APT002", "patient@email.com", "Your appointment is confirmed"
        );

        assertNotNull(notification);
        assertEquals("Email", notification.getType());
    }

    @Test
    @DisplayName("Factory Method — SMS notification status is Delivered after send()")
    public void testFactoryMethod_SMSStatusAfterSend() {
        NotificationSender sender = new SMSNotificationSender();
        Notification notification = sender.createNotification(
            "APT003", "0829998877", "Test message"
        );

        notification.send();
        assertEquals("Delivered", notification.getStatus());
    }

    @Test
    @DisplayName("Factory Method — SMS and Email senders are different types")
    public void testFactoryMethod_SendersAreDistinctTypes() {
        NotificationSender smsSender = new SMSNotificationSender();
        NotificationSender emailSender = new EmailNotificationSender();

        assertNotSame(smsSender, emailSender);
        assertNotEquals(
            smsSender.getClass().getSimpleName(),
            emailSender.getClass().getSimpleName()
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. ABSTRACT FACTORY TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Abstract Factory — Patient report factory generates a PDF report")
    public void testAbstractFactory_PatientReportGenerates() {
        ReportFactory factory = ReportGenerator.getFactory("Patient");
        ReportGenerator generator = new ReportGenerator(factory);

        String result = generator.generateReport();
        assertNotNull(result, "Generated report file should not be null");
        assertTrue(result.contains("patient"),
            "Patient report filename should contain 'patient'");
    }

    @Test
    @DisplayName("Abstract Factory — Financial report factory generates a CSV report")
    public void testAbstractFactory_FinancialReportGenerates() {
        ReportFactory factory = ReportGenerator.getFactory("Financial");
        ReportGenerator generator = new ReportGenerator(factory);

        String result = generator.generateReport();
        assertNotNull(result);
        assertTrue(result.contains("financial"),
            "Financial report filename should contain 'financial'");
    }

    @Test
    @DisplayName("Abstract Factory — Unknown report type throws IllegalArgumentException")
    public void testAbstractFactory_UnknownTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            ReportGenerator.getFactory("Insurance")
        );
    }

    @Test
    @DisplayName("Abstract Factory — Patient and Financial factories are different instances")
    public void testAbstractFactory_FactoriesAreDistinct() {
        ReportFactory patientFactory = ReportGenerator.getFactory("Patient");
        ReportFactory financialFactory = ReportGenerator.getFactory("Financial");

        assertNotSame(patientFactory, financialFactory);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. BUILDER TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Builder — Creates appointment with all required fields")
    public void testBuilder_CreatesAppointmentWithRequiredFields() {
        AppointmentProduct appointment = new AppointmentBuilder(
            "APT001", "P001", "D001", "SLOT001"
        ).build();

        assertNotNull(appointment);
        assertEquals("APT001", appointment.getAppointmentId());
        assertEquals("P001", appointment.getPatientId());
        assertEquals("D001", appointment.getDoctorId());
        assertEquals("SLOT001", appointment.getSlotId());
    }

    @Test
    @DisplayName("Builder — Default status is Pending when not specified")
    public void testBuilder_DefaultStatusIsPending() {
        AppointmentProduct appointment = new AppointmentBuilder(
            "APT002", "P002", "D002", "SLOT002"
        ).build();

        assertEquals("Pending", appointment.getStatus());
    }

    @Test
    @DisplayName("Builder — Optional fields are set correctly using method chaining")
    public void testBuilder_OptionalFieldsSetCorrectly() {
        AppointmentProduct appointment = new AppointmentBuilder(
            "APT003", "P003", "D003", "SLOT003"
        )
        .withStatus("Confirmed")
        .withNotes("Patient has diabetes")
        .withConsultationType("Virtual")
        .withSmsReminder(false)
        .withAppointmentDate(LocalDateTime.of(2026, 5, 15, 9, 0))
        .build();

        assertEquals("Confirmed", appointment.getStatus());
        assertEquals("Patient has diabetes", appointment.getNotes());
        assertEquals("Virtual", appointment.getConsultationType());
        assertFalse(appointment.isSmsReminderEnabled());
        assertEquals(LocalDateTime.of(2026, 5, 15, 9, 0), appointment.getAppointmentDate());
    }

    @Test
    @DisplayName("Builder — Throws IllegalStateException when required fields are null")
    public void testBuilder_ThrowsExceptionWhenRequiredFieldsMissing() {
        assertThrows(IllegalStateException.class, () ->
            new AppointmentBuilder(null, "P001", "D001", "SLOT001").build()
        );
    }

    @Test
    @DisplayName("Builder — SMS reminder is enabled by default")
    public void testBuilder_SmsReminderEnabledByDefault() {
        AppointmentProduct appointment = new AppointmentBuilder(
            "APT004", "P004", "D004", "SLOT004"
        ).build();

        assertTrue(appointment.isSmsReminderEnabled());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. PROTOTYPE TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Prototype — Cloned slots are different objects from the template")
    public void testPrototype_CloneIsNewObject() {
        TimeSlotPrototype slot1 = TimeSlotCache.getSlot("Morning30", "D001");
        TimeSlotPrototype slot2 = TimeSlotCache.getSlot("Morning30", "D001");

        assertNotSame(slot1, slot2,
            "Cloned slots should be different objects in memory");
    }

    @Test
    @DisplayName("Prototype — Each cloned slot has a unique ID")
    public void testPrototype_CloneHasUniqueId() throws InterruptedException {
        TimeSlotPrototype slot1 = TimeSlotCache.getSlot("Morning30", "D001");
        Thread.sleep(2);
        TimeSlotPrototype slot2 = TimeSlotCache.getSlot("Morning30", "D002");

        assertNotEquals(slot1.getSlotId(), slot2.getSlotId(),
            "Each cloned slot should have a unique ID");
    }

    @Test
    @DisplayName("Prototype — Cloned slot is available by default")
    public void testPrototype_CloneIsAvailable() {
        TimeSlotPrototype slot = TimeSlotCache.getSlot("Afternoon30", "D002");

        assertTrue(slot.isAvailable(), "Cloned slot should be available");
        assertEquals("Available", slot.getStatus());
    }

    @Test
    @DisplayName("Prototype — Cloned slot has the correct doctor ID assigned")
    public void testPrototype_CloneHasCorrectDoctorId() {
        TimeSlotPrototype slot = TimeSlotCache.getSlot("Evening30", "D003");

        assertEquals("D003", slot.getDoctorId());
    }

    @Test
    @DisplayName("Prototype — Unknown template type throws IllegalArgumentException")
    public void testPrototype_UnknownTemplateThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            TimeSlotCache.getSlot("Night60", "D001")
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. SINGLETON TESTS
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Singleton — Returns the exact same instance on every call")
    public void testSingleton_ReturnsSameInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        assertSame(instance1, instance2,
            "Both calls should return the exact same DatabaseConnection instance");
    }

    @Test
    @DisplayName("Singleton — Instance is not null")
    public void testSingleton_InstanceIsNotNull() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertNotNull(instance);
    }

    @Test
    @DisplayName("Singleton — Database is connected after initialization")
    public void testSingleton_IsConnectedAfterInit() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        assertTrue(db.isConnected(),
            "Database should be connected after initialization");
    }

    @Test
    @DisplayName("Singleton — Query count increments correctly after each query")
    public void testSingleton_QueryCountIncrements() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        int initialCount = db.getQueryCount();

        db.executeQuery("SELECT * FROM appointments");
        db.executeQuery("SELECT * FROM patients");

        assertEquals(initialCount + 2, db.getQueryCount(),
            "Query count should increment by 2 after two queries");
    }

    @Test
    @DisplayName("Singleton — Thread safety: 10 concurrent threads get the same instance")
    public void testSingleton_ThreadSafety() throws InterruptedException {
        DatabaseConnection[] instances = new DatabaseConnection[10];

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threads[i] = new Thread(() ->
                instances[index] = DatabaseConnection.getInstance()
            );
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        for (int i = 1; i < 10; i++) {
            assertSame(instances[0], instances[i],
                "All threads should get the same DatabaseConnection instance");
        }
    }
}