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

Small-to-medium healthcare facilities still rely on manual or paper-based systems to manage patient appointments and medical records. This results in double bookings, lost records, missed appointments due to no reminders, and no operational visibility for administrators. PulsePoint solves these problems by providing a single unified web platform that digitises and automates the end-to-end patient journey ,  from registration and appointment booking through to consultation records and SMS notifications.

---

### 1.4 Individual Scope & Feasibility Justification

PulsePoint is scoped as an individual semester-long project. It is built on React and Node.js ,  well-documented, widely supported technologies and is divided into three clearly bounded user roles (Patient, Doctor, Administrator). SMS notifications are handled by the Twilio API, avoiding the need to build complex communication infrastructure from scratch. The system is entirely web-based with no hardware dependencies and can be developed incrementally, making it fully feasible for a single developer within the semester timeframe.

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

    Person(patient, "Patient", "A registered individual who books appointments and views their medical records")
    Person(doctor, "Doctor", "A healthcare provider who manages schedules and patient consultations")
    Person(admin, "Administrator", "A clinic staff member who oversees the system and monitors operations")

    System(pulsepoint, "PulsePoint", "Web-based platform for managing patient appointments, medical records, and clinic operations")

    System_Ext(twilio, "Twilio SMS API", "External service that sends SMS appointment reminders to patients")

    Rel(patient, pulsepoint, "Registers, books appointments, views EMR", "HTTPS")
    Rel(doctor, pulsepoint, "Manages schedule, writes consultation notes", "HTTPS")
    Rel(admin, pulsepoint, "Manages users, views analytics and reports", "HTTPS")
    Rel(pulsepoint, twilio, "Sends appointment reminder SMS notifications", "HTTPS/REST")
```

---

## 2.2 Level 2 — Container Diagram

> Shows the high-level technology building blocks (containers) that make up PulsePoint.

```mermaid
C4Container
    title Container Diagram — PulsePoint

    Person(patient, "Patient", "Books appointments and views records")
    Person(doctor, "Doctor", "Manages schedule and patient notes")
    Person(admin, "Administrator", "Oversees system operations")

    System_Ext(twilio, "Twilio SMS API", "Sends SMS reminders to patients")

    System_Boundary(pulsepoint, "PulsePoint System") {

        Container(web_app, "React Web Application", "React + Tailwind CSS", "Provides the user interface for all three roles — Patient, Doctor, and Admin portals")

        Container(api_server, "REST API Server", "Node.js + Express", "Handles all business logic, authentication, role-based access control, and data operations")

        Container(database, "Relational Database", "PostgreSQL", "Persists all user accounts, appointments, medical records, and system data")

        Container(notification_service, "Notification Service", "Node.js Module", "Schedules and triggers SMS reminders via the Twilio API")
    }

    Rel(patient, web_app, "Uses via browser", "HTTPS")
    Rel(doctor, web_app, "Uses via browser", "HTTPS")
    Rel(admin, web_app, "Uses via browser", "HTTPS")

    Rel(web_app, api_server, "Makes API calls", "HTTPS/REST JSON")
    Rel(api_server, database, "Reads and writes data", "SQL/TCP")
    Rel(api_server, notification_service, "Triggers reminders", "Internal function call")
    Rel(notification_service, twilio, "Sends SMS via API", "HTTPS/REST")
```

---

## 2.3 Level 3 — Component Diagram

> Shows the internal components within the REST API Server container.

```mermaid
C4Component
    title Component Diagram — REST API Server (Node.js + Express)

    Container_Ext(web_app, "React Web Application", "React + Tailwind CSS", "Frontend used by all users")
    Container_Ext(database, "PostgreSQL Database", "Relational Database", "Stores all system data")
    Container_Ext(notification_service, "Notification Service", "Node.js Module", "Sends SMS reminders")

    Container_Boundary(api_server, "REST API Server") {

        Component(auth_controller, "Auth Controller", "Express Router + JWT", "Handles user registration, login, logout, and JWT token generation and validation")

        Component(patient_controller, "Patient Controller", "Express Router", "Manages patient profile creation, updates, and retrieval of patient-specific data")

        Component(appointment_controller, "Appointment Controller", "Express Router", "Handles booking, rescheduling, cancellation, and retrieval of appointments")

        Component(emr_controller, "EMR Controller", "Express Router", "Manages creation and retrieval of electronic medical records and consultation notes")

        Component(doctor_controller, "Doctor Controller", "Express Router", "Manages doctor profiles, specialisations, and availability schedules")

        Component(admin_controller, "Admin Controller", "Express Router", "Handles analytics queries, user management, and system configuration")

        Component(middleware, "Auth Middleware", "JWT Middleware", "Validates JWT tokens and enforces role-based access control on all protected routes")
    }

    Rel(web_app, auth_controller, "POST /auth/login, /auth/register", "HTTPS/REST")
    Rel(web_app, patient_controller, "GET/PUT /patients/:id", "HTTPS/REST")
    Rel(web_app, appointment_controller, "GET/POST/PUT/DELETE /appointments", "HTTPS/REST")
    Rel(web_app, emr_controller, "GET/POST /records/:patientId", "HTTPS/REST")
    Rel(web_app, doctor_controller, "GET /doctors, /doctors/:id/schedule", "HTTPS/REST")
    Rel(web_app, admin_controller, "GET /admin/analytics, /admin/users", "HTTPS/REST")

    Rel(auth_controller, middleware, "Issues token validated by", "Internal")
    Rel(patient_controller, middleware, "Protected by", "Internal")
    Rel(appointment_controller, middleware, "Protected by", "Internal")
    Rel(emr_controller, middleware, "Protected by", "Internal")
    Rel(doctor_controller, middleware, "Protected by", "Internal")
    Rel(admin_controller, middleware, "Protected by", "Internal")

    Rel(auth_controller, database, "Reads/writes user credentials", "SQL")
    Rel(patient_controller, database, "Reads/writes patient data", "SQL")
    Rel(appointment_controller, database, "Reads/writes appointment data", "SQL")
    Rel(emr_controller, database, "Reads/writes medical records", "SQL")
    Rel(doctor_controller, database, "Reads/writes doctor data", "SQL")
    Rel(admin_controller, database, "Reads analytics data", "SQL")
    Rel(appointment_controller, notification_service, "Triggers SMS on booking/update", "Internal")
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