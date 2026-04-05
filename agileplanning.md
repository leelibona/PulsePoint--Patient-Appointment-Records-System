# AGILE-PLANNING.md — PulsePoint Patient Appointment & Records System

---

## 1. Introduction

This document captures the Agile planning artifacts for the PulsePoint system. It translates the functional requirements from Assignment 4 and the use cases from Assignment 5 into actionable user stories, organises them into a prioritised product backlog using MoSCoW prioritisation, and defines the first sprint plan towards delivering a Minimum Viable Product (MVP).

---

## 2. User Stories

User stories are written in the format: *"As a [role], I want [action] so that [benefit]."*
All stories follow the INVEST criteria — Independent, Negotiable, Valuable, Estimable, Small, and Testable.

### 2.1 User Stories Table

| Story ID | User Story | Acceptance Criteria | Priority |
|---|---|---|---|
| US001 | As a **patient**, I want to register an account so that I can access the PulsePoint platform and book appointments online. | Account is created successfully. Duplicate emails are rejected with a clear error message. Patient is redirected to login page on success. | High |
| US002 | As a **patient**, I want to log in to the system so that I can securely access my personal dashboard and appointments. | Patient is authenticated using email and password. JWT token is issued. Patient is redirected to the Patient Portal. Invalid credentials display an error message. | High |
| US003 | As a **patient**, I want to search for a doctor by name or specialisation so that I can find the right healthcare provider for my needs. | Search results appear within 2 seconds. Results display doctor name, specialisation, and next available slots. Partial name searches return relevant results. | High |
| US004 | As a **patient**, I want to book an appointment with a doctor so that I can schedule a consultation at a convenient time. | Only available time slots are shown. Booking confirmation is displayed immediately. Booked slot is removed from availability in real time. SMS confirmation is sent within 60 seconds. | High |
| US005 | As a **patient**, I want to reschedule an existing appointment so that I can change my consultation time when my availability changes. | Patient can select a new available slot for the same doctor. Old slot is returned to the available pool. SMS notification confirming the reschedule is sent within 60 seconds. Rescheduling is blocked within 2 hours of the appointment. | Medium |
| US006 | As a **patient**, I want to cancel an appointment so that I can free up the slot when I am unable to attend. | Appointment status is updated to Cancelled. Slot is returned to the available pool. SMS cancellation notification is sent. Cancellation is blocked within 2 hours of the appointment. | Medium |
| US007 | As a **patient**, I want to receive an SMS reminder 24 hours before my appointment so that I do not miss my scheduled consultation. | SMS is automatically triggered 24 hours before every confirmed appointment. Message includes doctor name, date, and time. Delivery success rate is 98% or higher. Failed deliveries are retried once after 10 minutes. | High |
| US008 | As a **patient**, I want to view my medical records so that I can access my consultation history and prescriptions at any time. | Records are displayed in reverse chronological order. Each record shows date, doctor name, diagnosis, and prescription. Page loads within 2 seconds. Only the patient can view their own records. | High |
| US009 | As a **doctor**, I want to view my daily appointment schedule so that I can prepare for upcoming consultations efficiently. | Schedule loads within 2 seconds of login. Appointments are displayed in chronological order. Doctor can mark appointments as completed, cancelled, or no-show. Schedule updates in real time when patients book or cancel. | High |
| US010 | As a **doctor**, I want to create an electronic medical record after a consultation so that I can document the patient's diagnosis, notes, and prescription digitally. | Medical record form is accessible from the appointment view. All required fields must be completed before saving. Record is linked to the patient and appointment. Appointment status is updated to Completed on save. | High |
| US011 | As an **administrator**, I want to manage doctor and patient accounts so that I can onboard new doctors and maintain accurate user data. | Admin can create a new doctor account in under 2 minutes. Deactivated accounts cannot log in but data is retained. All account changes are logged with a timestamp and admin ID. | High |
| US012 | As an **administrator**, I want to view a real-time analytics dashboard so that I can monitor clinic performance and make informed operational decisions. | Dashboard displays total appointments, completion rate, and no-show rate. Data refreshes every 60 seconds. Admin can filter by date range, doctor, and status. Reports can be exported as PDF or CSV within 3 seconds. | Medium |
| US013 | As an **administrator**, I want all user passwords to be hashed using bcrypt so that sensitive credentials are never stored in plain text. | No plaintext passwords exist in the database. Passwords are hashed with bcrypt at a minimum salt round of 10. Verified by database audit. | High |
| US014 | As a **medical regulator**, I want to access a complete audit log of all record accesses and modifications so that I can verify compliance with healthcare data regulations. | Every read and write on medical records is logged automatically. Logs include user ID, action, timestamp, and affected record. Logs cannot be modified or deleted. Logs are retained for a minimum of 5 years. | Medium |

