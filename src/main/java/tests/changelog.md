# CHANGELOG.md — PulsePoint Patient Appointment & Records System

All notable changes to the PulsePoint project are documented in this file.


---

## [Assignment 10] — Class Implementation and Creational Patterns

### Added — Model Classes (`/src/main/java/com/pulsepoint/models/`)

- **User.java** — Abstract base class for all system users. Includes login(), logout(), resetPassword(), and deactivateAccount() methods. Enforces shared authentication logic across Patient, Doctor, and Administrator.
- **Patient.java** — Extends User. Adds patient-specific attributes (dateOfBirth, gender, profileStatus) and methods (viewMedicalRecords(), searchDoctors(), bookAppointment()).
- **Doctor.java** — Extends User. Adds doctor-specific attributes (specialisation, qualifications) and methods (viewSchedule(), createMedicalRecord(), markAppointmentStatus()).
- **Administrator.java** — Extends User. Adds admin-specific methods (createDoctorAccount(), viewAnalytics(), viewAuditLogs(), deactivateUserAccount()).
- **Appointment.java** — Core entity linking Patient to Doctor via a TimeSlot. Includes full lifecycle methods (confirm(), reschedule(), cancel(), markCompleted(), markNoShow()).
- **MedicalRecord.java** — Created by Doctor after a consultation. Includes save(), update(), flagForReview(), and resolveFlag(). Enforces field completion before saving.
- **TimeSlot.java** — Represents a doctor's available slot. Implements Cloneable for Prototype pattern support. Includes reserve(), book(), release(), and markCompleted().
- **SMSNotification.java** — Handles SMS delivery via Twilio simulation. Includes send(), retry() with one retry limit, and logDeliveryStatus().
- **AuditLog.java** — Immutable audit log entry. No setters — enforces tamper-proof compliance. Includes store(), archive(), flag(), and escalate().
- **AnalyticsReport.java** — Admin report class. Includes generate(), export() in PDF or CSV format, and applyFilter().

---

### Added — Creational Design Patterns (`/src/main/java/com/pulsepoint/creational_patterns/`)

- **Simple Factory** (`simple_factory/UserFactory.java`) — Centralises creation of Patient, Doctor, and Administrator objects based on a role string. Throws IllegalArgumentException for unknown roles.

- **Factory Method** (`factory_method/`) — Defines a NotificationSender interface with a factory method createNotification(). SMSNotificationSender and EmailNotificationSender are concrete implementations that each create their own notification type.

- **Abstract Factory** (`abstract_factory/`) — ReportFactory interface creates families of report components (ReportHeader, ReportBody, ReportExporter). PatientReportFactory and FinancialReportFactory are concrete implementations. ReportGenerator acts as the client.

- **Builder** (`builder/`) — AppointmentBuilder constructs Appointment objects step by step with required fields (appointmentId, patientId, doctorId, slotId) and optional fields (notes, consultationType, smsReminderEnabled). Throws IllegalStateException if required fields are null.

- **Prototype** (`prototype/`) — TimeSlotCache stores pre-configured slot templates (Morning30, Afternoon30, Evening30). TimeSlotPrototype implements Cloneable and produces unique clones with new IDs for each doctor.

- **Singleton** (`singleton/DatabaseConnection.java`) — Ensures a single shared PostgreSQL database connection throughout the application. Implements double-checked locking for thread safety. Tracks query count.

---

### Added — Unit Tests (`/tests/java/com/pulsepoint/CreationalPatternsTest.java`)

- **Simple Factory Tests (4)** — Verifies correct Patient, Doctor, and Administrator creation. Verifies IllegalArgumentException for unknown roles.
- **Factory Method Tests (4)** — Verifies SMS and Email senders create correct notification types. Verifies status changes to Delivered after send().
- **Abstract Factory Tests (4)** — Verifies Patient and Financial report generation. Verifies exception for unknown report types.
- **Builder Tests (5)** — Verifies required fields, default values, optional fields, exception for missing fields, and SMS reminder default.
- **Prototype Tests (5)** — Verifies unique IDs, availability status, doctor ID assignment, distinct object references, and exception for unknown templates.
- **Singleton Tests (5)** — Verifies same instance returned, connection status, query count increment, and thread safety across 10 concurrent threads.

