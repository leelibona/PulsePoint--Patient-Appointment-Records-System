# 💓 PulsePoint — Patient Appointment & Records System

## Overview

PulsePoint is a web-based healthcare management platform designed to simplify and modernise the way patients, doctors, and hospital administrators interact. Built with accessibility and efficiency in mind, PulsePoint bridges the gap between patients and healthcare providers by bringing appointment booking, medical records, and clinical workflows into a single, unified digital platform.

Once completed, PulsePoint will provide:

- **Patients** with a self-service portal to register, book, reschedule, or cancel appointments and securely view their electronic medical records (EMR)
- **Doctors** with a dedicated dashboard to manage daily schedules, write consultation notes, and issue digital prescriptions
- **Administrators** with a system-wide control panel featuring real-time analytics on appointments, revenue, and doctor performance
- **Automated SMS reminders** via Twilio to notify patients of upcoming appointments and reduce no-shows
- **Role-based access control** ensuring every user only accesses what they are authorised to see

---

## The Problem PulsePoint Solves

Many healthcare facilities still rely on manual, paper-based systems or fragmented digital tools to manage patient appointments and records. This leads to long waiting times, lost records, missed appointments, and poor communication between patients and providers. PulsePoint addresses these challenges by providing one cohesive platform that automates and digitises the end-to-end patient journey — from booking to post-consultation records.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React + Tailwind CSS |
| Backend | Node.js + Express |
| Database | PostgreSQL |
| Authentication | JWT + Role-Based Access Control |
| SMS Notifications | Twilio API |

---

## Project Documents

### Assignment 3 — System Specification and Architectural Modelling
- 📄 [SPECIFICATION.md](./specification.md) — Full system specification including domain, problem statement, functional and non-functional requirements
- 🏗️ [ARCHITECTURE.md](./architecture.md) — C4 architectural diagrams covering Context, Container, Component, and Code levels

### Assignment 4 — Stakeholder and System Requirements Documentation
- 👥 [STAKEHOLDERS.md](./stakeholders.md) — Stakeholder analysis including roles, key concerns, pain points, and success metrics
- ✅ [REQUIREMENTS.md](./requirements.md) — Full functional and non-functional system requirements with acceptance criteria and traceability matrix
- 💭 [REFLECTION.md](./reflection.md) — Reflection on challenges faced in balancing stakeholder needs

### Assignment 5 — Use Case Modeling and Test Case Development
- 📊 [USECASES.md](./usecases.md) — Use case diagram and written explanation of actors and relationships
- 📋 [USE-CASE-SPECIFICATIONS.md](./use-case-specifications.md) — Detailed specifications for 8 critical use cases
- 🧪 [TEST-CASES.md](./test-cases.md) — Functional and non-functional test cases with traceability
- 💭 [REFLECTION5.md](./reflection5.md) — Reflection on challenges in translating requirements to use cases and tests

### Assignment 6 — Agile User Stories, Backlog, and Sprint Planning
- 🏃 [AGILE-PLANNING.md](./agile-planning.md) — User stories, product backlog, and Sprint 1 plan
- 💭 [REFLECTION6.md](./reflection6.md) — Reflection on Agile planning challenges as a solo developer

### Assignment 7 — GitHub Project Templates and Kanban Board Implementation
- 📊 [TEMPLATE-ANALYSIS.md](./template-analysis.md) — Comparison of GitHub project templates and justification for chosen template
- 📌 [KANBAN-EXPLANATION.md](./kanban-explanation.md) — Definition and purpose of the PulsePoint Kanban board
- 💭 [REFLECTION7.md](./reflection7.md) — Reflection on template selection and comparison with Trello and Jira

### Assignment 8 — Object State Modeling and Activity Workflow Modeling
- 🔄 [STATE-DIAGRAMS.md](./state-diagrams.md) — State transition diagrams for 8 critical system objects
- 🔀 [ACTIVITY-DIAGRAMS.md](./activity-diagrams.md) — Activity workflow diagrams for 8 key system processes
- 💭 [REFLECTION8.md](./reflection8.md) — Reflection on state and activity modeling challenges

### Assignment 9 — Domain Modeling and Class Diagram Development
- 🗂️ [DOMAIN-MODEL.md](./domain-model.md) — Domain entities, attributes, methods, and business rules
- 📐 [CLASS-DIAGRAM.md](./class-diagram.md) — Full UML class diagram in Mermaid with relationships and multiplicity
- 💭 [REFLECTION9.md](./reflection9.md) — Reflection on domain modeling and class diagram design