---

## 3. Product Backlog

The product backlog is organised using MoSCoW prioritisation and includes effort estimates using Fibonacci story points (1 = trivial, 2 = small, 3 = medium, 5 = large, 8 = very large).

### 3.1 Backlog Table

| Story ID | User Story Summary | MoSCoW Priority | Effort Estimate (Points) | Dependencies |
|---|---|---|---|---|
| US001 | Patient registers an account | Must-have | 2 | None |
| US002 | Patient logs in to the system | Must-have | 2 | US001 |
| US003 | Patient searches for a doctor | Must-have | 3 | US002 |
| US004 | Patient books an appointment | Must-have | 5 | US002, US003 |
| US007 | Patient receives SMS reminder | Must-have | 3 | US004 |
| US008 | Patient views medical records | Must-have | 3 | US002 |
| US009 | Doctor views appointment schedule | Must-have | 3 | US002 |
| US010 | Doctor creates medical record | Must-have | 5 | US009 |
| US011 | Admin manages user accounts | Must-have | 3 | US002 |
| US013 | Passwords hashed with bcrypt | Must-have | 2 | US001 |
| US005 | Patient reschedules appointment | Should-have | 3 | US004 |
| US006 | Patient cancels appointment | Should-have | 2 | US004 |
| US012 | Admin views analytics dashboard | Should-have | 5 | US011 |
| US014 | Regulator views audit logs | Could-have | 3 | US010, US008 |

### 3.2 Prioritisation Justification

- **Must-have stories** (US001–US004, US007–US011, US013) form the core of the MVP. They directly address the highest-priority stakeholder concerns identified in Assignment 4 — patient self-service booking, doctor schedule management, electronic medical records, and basic security. Without these, the system cannot function as intended.

- **Should-have stories** (US005, US006, US012) add significant value but the system remains functional without them in the first release. Rescheduling and cancellation can initially be handled by the receptionist, and analytics can be deferred to a second sprint.

- **Could-have stories** (US014) are important for regulatory compliance but are not required for the initial operational launch of the system. Audit logging can be introduced in a later sprint once the core features are stable.

---

## 4. Sprint 1 Plan

### 4.1 Sprint Goal

> **"Implement the core authentication and appointment booking functionality so that patients can register, log in, search for doctors, and book appointments — delivering the foundation of the PulsePoint MVP."**

This sprint focuses on delivering the most critical end-to-end user journey: a patient creating an account, logging in, finding a doctor, and booking an appointment. This represents the minimum set of features needed to demonstrate the system's core value to stakeholders.

### 4.2 Selected User Stories for Sprint 1

| Story ID | User Story Summary | Effort (Points) |
|---|---|---|
| US001 | Patient registers an account | 2 |
| US002 | Patient logs in to the system | 2 |
| US003 | Patient searches for a doctor | 3 |
| US004 | Patient books an appointment | 5 |
| US013 | Passwords hashed with bcrypt | 2 |
| US007 | Patient receives SMS reminder | 3 |

**Total Sprint Points: 17**

### 4.3 Sprint Backlog — Task Breakdown

