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

<<<<<<< HEAD
### Assignment 14 — Peer Review, Onboarding, and Open-Source Collaboration
- 🤝 [CONTRIBUTING.md](./CONTRIBUTING.md) — Setup instructions, coding standards, and PR guidelines
- 🗺️ [ROADMAP.md](./ROADMAP.md) — Planned features across 5 development phases
- 📊 [VOTING_RESULTS.md](./VOTING_RESULTS.md) — Peer engagement stars and forks
- 💭 [REFLECTION14.md](./REFLECTION14.md) — Reflection on open-source collaboration
- 📜 [LICENSE](./LICENSE) — MIT License

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Git

### Quick Setup
```bash
# Clone the repository
git clone https://github.com/leelibona/PulsePoint--Patient-Appointment-Records-System.git
cd PulsePoint--Patient-Appointment-Records-System

# Install dependencies
mvn clean install

# Run all tests
mvn clean test

# Start the application
mvn spring-boot:run
```

API: `http://localhost:8080/api`
Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Features Available for Contribution

| Feature | Difficulty | Label |
|---|---|---|
| Add input validation with `@Valid` annotations | Easy | `good-first-issue` |
| Add pagination to GET endpoints | Easy | `good-first-issue` |
| Add Docker and docker-compose support | Easy | `good-first-issue` |
| Add Twilio SMS integration | Medium | `good-first-issue` |
| Add email notifications | Medium | `good-first-issue` |
| Implement PostgreSQL database | Medium | `feature-request` |
| Build React patient portal | Hard | `feature-request` |
| Implement JWT authentication | Hard | `feature-request` |
| Add Redis caching | Hard | `feature-request` |

See [ROADMAP.md](./ROADMAP.md) for the full list and [CONTRIBUTING.md](./CONTRIBUTING.md) to get started.

---

## License

This project is licensed under the **MIT License** — see [LICENSE](./LICENSE) for details.

---
- 🔒 [PROTECTION.md](./PROTECTION.md) — Branch protection rules and justification
- ⚙️ [.github/workflows/ci.yml](./.github/workflows/ci.yml) — CI/CD pipeline workflow


- ⚙️ [services/](./src/main/java/com/pulsepoint/services/) — PatientService, DoctorService, AppointmentService
- 🌐 [api/controllers/](./src/main/java/com/pulsepoint/api/controllers/) — REST API controllers for all 3 entities
- ⚠️ [api/exceptions/](./src/main/java/com/pulsepoint/api/exceptions/) — Global exception handler
- 📄 [docs/openapi.yaml](./docs/openapi.yaml) — Full OpenAPI 3.0.3 documentation for all 22 endpoints
- 🧪 [tests/services/](./tests/services/) — 24 service layer unit tests
- 🧪 [tests/api/](./tests/api/) — 20 API integration tests


- 🗃️ [repositories/](./src/main/java/com/pulsepoint/repositories/) — Generic and entity-specific repository interfaces
- 💾 [repositories/inmemory/](./src/main/java/com/pulsepoint/repositories/inmemory/) — In-memory HashMap implementations
- 🔮 [repositories/database/](./src/main/java/com/pulsepoint/repositories/database/) — Database stub implementations for future use
- 🏭 [factories/RepositoryFactory.java](./src/main/java/com/pulsepoint/factories/RepositoryFactory.java) — Storage abstraction factory
- 🧪 [tests/RepositoryTest.java](./tests/java/com/pulsepoint/RepositoryTest.java) — 30 unit tests for all repository implementations

---

=======
### Assignment 13 — Implementing CI/CD with GitHub Actions
- 🔒 [PROTECTION.md](./PROTECTION.md) — Branch protection rules and justification
- ⚙️ [.github/workflows/ci.yml](./.github/workflows/ci.yml) — CI/CD pipeline workflow


- ⚙️ [services/](./src/main/java/com/pulsepoint/services/) — PatientService, DoctorService, AppointmentService
- 🌐 [api/controllers/](./src/main/java/com/pulsepoint/api/controllers/) — REST API controllers for all 3 entities
- ⚠️ [api/exceptions/](./src/main/java/com/pulsepoint/api/exceptions/) — Global exception handler
- 📄 [docs/openapi.yaml](./docs/openapi.yaml) — Full OpenAPI 3.0.3 documentation for all 22 endpoints
- 🧪 [tests/services/](./tests/services/) — 24 service layer unit tests
- 🧪 [tests/api/](./tests/api/) — 20 API integration tests


- 🗃️ [repositories/](./src/main/java/com/pulsepoint/repositories/) — Generic and entity-specific repository interfaces
- 💾 [repositories/inmemory/](./src/main/java/com/pulsepoint/repositories/inmemory/) — In-memory HashMap implementations
- 🔮 [repositories/database/](./src/main/java/com/pulsepoint/repositories/database/) — Database stub implementations for future use
- 🏭 [factories/RepositoryFactory.java](./src/main/java/com/pulsepoint/factories/RepositoryFactory.java) — Storage abstraction factory
- 🧪 [tests/RepositoryTest.java](./tests/java/com/pulsepoint/RepositoryTest.java) — 30 unit tests for all repository implementations

