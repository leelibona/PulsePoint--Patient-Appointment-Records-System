# REQUIREMENTS.md — PulsePoint Patient Appointment & Records System

---

## 1. Overview

This document defines the functional and non-functional requirements for the PulsePoint system. All requirements are traceable to stakeholder needs identified in STAKEHOLDERS.md and are written as clear, specific, and actionable statements.

---

## 2. Functional Requirements

Functional requirements describe what the system shall do. Each requirement includes an acceptance criterion to define when it is considered complete.

---

### FR-01 — User Registration

**Requirement:** The system shall allow patients to self-register by providing their full name, email address, phone number, date of birth, and gender. Doctors shall be registered exclusively by the Administrator.

**Stakeholder:** Patient, Administrator

**Acceptance Criteria:**
- A patient can complete registration in under 2 minutes
- The system rejects duplicate email addresses with a clear error message
- A confirmation message is displayed upon successful registration
- Doctor accounts can only be created from the Admin dashboard

---

### FR-02 — User Authentication and Role-Based Access

**Requirement:** The system shall authenticate all users via email and password. Upon login, the system shall redirect each user to their role-specific dashboard ,  Patient Portal, Doctor Dashboard, or Admin Control Panel.

**Stakeholder:** Patient, Doctor, Administrator, IT Support Staff

**Acceptance Criteria:**
- Users with invalid credentials receive an error message and are not granted access
- JWT tokens expire after 24 hours, requiring re-login
- Each role can only access features and data permitted for that role
- A patient cannot access doctor or admin pages under any circumstance

---

### FR-03 — Appointment Booking

**Requirement:** The system shall allow patients to book an appointment by selecting a doctor, choosing an available date, and selecting an available time slot.

**Stakeholder:** Patient, Receptionist

**Acceptance Criteria:**
- Only available time slots are displayed to the patient
- A booking confirmation is shown on screen immediately after booking
- The booked slot is removed from availability in real time to prevent double bookings
- Patients receive an SMS confirmation via Twilio within 60 seconds of booking

---

### FR-04 — Appointment Rescheduling and Cancellation

**Requirement:** The system shall allow patients to reschedule or cancel an existing appointment up to 2 hours before the scheduled time.

**Stakeholder:** Patient, Receptionist, Doctor

**Acceptance Criteria:**
- Patients can reschedule to any available slot for the same doctor
- Cancelled appointments are immediately returned to the available slots pool
- The doctor's schedule is updated in real time upon cancellation or rescheduling
- Patients receive an SMS notification confirming the change within 60 seconds

---

### FR-05 — Doctor Schedule Management

**Requirement:** The system shall provide doctors with a dashboard displaying their daily and weekly appointment schedule, including patient names, appointment times, and consultation status.

**Stakeholder:** Doctor

**Acceptance Criteria:**
- Schedule loads within 2 seconds of the doctor logging in
- Appointments are displayed in chronological order
- Doctors can mark an appointment as completed, cancelled, or no-show
- Schedule updates in real time when a patient books or cancels

---

### FR-06 — Electronic Medical Records (EMR) Creation

**Requirement:** The system shall allow doctors to create an electronic medical record for a patient after each consultation, including diagnosis, consultation notes, and prescription details.

**Stakeholder:** Doctor, Patient, Medical Regulators

**Acceptance Criteria:**
- EMR form is accessible directly from the appointment view
- All fields (diagnosis, notes, prescription) must be completed before saving
- Saved records are immediately linked to the patient's profile
- Records cannot be deleted — only updated with a tracked change log

---

### FR-07 — Patient Medical Records Access

**Requirement:** The system shall allow patients to view their full medical history, including past diagnoses, consultation notes, and prescriptions issued by their doctors.

**Stakeholder:** Patient, Medical Regulators

**Acceptance Criteria:**
- Records are displayed in reverse chronological order (most recent first)
- Patients can only view their own records
- Each record displays the date, doctor name, diagnosis, and prescription
- Records page loads within 2 seconds

---

### FR-08 — SMS Appointment Reminders

**Requirement:** The system shall automatically send an SMS reminder to the patient 24 hours before their scheduled appointment via the Twilio API.

**Stakeholder:** Patient, Twilio

**Acceptance Criteria:**
- SMS is triggered automatically 24 hours before every confirmed appointment
- The message includes the doctor's name, date, and time of the appointment
- If the SMS fails to deliver, the system logs the failure and retries once after 10 minutes
- SMS delivery success rate must be 98% or higher

---

### FR-09 — Admin User Management

**Requirement:** The system shall allow administrators to create, update, deactivate, and delete doctor and patient accounts from the Admin Control Panel.

**Stakeholder:** Administrator

**Acceptance Criteria:**
- Admin can create a new doctor account in under 2 minutes
- Deactivated accounts cannot log in but their data is retained in the system
- Admin receives a confirmation prompt before any account deletion
- All account changes are logged with a timestamp and admin ID

---

### FR-10 — Admin Analytics Dashboard

**Requirement:** The system shall provide administrators with a real-time analytics dashboard displaying total appointments, appointment status breakdown, doctor performance metrics, and no-show rates.

**Stakeholder:** Administrator

**Acceptance Criteria:**
- Dashboard data refreshes automatically every 60 seconds
- Admin can filter analytics by date range, doctor, and appointment status
- A summary report can be exported as a PDF or CSV file
- All metrics are calculated and displayed within 3 seconds of page load

---

### FR-11 — Doctor Search and Profile Viewing

**Requirement:** The system shall allow patients to search for available doctors by name or specialisation and view each doctor's profile including qualifications and available time slots.

**Stakeholder:** Patient

