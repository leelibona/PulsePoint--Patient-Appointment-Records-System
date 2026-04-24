# DOMAIN-MODEL.md — PulsePoint Patient Appointment & Records System

**Assignment:** 9 — Domain Modeling and Class Diagram Development
**Task:** 1 — Domain Model

---

## 1. Introduction

This document presents the domain model for the PulsePoint system. It identifies the core entities, their attributes, responsibilities, and relationships, and documents the business rules that govern system behaviour. All entities and rules are traceable to the functional requirements defined in Assignment 4 and the state diagrams defined in Assignment 8.

---

## 2. Domain Entity Table

| Entity | Attributes | Methods | Relationships |
|---|---|---|---|
| **User** | `userId`, `fullName`, `email`, `passwordHash`, `phone`, `role` (Patient / Doctor / Admin / Receptionist), `status` (Registered / Active / Inactive / Deactivated / Deleted) | `register()`, `login()`, `logout()`, `deactivate()`, `reactivate()` | Generalised into `Patient`, `Doctor`, `Administrator`. Associated with `AuditLog` for all record actions. |
| **Patient** | `dateOfBirth`, `gender`, `profileStatus` (Incomplete / Complete / Updated / Deactivated / Archived) | `updateProfile()`, `viewMedicalRecords()`, `searchDoctors()` | Extends `User`. Has many `Appointments`. Owns many `MedicalRecords`. |
| **Doctor** | `specialisation`, `qualifications` | `viewSchedule()`, `markAppointmentStatus()`, `createMedicalRecord()` | Extends `User`. Has many `Appointments`. Owns many `TimeSlots`. Creates many `MedicalRecords`. |
| **Appointment** | `appointmentId`, `patientId`, `doctorId`, `slotId`, `status` (Pending / Confirmed / Rescheduled / InProgress / Completed / Cancelled / NoShow / Failed), `createdAt` | `book()`, `confirm()`, `reschedule()`, `cancel()`, `markNoShow()`, `startConsultation()`, `complete()` | Belongs to one `Patient`. Belongs to one `Doctor`. Occupies one `TimeSlot`. Produces zero or one `MedicalRecord`. Triggers one or more `SMSNotifications`. |
| **TimeSlot** | `slotId`, `doctorId`, `date`, `startTime`, `endTime`, `status` (Available / Reserved / Booked / Completed / NoShow) | `reserve()`, `book()`, `release()`, `markCompleted()` | Belongs to one `Doctor`. Associated with zero or one `Appointment`. |
| **MedicalRecord** | `recordId`, `appointmentId`, `patientId`, `doctorId`, `diagnosis`, `notes`, `prescription`, `status` (Draft / Saved / Updated / UnderReview / Reviewed / Flagged / Discarded), `createdAt`, `updatedAt` | `save()`, `update()`, `flagForReview()`, `resolveFlag()` | Belongs to one `Appointment`. Belongs to one `Patient`. Created by one `Doctor`. Generates many `AuditLog` entries on every access or modification. |
| **SMSNotification** | `notificationId`, `appointmentId`, `patientPhone`, `message`, `status` (Queued / Sending / Delivered / Failed / Retrying / PermanentlyFailed), `scheduledAt`, `sentAt`, `retryCount` | `send()`, `retry()`, `logFailure()` | Belongs to one `Appointment`. Sent to one `Patient`. |
| **AuditLog** | `logId`, `userId`, `action`, `affectedRecordId`, `timestamp`, `status` (Created / Stored / UnderReview / Cleared / Flagged / Escalated / Retained / Archived) | `createEntry()`, `archive()`, `escalate()` | Created by one `User`. References one `MedicalRecord`. |

---

## 3. Business Rules

| Rule | Entity | Source |
|---|---|---|
| Email addresses must be unique across the system | `User` | FR01 |
| Passwords are hashed using bcrypt with a minimum salt round of 10 before storage | `User` | NFR-Security02 |
| JWT tokens expire after 24 hours, requiring re-authentication | `User` | FR02 |
| Deactivated accounts retain their data but cannot log in | `User` | FR09 |
| Doctor accounts can only be created by the Administrator | `Doctor` | FR01, FR09 |
| A patient can only view their own medical records | `Patient`, `MedicalRecord` | FR07, NFR-Security03 |
| A patient profile is archived after 5 years of inactivity | `Patient` | FR12 |
| Cancellation and rescheduling are only permitted more than 2 hours before the appointment time | `Appointment` | FR04 |
| A slot cannot be double-booked — it is removed from availability in real time upon confirmation | `Appointment`, `TimeSlot` | FR03 |
| A slot transitions to Reserved while a patient is mid-booking to prevent race conditions | `TimeSlot` | FR03 |
| Cancelled or rescheduled appointments immediately release their slot back to Available | `TimeSlot` | FR04 |
| All fields (diagnosis, notes, prescription) must be completed before a medical record can be saved | `MedicalRecord` | FR06 |
| Medical records cannot be deleted — only updated, with every change tracked in the audit log | `MedicalRecord` | FR06 |
| An SMS reminder is automatically sent 24 hours before every confirmed appointment | `SMSNotification` | FR08 |
| If SMS delivery fails, the system retries exactly once after 10 minutes | `SMSNotification` | FR08 |
| Audit logs cannot be modified or deleted by any user | `AuditLog` | FR12 |
| Audit logs are retained for a minimum of 5 years in compliance with POPIA | `AuditLog` | FR12 |
| Only the Administrator and IT Support Staff may access audit logs | `AuditLog` | FR12 |

---

## 4. Relationships Summary

| Relationship | Multiplicity | Description |
|---|---|---|
| `Patient` → `Appointment` | 1 to 0..* | A patient can have many appointments over time |
| `Doctor` → `Appointment` | 1 to 0..* | A doctor has many appointments in their schedule |
| `Doctor` → `TimeSlot` | 1 to 1..* | A doctor owns and manages their availability slots |
| `Appointment` → `TimeSlot` | 1 to 1 | Each appointment occupies exactly one time slot |
| `Appointment` → `MedicalRecord` | 1 to 0..1 | A completed appointment may produce one medical record |
| `Appointment` → `SMSNotification` | 1 to 1..* | An appointment triggers one or more SMS notifications |
| `MedicalRecord` → `AuditLog` | 1 to 1..* | Every record access or modification generates an audit entry |
| `User` → `AuditLog` | 1 to 0..* | Every user's actions on records are logged |

---

## 5. Requirements Traceability

| Entity | Functional Requirement | State Diagram |
|---|---|---|
| `User` | FR01 (Registration), FR02 (Authentication), FR09 (User Management) | State Diagram 1 — User Account |
| `Patient` | FR01, FR07 (Records Access), FR11 (Doctor Search) | State Diagram 6 — Patient Profile |
| `Doctor` | FR05 (Schedule Management), FR06 (EMR Creation), FR11 | State Diagram 1 — User Account |
| `Appointment` | FR03 (Booking), FR04 (Reschedule / Cancel), FR05 | State Diagram 2 — Appointment |
| `TimeSlot` | FR03, FR05 | State Diagram 5 — Doctor Availability Slot |
| `MedicalRecord` | FR06 (EMR Creation), FR07 (Records Access), FR12 (Audit Logging) | State Diagram 3 — Medical Record |
| `SMSNotification` | FR08 (SMS Reminders) | State Diagram 4 — SMS Notification |
| `AuditLog` | FR12 (Audit Trail Logging) | State Diagram 8 — Audit Log Entry |