### Assignment 10 — From Class Diagrams to Code with Creational Patterns
- ☕ [src/](./src/main/java/com/pulsepoint/) — Java source code for all model classes
- 🏭 [creational_patterns/](./src/main/java/com/pulsepoint/creational_patterns/) — All 6 creational design pattern implementations
- 🧪 [tests/](./tests/java/com/pulsepoint/) — Unit tests for all creational patterns
- 📋 [CHANGELOG.md](./CHANGELOG.md) — Summary of all changes and progress

---

## Language Choice and Key Design Decisions

### Language — Java

Java was chosen as the implementation language for PulsePoint for the following reasons:

- **Strong OOP Support** — Java is a purely object-oriented language that naturally supports the class hierarchy defined in the UML class diagram. The `User` base class with `Patient`, `Doctor`, and `Administrator` subclasses maps directly to Java's inheritance model.
- **Design Pattern Compatibility** — All six creational design patterns (Simple Factory, Factory Method, Abstract Factory, Builder, Prototype, Singleton) are well-established in Java with clear, idiomatic implementations. Java's interface and abstract class system makes pattern implementation clean and readable.
- **Type Safety** — Java's strong static typing ensures that relationships between classes (e.g. an `Appointment` always has a valid `patientId` and `doctorId`) are enforced at compile time rather than runtime.
- **Industry Standard** — Java is widely used in enterprise healthcare systems, making it a realistic and appropriate choice for a patient management platform like PulsePoint.

### Key Design Decisions

| Decision | Justification |
|---|---|
| **Abstract `User` class** | Patient, Doctor, and Administrator share common authentication logic (login, logout, resetPassword). Centralising this in an abstract base class avoids duplication and enforces consistency. |
| **Composition for `MedicalRecord` and `TimeSlot`** | A `MedicalRecord` cannot exist without an `Appointment`, and a `TimeSlot` is always owned by a specific booking context. Composition enforces this lifecycle dependency. |
| **Immutable `AuditLog`** | Audit logs have no setters — once created they cannot be modified. This enforces the business rule that audit logs must be tamper-proof, directly addressing the medical regulator's compliance requirement. |
| **`Cloneable` on `TimeSlot`** | `TimeSlot` implements `Cloneable` to support the Prototype design pattern, allowing pre-configured slot templates to be cloned rather than creating new instances from scratch. |
| **Singleton for Database Connection** | A single shared database connection instance prevents multiple connections from being opened simultaneously, improving performance and preventing data inconsistency. |
| **Builder for `Appointment`** | Appointments have many optional and required fields. The Builder pattern allows appointments to be constructed step by step, making the creation process readable and preventing invalid partial objects. |

---

## Repository Structure

```
pulsepoint/
├── README.md
├── CHANGELOG.md                 # Assignment 10 progress tracking
├── specification.md             # System specification document
├── architecture.md              # C4 architectural diagrams
├── stakeholders.md              # Stakeholder analysis
├── requirements.md              # Functional and non-functional requirements
├── reflection.md                # Assignment 4 reflection
├── usecases.md                  # Use case diagram and explanation
├── use-case-specifications.md   # Detailed use case specifications
├── test-cases.md                # Functional and non-functional test cases
├── reflection5.md               # Assignment 5 reflection
├── agile-planning.md            # User stories, backlog and sprint plan
├── reflection6.md               # Assignment 6 reflection
├── template-analysis.md         # GitHub template comparison
├── kanban-explanation.md        # Kanban board definition and purpose
├── reflection7.md               # Assignment 7 reflection
├── state-diagrams.md            # State transition diagrams
├── activity-diagrams.md         # Activity workflow diagrams
├── reflection8.md               # Assignment 8 reflection
├── domain-model.md              # Domain model entities and business rules
├── class-diagram.md             # UML class diagram in Mermaid
├── reflection9.md               # Assignment 9 reflection
├── src/
│   └── main/java/com/pulsepoint/
│       ├── models/              # All Java model classes
│       └── creational_patterns/ # All 6 creational design patterns
├── tests/
│   └── java/com/pulsepoint/     # Unit tests for all patterns
├── frontend/                    # React application
├── backend/                     # Node.js + Express API
└── database/                    # PostgreSQL schema and migrations
```

---

## Author

Developed as part of a Software Engineering semester project.