**Total Test Count: 27 unit tests**

---

## [Assignment 9] — Domain Modeling and Class Diagram

### Added
- **DOMAIN-MODEL.md** — 8 domain entities with attributes, methods, relationships, and 17 business rules. Full requirements traceability table.
- **CLASS-DIAGRAM.md** — Full UML class diagram in Mermaid.js with inheritance, composition, aggregation, and association relationships. Multiplicity defined on all relationships.
- **REFLECTION9.md** — Reflection on domain modeling and class diagram design challenges.

---

## [Assignment 8] — Object State Modeling and Activity Workflow Modeling

### Added
- **STATE-DIAGRAMS.md** — 8 state transition diagrams for User Account, Appointment, Medical Record, SMS Notification, Doctor Availability Slot, Patient Profile, Admin Report, and Audit Log Entry.
- **ACTIVITY-DIAGRAMS.md** — 8 activity workflow diagrams with swimlanes for User Registration, Login, Book Appointment, Reschedule, Cancel, Create Medical Record, View Medical Records, and Admin Analytics.
- **REFLECTION8.md** — Reflection on granularity, parallel actions, and comparing state vs activity diagrams.

---

## [Assignment 7] — GitHub Project Templates and Kanban Board

### Added
- **TEMPLATE-ANALYSIS.md** — Comparison of 4 GitHub project templates (Team Planning, Kanban, Feature Release, Bug Tracker). Justification for selecting Kanban template.
- **KANBAN-EXPLANATION.md** — Definition of Kanban board, WIP limits, column explanations, and Agile principles alignment.
- **REFLECTION7.md** — Challenges in template selection and comparison with Trello and Jira.
- **GitHub Kanban Board** — Created with 5 columns: To Do, In Progress, Testing, Blocked, Done. Sprint 1 tasks added as GitHub Issues with labels and assignments.

---

## [Assignment 6] — Agile Planning

### Added
- **AGILE-PLANNING.md** — 14 user stories in INVEST format, MoSCoW prioritised product backlog with Fibonacci story points, Sprint 1 plan with 6 stories and 15 tasks, and traceability matrix.
- **REFLECTION6.md** — Reflection on Agile planning challenges as a solo developer.

---

## [Assignment 5] — Use Case Modeling and Test Case Development

### Added
- **USECASES.md** — Use case diagram with 6 actors and 10 use cases. Written explanation of relationships and stakeholder alignment.
- **USE-CASE-SPECIFICATIONS.md** — 8 detailed use case specifications with basic flows and alternative flows.
- **TEST-CASES.md** — 10 functional test cases and 2 non-functional test cases (performance and security).
- **REFLECTION5.md** — Reflection on translating requirements to use cases and tests.

---

## [Assignment 4] — Stakeholder and System Requirements

### Added
- **STAKEHOLDERS.md** — 7 stakeholders with roles, key concerns, pain points, and success metrics.
- **REQUIREMENTS.md** — 12 functional requirements and 13 non-functional requirements across 6 categories, with full traceability matrix.
- **REFLECTION.md** — Reflection on balancing stakeholder needs.

---

## [Assignment 3] — System Specification and Architectural Modeling

### Added
- **README.md** — Project overview, tech stack, and links to all documentation.
- **SPECIFICATION.md** — Domain description, problem statement, functional and non-functional requirements, and feasibility justification.
- **ARCHITECTURE.md** — C4 diagrams (Context, Container, Component, Code) using Mermaid.
- **.gitignore** — Excludes .vs folder and other Visual Studio temporary files.