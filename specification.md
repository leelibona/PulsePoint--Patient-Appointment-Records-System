# SPECIFICATION.md — PulsePoint Patient Appointment & Records System

---

## 1. Introduction

### 1.1 Project Title
**PulsePoint — Patient Appointment & Records System**

---

### 1.2 Domain

**Domain: Healthcare**

The healthcare domain encompasses all systems, processes, and services involved in the delivery of medical care to individuals. This includes hospitals, clinics, private practices, pharmacies, and any institution responsible for maintaining patient health and wellbeing.

Within the healthcare domain, digitisation has become a critical priority. Patients need convenient access to medical services, doctors need efficient tools to manage their workload, and administrators need real-time visibility into operations. Systems within this domain typically handle highly sensitive personal and medical data, requiring strict security, privacy compliance, and reliable availability.

PulsePoint operates within the **outpatient clinic and private practice** segment of the healthcare domain ,  focusing specifically on appointment management, electronic medical records (EMR), and the communication layer between patients and healthcare providers.

---

### 1.3 Problem Statement

Many small-to-medium healthcare facilities , including private clinics and general practitioner (GP) practices , still rely on outdated, manual, or paper-based systems to manage patient appointments and medical records. These approaches introduce a range of critical inefficiencies and risks:

- **Appointment mismanagement**: Patients book appointments via phone calls or walk-ins, leading to double bookings, long wait times, and missed appointments with no reminder system in place.
- **Fragmented medical records**: Patient histories are stored in physical files or disconnected systems, making it difficult for doctors to access accurate, up-to-date information during consultations.
- **Poor patient communication**: There is no automated mechanism to notify patients of upcoming appointments, cancellations, or changes — resulting in high no-show rates.
- **Lack of operational visibility**: Clinic administrators have no real-time dashboard to monitor appointment volumes, revenue, or doctor availability.

**PulsePoint** addresses all of these problems by providing a unified, web-based platform that digitises and automates the end-to-end patient journey , from initial registration and appointment booking through to consultation records and payment processing.

---

### 1.4 Individual Scope & Feasibility Justification

PulsePoint is scoped as an individual semester-long project and has been carefully designed to be both **ambitious enough** to demonstrate full-stack software engineering skills and **feasible enough** to be completed by a single developer within the available timeframe.

#### Feasibility Justification

| Factor | Justification |
|---|---|
| **Technology Familiarity** | The system is built using React and Node.js , widely documented technologies with large support communities, reducing development risk. |
| **Bounded Scope** | The system focuses on three clearly defined user roles (Patient, Doctor, Admin) with well-understood workflows, preventing scope creep. |
| **Third-Party APIs** | SMS (Twilio) functionality is handled via a well-documented API, avoiding the need to build complex features from scratch. |
| **No Hardware Dependencies** | PulsePoint is entirely web-based, requiring no IoT devices, sensors, or physical infrastructure. |
| **Incremental Development** | The system can be built incrementally , core appointment booking first, then EMR, then payments and notifications , allowing for manageable sprint planning. |
| **Open Source Tools** | PostgreSQL, React, Node.js, and Express are all free and open source, eliminating licensing costs and barriers. |

#### What is In Scope
- User registration and login with role-based access control (Patient, Doctor, Admin)
- Appointment booking, rescheduling, and cancellation
- Electronic Medical Records (EMR) ( creation and viewing)
- SMS appointment reminders via Twilio
- Admin analytics dashboard (appointments, revenue, doctor performance)

#### What is Out of Scope
- Video/telemedicine consultations
- Integration with national health databases or government health systems
- Mobile application (iOS/Android)
- Pharmacy or prescription dispensing management
- Insurance claims processing

---

## 2. Functional Requirements

### 2.1 Patient
- Register and create a personal profile
- Log in securely and manage account details
- Search for available doctors by name or specialisation
- Book, reschedule, or cancel an appointment
- Receive SMS reminders before appointments
- View personal medical records and consultation history

### 2.2 Doctor
- Log in to a dedicated doctor dashboard
- View daily and weekly appointment schedule
- Accept or decline appointment requests
- Write and save consultation notes per patient
- Issue digital prescriptions linked to a patient record
- View patient medical history before consultations

### 2.3 Administrator
- Log in to a system-wide admin control panel
- Manage doctor and patient accounts
- View real-time analytics: total appointments, revenue, no-show rates
- Generate reports on doctor performance and appointment trends
- Configure system settings (clinic hours, appointment durations, etc.)

---

## 3. Non-Functional Requirements

| Requirement | Description |
|---|---|
| **Security** | All user data must be encrypted in transit (HTTPS/TLS). Passwords must be hashed using bcrypt. JWT tokens must expire after a defined period. |
| **Performance** | The system must load key pages within 2 seconds under normal network conditions. |
| **Scalability** | The backend must be structured to support horizontal scaling as user numbers grow. |
| **Availability** | The system should target 99% uptime during clinic operating hours. |
| **Usability** | The UI must be intuitive and accessible, requiring no technical knowledge to navigate. |
| **Data Privacy** | Patient medical records must only be accessible to the patient themselves and their assigned doctor. |

---

## 4. System Users (Actors)

| Actor | Description |
|---|---|
| **Patient** | A registered individual seeking medical appointments and access to their records |
| **Doctor** | A healthcare provider managing their schedule and patient consultations |
| **Administrator** | A clinic staff member overseeing the entire system and its operations |
| **Twilio (External)** | Third-party SMS service that sends appointment reminders to patients |

---

## 5. Assumptions and Constraints

- The system assumes stable internet connectivity for all users
- All users are assumed to have access to a modern web browser
- The system is designed for a single clinic or practice (not a multi-branch hospital network)
- Doctors are pre-registered by the administrator and cannot self-register
- The system will be developed and tested in a local development environment before deployment