| Task ID | Task Description | Assigned To | Estimated Hours | Status |
|---|---|---|---|---|
| T001 | Design and implement the PostgreSQL database schema for Users and Patients tables | Developer | 4 | To Do |
| T002 | Develop the POST /auth/register API endpoint with input validation | Developer | 4 | To Do |
| T003 | Build the patient registration form UI in React with field validation | Developer | 4 | To Do |
| T004 | Develop the POST /auth/login API endpoint with JWT token generation | Developer | 4 | To Do |
| T005 | Implement bcrypt password hashing in the authentication service | Developer | 2 | To Do |
| T006 | Build the login page UI in React with error handling | Developer | 3 | To Do |
| T007 | Design and implement the Doctors table and availability slots schema | Developer | 3 | To Do |
| T008 | Develop the GET /doctors search API endpoint with name and specialisation filters | Developer | 5 | To Do |
| T009 | Build the doctor search UI in React with results display | Developer | 4 | To Do |
| T010 | Design and implement the Appointments table in PostgreSQL | Developer | 3 | To Do |
| T011 | Develop the POST /appointments booking API endpoint with slot availability check | Developer | 6 | To Do |
| T012 | Build the appointment booking UI in React with slot selection and confirmation screen | Developer | 5 | To Do |
| T013 | Integrate Twilio API into the Notification Service for SMS confirmation messages | Developer | 4 | To Do |
| T014 | Implement SMS trigger on successful appointment booking | Developer | 2 | To Do |
| T015 | Write unit tests for authentication and booking API endpoints | Developer | 4 | To Do |

**Total Estimated Hours: 57 hours**

### 4.4 Sprint Summary

| Attribute | Detail |
|---|---|
| **Sprint Duration** | 2 weeks |
| **Sprint Goal** | Deliver core authentication and appointment booking for MVP |
| **Total Story Points** | 17 |
| **Total Estimated Hours** | 57 hours |
| **Definition of Done** | All selected user stories pass their acceptance criteria. All API endpoints are tested. UI is functional and responsive. Code is committed and pushed to the GitHub repository. |

---

## 5. Traceability Matrix

| Story ID | User Story | Assignment 4 Requirement | Assignment 5 Use Case |
|---|---|---|---|
| US001 | Patient registers an account | Functional Requirement 01 — User Registration | Use Case 1 — Register Account |
| US002 | Patient logs in to the system | Functional Requirement 02 — User Authentication | Use Case 2 — Login to System |
| US003 | Patient searches for a doctor | Functional Requirement 11 — Doctor Search | Use Case 3 — Book Appointment |
| US004 | Patient books an appointment | Functional Requirement 03 — Appointment Booking | Use Case 3 — Book Appointment |
| US005 | Patient reschedules appointment | Functional Requirement 04 — Rescheduling | Use Case 4 — Reschedule Appointment |
| US006 | Patient cancels appointment | Functional Requirement 04 — Cancellation | Use Case 5 — Cancel Appointment |
| US007 | Patient receives SMS reminder | Functional Requirement 08 — SMS Reminders | Use Case 3 — Book Appointment |
| US008 | Patient views medical records | Functional Requirement 07 — Records Access | Use Case 7 — View Medical Records |
| US009 | Doctor views appointment schedule | Functional Requirement 05 — Schedule Management | Use Case 2 — Login to System |
| US010 | Doctor creates medical record | Functional Requirement 06 — EMR Creation | Use Case 6 — Create Medical Record |
| US011 | Admin manages user accounts | Functional Requirement 09 — User Management | Use Case 8 — View Analytics Dashboard |
| US012 | Admin views analytics dashboard | Functional Requirement 10 — Analytics Dashboard | Use Case 8 — View Analytics Dashboard |
| US013 | Passwords hashed with bcrypt | Non-Functional Requirement — Security 02 | Use Case 2 — Login to System |
| US014 | Regulator views audit logs | Functional Requirement 12 — Audit Trail | Use Case 7 — View Medical Records |