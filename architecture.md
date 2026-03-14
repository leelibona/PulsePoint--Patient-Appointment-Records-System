# ARCHITECTURE.md — PulsePoint Patient Appointment & Records System

---

## 1. Introduction

### 1.1 Project Title
**PulsePoint — Patient Appointment & Records System**

---

### 1.2 Domain

**Domain: Healthcare**

The healthcare domain encompasses all systems, services, and processes involved in delivering medical care to individuals. This includes hospitals, private clinics, GP practices, and any institution responsible for maintaining patient health and wellbeing.

PulsePoint operates within the **outpatient clinic and private practice** segment — focusing on appointment management, electronic medical records (EMR), and the communication layer between patients and healthcare providers. Systems in this domain demand high security, data privacy, and reliable availability since they handle sensitive personal and medical information.

---

### 1.3 Problem Statement

Small-to-medium healthcare facilities still rely on manual or paper-based systems to manage patient appointments and medical records. This results in double bookings, lost records, missed appointments due to no reminders, and no operational visibility for administrators. PulsePoint solves these problems by providing a single unified web platform that digitises and automates the end-to-end patient journey — from registration and appointment booking through to consultation records and SMS notifications.

---

### 1.4 Individual Scope & Feasibility Justification

PulsePoint is scoped as an individual semester-long project. It is built on React and Node.js — well-documented, widely supported technologies — and is divided into three clearly bounded user roles (Patient, Doctor, Administrator). SMS notifications are handled by the Twilio API, avoiding the need to build complex communication infrastructure from scratch. The system is entirely web-based with no hardware dependencies and can be developed incrementally, making it fully feasible for a single developer within the semester timeframe.

---

## 2. C4 Architectural Diagrams

The C4 model describes software architecture at four levels of abstraction:
- **Level 1 — System Context**: The system and its relationship to users and external services
- **Level 2 — Container**: The high-level technology building blocks inside the system
- **Level 3 — Component**: The internal components within each container
- **Level 4 — Code**: The key classes and data structures within a component

---

## 2.1 Level 1 — System Context Diagram

> Shows PulsePoint as a whole and how it interacts with users and external systems.

```mermaid
C4Context
    title System Context Diagram — PulsePoint

    Person(patient, "Patient", "Books appointments and views medical records")
    Person(doctor, "Doctor", "Manages schedule and consultations")
    Person(admin, "Administrator", "Oversees system operations")

    System(pulsepoint, "PulsePoint", "Web-based patient appointment and records platform")

    System_Ext(twilio, "Twilio SMS API", "Sends SMS reminders to patients")

    Rel(patient, pulsepoint, "Uses", "HTTPS")
    Rel(doctor, pulsepoint, "Uses", "HTTPS")
    Rel(admin, pulsepoint, "Manages", "HTTPS")
    Rel(pulsepoint, twilio, "Sends reminders", "REST")

    UpdateLayoutConfig($c4ShapeInRow="3", $c4BoundaryInRow="1")
```

---

## 2.2 Level 2 — Container Diagram

> Shows the high-level technology building blocks (containers) that make up PulsePoint.

```mermaid
C4Container
    title Container Diagram — PulsePoint

    Person(users, "Users", "Patient, Doctor, Administrator")
    System_Ext(twilio, "Twilio SMS API", "Sends SMS reminders")

    System_Boundary(pulsepoint, "PulsePoint") {
        Container(web, "Web Application", "React + Tailwind CSS", "User interface for all roles")
        Container(api, "REST API Server", "Node.js + Express", "Business logic and authentication")
        Container(db, "Database", "PostgreSQL", "Stores all system data")
        Container(notify, "Notification Service", "Node.js Module", "Schedules SMS reminders")
    }

    Rel(users, web, "Uses", "HTTPS")
    Rel(web, api, "Calls", "REST/JSON")
    Rel(api, db, "Reads/Writes", "SQL")
    Rel(api, notify, "Triggers", "Internal")
    Rel(notify, twilio, "Sends SMS", "REST")

    UpdateLayoutConfig($c4ShapeInRow="2", $c4BoundaryInRow="1")
```

---

## 2.3 Level 3 — Component Diagram

> Shows the internal components within the REST API Server container.

