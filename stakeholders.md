# STAKEHOLDERS.md — PulsePoint Patient Appointment & Records System

---

## 1. Overview

This document identifies and analyses all key stakeholders for the PulsePoint system. Each stakeholder is documented with their role, key concerns, pain points, and measurable success metrics to ensure the system is built around real needs.

---

## 2. Stakeholder Analysis Table

### Stakeholder 1 — Patient

| Attribute | Detail |
|---|---|
| **Role** | End user of the system. Registers on the platform, books appointments with doctors, receives SMS reminders, and views their personal medical records and consultation history. |
| **Key Concerns** | Easy and convenient appointment booking without needing to call the clinic. Access to personal medical records at any time. Timely reminders to avoid missing appointments. |
| **Pain Points** | Currently must phone the clinic to book, which is time-consuming and often results in long hold times. No access to personal medical history outside of clinic visits. Frequently misses appointments due to no reminder system. |
| **Success Metrics** | Appointment booking time reduced from 10+ minutes (phone call) to under 2 minutes online. 30% reduction in missed appointments due to SMS reminders. Patient satisfaction score of 80% or higher on post-visit surveys. |

---

### Stakeholder 2 — Doctor

| Attribute | Detail |
|---|---|
| **Role** | Healthcare provider on the platform. Manages their appointment schedule, views patient medical histories before consultations, writes consultation notes, and issues digital prescriptions. |
| **Key Concerns** | Clear and organised daily schedule to avoid double bookings. Quick access to a patient's full medical history before and during consultations. Efficient tools for writing and saving consultation notes. |
| **Pain Points** | Currently relies on paper-based schedules that are prone to errors and double bookings. Must search through physical files to retrieve patient history, wasting consultation time. Consultation notes are handwritten and easily lost. |
| **Success Metrics** | Zero double bookings after system adoption. Doctor spends less than 1 minute retrieving patient history before a consultation. 90% of consultation notes saved digitally within 5 minutes of appointment end. |

---

### Stakeholder 3 — Hospital/Clinic Administrator

| Attribute | Detail |
|---|---|
| **Role** | Oversees the day-to-day operations of the clinic. Manages doctor and patient accounts, monitors appointment volumes, views revenue reports, and configures system settings such as clinic hours and appointment durations. |
| **Key Concerns** | Real-time visibility into clinic operations and appointment load. Accurate reporting on revenue and doctor performance. Ability to manage user accounts efficiently. |
| **Pain Points** | No central dashboard to monitor operations — currently relies on manual spreadsheets and verbal updates from staff. Generating reports takes hours of manual data collection. No easy way to onboard new doctors or deactivate old accounts. |
| **Success Metrics** | Admin can generate a full operational report in under 5 minutes. Doctor onboarding time reduced from 1 day to under 30 minutes. Dashboard reflects real-time appointment data with less than 1-minute delay. |

---

### Stakeholder 4 — IT Support Staff

| Attribute | Detail |
|---|---|
| **Role** | Responsible for deploying, maintaining, and monitoring the PulsePoint system. Handles server uptime, security patches, database backups, and troubleshooting technical issues. |
| **Key Concerns** | System stability and high availability during clinic operating hours. Clear technical documentation for maintenance and future development. Secure handling of sensitive patient data. Easy deployment and update process. |
| **Pain Points** | Legacy systems are difficult to maintain due to poor documentation. Security vulnerabilities in existing tools are hard to identify and patch. No automated monitoring or alerting in place for system downtime. |
| **Success Metrics** | System achieves 99% uptime during clinic hours. All API endpoints documented in a developer guide. Security patches deployable within 24 hours of release. Automated alerts triggered within 2 minutes of any system failure. |

---

### Stakeholder 5 — Receptionist

| Attribute | Detail |
|---|---|
| **Role** | Front-desk staff member who assists patients with booking appointments, answers queries about scheduling, and coordinates between patients and doctors on a daily basis. |
| **Key Concerns** | A simple and fast interface to book or reschedule appointments on behalf of patients who are not tech-savvy. Clear visibility of doctor availability in real time. Reduced phone call volume as patients self-serve online. |
| **Pain Points** | Spends the majority of the working day on the phone booking appointments manually. Frequently has to check multiple places (paper diary, spreadsheet, verbal confirmation) to confirm doctor availability. High chance of human error when managing bookings manually. |
| **Success Metrics** | 50% reduction in appointment-related phone calls within 3 months of system launch. Receptionist can book or reschedule an appointment in under 1 minute. Zero scheduling conflicts caused by manual booking errors. |

---

### Stakeholder 6 — Medical Regulators / Compliance Officers

| Attribute | Detail |
|---|---|
| **Role** | External stakeholders responsible for ensuring that the clinic's digital systems comply with healthcare data privacy laws and medical record-keeping regulations (e.g. POPIA in South Africa, HIPAA internationally). |
| **Key Concerns** | Patient data must be stored securely and accessed only by authorised personnel. Medical records must be retained for the legally required period. The system must produce an audit trail of who accessed what data and when. |
| **Pain Points** | Existing paper-based systems make compliance audits slow and difficult. No automated audit trail exists for who has accessed or modified patient records. Risk of data breaches due to unsecured physical files. |
| **Success Metrics** | 100% of patient data encrypted in transit and at rest. Full audit log available for every record access and modification. System passes external compliance audit with zero critical findings. Data retention policies enforced automatically by the system. |

---

### Stakeholder 7 — Twilio (External Service Provider)

| Attribute | Detail |
|---|---|
| **Role** | Third-party SMS service provider integrated into PulsePoint to send automated appointment reminder messages to patients. |
| **Key Concerns** | Correct and well-formatted API requests from PulsePoint. Compliance with SMS regulations and anti-spam policies. Reliable and timely delivery of messages. |
| **Pain Points** | Poorly structured API calls from integrated systems can cause message delivery failures. Systems that send too many messages in a short time may be flagged or throttled. |
| **Success Metrics** | 98% SMS delivery success rate. No API errors caused by incorrect request formatting from PulsePoint. All messages delivered within 60 seconds of being triggered. |

---

## 3. Stakeholder Summary Table

| # | Stakeholder | Role Type | Priority |
|---|---|---|---|
| 1 | Patient | Primary User | High |
| 2 | Doctor | Primary User | High |
| 3 | Administrator | Primary User | High |
| 4 | IT Support Staff | Internal Technical | Medium |
| 5 | Receptionist | Internal Operational | Medium |
| 6 | Medical Regulators | External Compliance | High |
| 7 | Twilio | External Service Provider | Medium |