---

>>>>>>> 6e55ce7ae48b056686e26c51a1d4a3c9799dceff
## CI/CD Pipeline

PulsePoint uses **GitHub Actions** for continuous integration and continuous deployment.

### How the Pipeline Works

```
Push to any branch          Pull Request to main
        ↓                           ↓
  CI Job triggers             CI Job triggers
        ↓                           ↓
  All 91 tests run           All 91 tests run
        ↓                           ↓
  Results logged           Pass? → PR can merge
                           Fail? → PR is blocked
                                     ↓
                              Merged to main
                                     ↓
                          CD Job builds JAR artifact
                                     ↓
                        JAR uploaded to GitHub Actions
```

### CI Job — Runs on every push and PR
- Sets up Java 17 environment
- Caches Maven dependencies for faster builds
- Runs `mvn clean test` — all unit and integration tests
- Uploads test reports as artifacts (retained 7 days)
- Blocks PR merge if any test fails

### CD Job — Runs only when merged to main
- Builds the JAR using `mvn clean package`
- Names it `pulsepoint-{build_number}.jar`
- Uploads as a downloadable GitHub Actions artifact (retained 30 days)

### Workflow File
`.github/workflows/ci.yml`

---

## How to Run Tests Locally

### Prerequisites
- Java 17 or higher installed
- Maven 3.8+ installed

### Run all tests:
```bash
mvn clean test
```

### Run a specific test class:
```bash
mvn test -Dtest=ServiceLayerTest
mvn test -Dtest=RepositoryTest
mvn test -Dtest=CreationalPatternsTest
mvn test -Dtest=ApiControllerTest
```

### Run the Spring Boot application locally:
```bash
mvn spring-boot:run
```
API will be available at: `http://localhost:8080/api`
Swagger UI will be available at: `http://localhost:8080/swagger-ui.html`

### Build the JAR manually:
```bash
mvn clean package
```
JAR will be generated at: `target/pulsepoint-1.0.0.jar`

---

## Branch Protection Rules

The `main` branch is protected with the following rules:
- ✅ All changes must go through a Pull Request
- ✅ At least 1 approval required before merging
- ✅ CI tests must pass before merging
- ✅ Branch must be up to date with main before merging
- ✅ No direct pushes to main allowed — not even by administrators

See [PROTECTION.md](./PROTECTION.md) for full justification.

---

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
| **Generic `Repository<T, ID>` interface** | Using Java generics for the base repository interface avoids duplicating CRUD method signatures across every entity-specific repository. All five repositories share the same base contract while adding entity-specific query methods. |
| **Factory Pattern over Dependency Injection** | The `RepositoryFactory` was chosen over Dependency Injection because PulsePoint is a solo project without a DI framework like Spring. The factory provides the same storage-swapping benefit — changing "MEMORY" to "DATABASE" in one place switches all repositories — without requiring additional framework setup. |
| **In-Memory HashMap for default storage** | The HashMap implementation requires no external dependencies, making it ideal for unit testing and development. The repository interfaces ensure this can be swapped for a real PostgreSQL database in production without changing any business logic. |
| **`AuditLog` delete blocked at interface level** | The `AuditLogRepository` interface overrides `delete()` with a default method that throws `UnsupportedOperationException`. This enforces the compliance rule that audit logs cannot be deleted at the architecture level, not just at the application level. |

---

## Repository Structure

```
pulsepoint/
├── README.md
├── CHANGELOG.md                 # Project progress tracking
├── PROTECTION.md                # Branch protection rules
├── pom.xml                      # Maven build configuration
├── .github/
│   └── workflows/
│       └── ci.yml               # CI/CD GitHub Actions pipeline
├── specification.md
├── architecture.md
├── stakeholders.md
├── requirements.md
├── reflection.md
├── usecases.md
├── use-case-specifications.md
├── test-cases.md
├── reflection5.md
├── agile-planning.md
├── reflection6.md
├── template-analysis.md
├── kanban-explanation.md
├── reflection7.md
├── state-diagrams.md
├── activity-diagrams.md
├── reflection8.md
├── domain-model.md
├── class-diagram.md
├── reflection9.md
├── docs/
│   └── openapi.yaml             # OpenAPI 3.0.3 API documentation
├── src/
│   └── main/
│       ├── java/com/pulsepoint/
│       │   ├── PulsePointApplication.java
│       │   ├── models/              # Domain model classes
│       │   ├── creational_patterns/ # 6 creational design patterns
│       │   ├── repositories/        # Repository interfaces
│       │   │   ├── inmemory/        # In-memory implementations
│       │   │   └── database/        # Database stub implementations
│       │   ├── factories/           # RepositoryFactory
│       │   ├── services/            # Business logic services
│       │   └── api/
│       │       ├── controllers/     # REST API controllers
│       │       └── exceptions/      # Global exception handler
│       └── resources/
│           └── application.properties
└── tests/
    ├── java/com/pulsepoint/     # Creational pattern + repository tests
    ├── services/                # Service layer unit tests
    └── api/                     # API integration tests
```

---

## Author

Developed as part of a Software Engineering semester project.