```mermaid
C4Component
    title Component Diagram — REST API Server

    Container_Ext(web, "Web Application", "React + Tailwind CSS", "Frontend for all users")
    Container_Ext(db, "Database", "PostgreSQL", "Stores all system data")
    Container_Ext(notify, "Notification Service", "Node.js", "Sends SMS reminders")

    Container_Boundary(api, "REST API Server") {
        Component(auth, "Auth Controller", "Express + JWT", "Handles login, registration and token validation")
        Component(patient, "Patient Controller", "Express Router", "Manages patient profiles and data")
        Component(appt, "Appointment Controller", "Express Router", "Handles booking and scheduling")
        Component(emr, "EMR Controller", "Express Router", "Manages medical records and notes")
        Component(doctor, "Doctor Controller", "Express Router", "Manages doctor profiles and schedules")
        Component(admin, "Admin Controller", "Express Router", "Handles analytics and user management")
    }

    Rel(web, auth, "POST /auth/login", "REST")
    Rel(web, patient, "GET/PUT /patients", "REST")
    Rel(web, appt, "GET/POST /appointments", "REST")
    Rel(web, emr, "GET/POST /records", "REST")
    Rel(web, doctor, "GET /doctors", "REST")
    Rel(web, admin, "GET /admin/analytics", "REST")

    Rel(auth, db, "Reads/Writes", "SQL")
    Rel(patient, db, "Reads/Writes", "SQL")
    Rel(appt, db, "Reads/Writes", "SQL")
    Rel(emr, db, "Reads/Writes", "SQL")
    Rel(doctor, db, "Reads/Writes", "SQL")
    Rel(admin, db, "Reads", "SQL")
    Rel(appt, notify, "Triggers SMS", "Internal")

    UpdateLayoutConfig($c4ShapeInRow="3", $c4BoundaryInRow="1")
```

---

## 2.4 Level 4 — Code Diagram

> Shows the key classes and relationships within the Appointment Controller component.

```mermaid
classDiagram
    class Appointment {
        +int id
        +int patientId
        +int doctorId
        +Date appointmentDate
        +String timeSlot
        +String status
        +String notes
        +Date createdAt
        +Date updatedAt
        +book()
        +reschedule(newDate, newTimeSlot)
        +cancel()
        +getStatus()
    }

    class Patient {
        +int id
        +String firstName
        +String lastName
        +String email
        +String phone
        +Date dateOfBirth
        +String gender
        +Date createdAt
        +getAppointments()
        +getMedicalRecords()
        +updateProfile()
    }

    class Doctor {
        +int id
        +String firstName
        +String lastName
        +String email
        +String specialisation
        +String phone
        +getSchedule()
        +getAppointments()
        +updateAvailability()
    }

    class MedicalRecord {
        +int id
        +int patientId
        +int doctorId
        +int appointmentId
        +String diagnosis
        +String prescription
        +String consultationNotes
        +Date recordDate
        +create()
        +update()
        +getByPatient(patientId)
    }

    class User {
        +int id
        +String email
        +String passwordHash
        +String role
        +Date createdAt
        +login()
        +logout()
        +resetPassword()
    }

    class NotificationService {
        +sendSMSReminder(phone, appointmentDetails)
        +scheduleReminder(appointment)
        +cancelReminder(appointmentId)
    }

    User <|-- Patient : extends
    User <|-- Doctor : extends
    Patient "1" --> "many" Appointment : books
    Doctor "1" --> "many" Appointment : manages
    Appointment "1" --> "1" MedicalRecord : generates
    Appointment "1" --> "1" NotificationService : triggers
```

---

## 3. Summary of Architecture Decisions

| Decision | Choice | Reason |
|---|---|---|
| **Frontend Framework** | React + Tailwind CSS | Component-based architecture makes role-specific portals easy to manage |
| **Backend Framework** | Node.js + Express | Lightweight, fast, and well-suited for REST API development |
| **Database** | PostgreSQL | Relational data model suits structured healthcare records with clear relationships |
| **Authentication** | JWT + RBAC | Stateless authentication scales well; role-based control protects sensitive data |
| **SMS Notifications** | Twilio API | Reliable, well-documented third-party service — no need to build SMS infrastructure |
| **Architecture Style** | Monolithic (modular) | Appropriate for a single-developer semester project; can be split into microservices later |