**Acceptance Criteria:**
- Search results appear within 2 seconds of submitting the query
- Each doctor profile displays name, specialisation, and next 5 available slots
- Patients can initiate a booking directly from the doctor profile page
- Search returns relevant results even with partial name input

---

### FR-12 — Audit Trail Logging

**Requirement:** The system shall maintain a complete audit log of all record accesses and modifications, capturing the user ID, action performed, timestamp, and affected record.

**Stakeholder:** Medical Regulators, IT Support Staff

**Acceptance Criteria:**
- Every read and write operation on medical records is logged automatically
- Audit logs are accessible only to the Administrator and IT Support Staff
- Logs cannot be modified or deleted by any user
- Logs are retained for a minimum of 5 years in compliance with healthcare regulations

---

## 3. Non-Functional Requirements

Non-functional requirements define the quality attributes the system must meet. They are categorised across six key dimensions.

---

### 3.1 Usability

**NFR-U01:** The system interface shall be responsive and fully functional on desktop, tablet, and mobile browsers without requiring any plugin installation.

> **Measure:** Tested across Chrome, Firefox, and Safari on screen sizes from 375px to 1920px wide with zero layout breakage.

**NFR-U02:** The system shall comply with WCAG 2.1 Level AA accessibility standards to ensure usability for users with visual or motor impairments.

> **Measure:** Automated accessibility audit using Lighthouse returns a score of 90 or above.

---

### 3.2 Deployability

**NFR-D01:** The system shall be deployable on both Windows and Linux server environments using standard Node.js and PostgreSQL installation procedures.

> **Measure:** Full deployment completed in under 30 minutes using the provided setup documentation.

**NFR-D02:** The system shall include a Docker configuration file to support containerised deployment for consistent environment setup across development, staging, and production.

> **Measure:** System starts successfully using `docker-compose up` with no manual configuration required.

---

### 3.3 Maintainability

**NFR-M01:** All API endpoints shall be documented in a developer API guide, including request/response formats, authentication requirements, and example calls.

> **Measure:** 100% of endpoints documented before system release.

**NFR-M02:** The codebase shall follow a modular architecture with clearly separated concerns (controllers, services, models) so that individual components can be updated without affecting others.

> **Measure:** A change to any single controller requires no modification to any other controller to take effect.

---

### 3.4 Scalability

**NFR-S01:** The system shall support a minimum of 500 concurrent users during peak clinic hours without degradation in response time.

> **Measure:** Load testing with 500 simultaneous users produces average response times under 2 seconds.

**NFR-S02:** The database schema shall be designed to support future expansion, including the addition of new user roles, appointment types, and clinic branches, without requiring structural redesign.

> **Measure:** A new user role can be added by modifying only the roles table and relevant controller, with no changes to the core schema.

---

### 3.5 Security

**NFR-SEC01:** All data transmitted between the client and server shall be encrypted using HTTPS/TLS 1.2 or higher.

> **Measure:** All HTTP requests are redirected to HTTPS. SSL Labs security rating of A or above.

**NFR-SEC02:** All user passwords shall be hashed using bcrypt with a minimum salt round of 10 before being stored in the database.

> **Measure:** No plaintext passwords exist in the database under any circumstance. Verified by database audit.

**NFR-SEC03:** All patient medical records shall be accessible only to the patient themselves and their assigned doctor, enforced at the API level through role-based access control.

> **Measure:** Penetration test confirms that a patient cannot access another patient's records under any authenticated session.

---

### 3.6 Performance

**NFR-P01:** All key pages — including the appointment booking page, patient dashboard, and doctor schedule — shall load within 2 seconds under normal network conditions.

> **Measure:** Google Lighthouse performance score of 85 or above on all key pages.

**NFR-P02:** The system shall process and confirm an appointment booking within 3 seconds of the patient submitting the booking form.

> **Measure:** End-to-end booking time measured from form submission to confirmation screen display is under 3 seconds in 95% of test cases.

---

## 4. Requirements Traceability Matrix

| Requirement ID | Description | Stakeholder |
|---|---|---|
| FR-01 | User Registration | Patient, Administrator |
| FR-02 | Authentication & Role-Based Access | Patient, Doctor, Admin, IT Staff |
| FR-03 | Appointment Booking | Patient, Receptionist |
| FR-04 | Appointment Rescheduling & Cancellation | Patient, Receptionist, Doctor |
| FR-05 | Doctor Schedule Management | Doctor |
| FR-06 | EMR Creation | Doctor, Patient, Regulators |
| FR-07 | Patient Medical Records Access | Patient, Regulators |
| FR-08 | SMS Appointment Reminders | Patient, Twilio |
| FR-09 | Admin User Management | Administrator |
| FR-10 | Admin Analytics Dashboard | Administrator |
| FR-11 | Doctor Search & Profile Viewing | Patient |
| FR-12 | Audit Trail Logging | Regulators, IT Staff |
| NFR-U01 | Responsive Design | Patient, Receptionist |
| NFR-U02 | Accessibility (WCAG 2.1) | Patient, Regulators |
| NFR-D01 | Cross-Platform Deployability | IT Support Staff |
| NFR-D02 | Docker Support | IT Support Staff |
| NFR-M01 | API Documentation | IT Support Staff |
| NFR-M02 | Modular Architecture | IT Support Staff |
| NFR-S01 | 500 Concurrent Users | Administrator, IT Staff |
| NFR-S02 | Scalable Schema | IT Support Staff |
| NFR-SEC01 | HTTPS/TLS Encryption | Regulators, IT Staff |
| NFR-SEC02 | Password Hashing | Regulators, IT Staff |
| NFR-SEC03 | Role-Based Record Access | Patient, Regulators |
| NFR-P01 | Page Load Under 2 Seconds | Patient, Doctor, Receptionist |
| NFR-P02 | Booking Confirmation Under 3 Seconds | Patient |