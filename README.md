#  PulsePoint — Patient Appointment & Records System

## Overview

PulsePoint is a web-based healthcare management platform designed to simplify and modernise the way patients, doctors, and hospital administrators interact. Built with accessibility and efficiency in mind, PulsePoint bridges the gap between patients and healthcare providers by bringing appointment booking, medical records, and clinical workflows into a single, unified digital platform.

Once completed, PulsePoint will provide:

- **Patients** with a self-service portal to register, book, reschedule, or cancel appointments and securely view their electronic medical records (EMR)
- **Doctors** with a dedicated dashboard to manage daily schedules, write consultation notes, and issue digital prescriptions
- **Administrators** with a system-wide control panel featuring real-time analytics on appointments, revenue, and doctor performance
- **Automated SMS reminders** via Twilio to notify patients of upcoming appointments and reduce no-shows

- **Role-based access control** ensuring every user only accesses what they are authorised to see

## The Problem PulsePoint Solves

Many healthcare facilities still rely on manual, paper-based systems or fragmented digital tools to manage patient appointments and records. This leads to long waiting times, lost records, missed appointments, and poor communication between patients and providers. PulsePoint addresses these challenges by providing one cohesive platform that automates and digitises the end-to-end patient journey — from booking to post-consultation records.

---

## Project Documents

### Assignment 3 — System Specification and Architectural Modelling
-  [SPECIFICATION.md](./specification.md) — Full system specification including domain, problem statement, functional and non-functional requirements
-  [ARCHITECTURE.md](./architecture.md) — C4 architectural diagrams covering Context, Container, Component, and Code levels

### Assignment 4 — Stakeholder and System Requirements Documentation
-  [STAKEHOLDERS.md](./stakeholders.md) — Stakeholder analysis including roles, key concerns, pain points, and success metrics
-  [REQUIREMENTS.md](./requirements.md) — Full functional and non-functional system requirements with acceptance criteria and traceability matrix
-  [REFLECTION.md](./reflection.md) — Reflection on challenges faced in balancing stakeholder needs

---

## Repository Structure

```
pulsepoint/
├── README.md
├── specification.md       # System specification document
├── architecture.md        # C4 architectural diagrams
├── stakeholders.md        # Stakeholder analysis
├── requirements.md        # Functional and non-functional requirements
├── reflection.md          # Stakeholder balancing reflection
├── frontend/              # React application
├── backend/               # Node.js + Express API
└── database/              # PostgreSQL schema and migrations
```

---

## Author

Developed as part of a Software Engineering semester project.