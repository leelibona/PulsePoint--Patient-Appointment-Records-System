# ROADMAP.md — PulsePoint Development Roadmap

**Project:** PulsePoint — Patient Appointment & Records System

This roadmap outlines the planned features and improvements for PulsePoint. It is organised by priority and release phase. Community contributions are welcome on any of the items listed here — check the Issues tab for tasks labelled `feature-request`.

---

## ✅ Completed (Assignments 3–13)

- Domain model with 8 entities
- Repository layer with in-memory and database stubs
- 6 Creational design patterns
- Service layer with business rule enforcement
- REST API with 22 endpoints across 3 entities
- OpenAPI/Swagger documentation
- CI/CD pipeline with GitHub Actions
- Branch protection and PR workflow
- 91 unit and integration tests

---

## 🚀 Phase 1 — Core Feature Completion (Short Term)

These are the next immediate priorities to make PulsePoint fully functional.

| Feature | Description | Label |
|---|---|---|
| **Doctor availability management** | Allow doctors to set and update their weekly availability slots via the API | `feature-request` |
| **Patient medical records API** | Add REST endpoints for creating and viewing electronic medical records | `feature-request` |
| **Admin analytics endpoints** | Expose analytics data (appointment counts, no-show rates) via REST API | `feature-request` |
| **Audit log endpoints** | Allow administrators to query audit logs via the API | `feature-request` |
| **JWT authentication** | Implement real JWT-based login and role-based access control at the API level | `feature-request` |
| **Input validation layer** | Add Bean Validation (`@Valid`) annotations to all request bodies | `good-first-issue` |
| **Pagination support** | Add pagination to `GET /api/patients` and `GET /api/appointments` endpoints | `good-first-issue` |

---

## 🔧 Phase 2 — Infrastructure and Persistence (Medium Term)

| Feature | Description | Label |
|---|---|---|
| **PostgreSQL integration** | Replace in-memory repositories with real PostgreSQL database using JDBC | `feature-request` |
| **Database migrations** | Add Flyway or Liquibase for versioned database schema management | `feature-request` |
| **Redis caching** | Cache frequently accessed data (doctor lists, slot availability) using Redis | `feature-request` |
| **Docker support** | Add `Dockerfile` and `docker-compose.yml` for containerised deployment | `good-first-issue` |
| **Environment configuration** | Move sensitive config (DB credentials, API keys) to environment variables | `good-first-issue` |

---

## 📱 Phase 3 — Frontend and Notifications (Medium Term)

| Feature | Description | Label |
|---|---|---|
| **React patient portal** | Build the patient-facing web portal using React and Tailwind CSS | `feature-request` |
| **React doctor dashboard** | Build the doctor schedule and EMR management dashboard | `feature-request` |
| **React admin panel** | Build the admin analytics and user management dashboard | `feature-request` |
| **Twilio SMS integration** | Connect the real Twilio API for live SMS appointment reminders | `good-first-issue` |
| **Email notifications** | Add email confirmation for appointments using JavaMail or SendGrid | `good-first-issue` |

---

## 🔒 Phase 4 — Security and Compliance (Long Term)

| Feature | Description | Label |
|---|---|---|
| **HTTPS/TLS enforcement** | Configure SSL certificate and enforce HTTPS in production | `feature-request` |
| **POPIA compliance audit** | Review all data handling against South Africa's POPIA regulations | `feature-request` |
| **Password reset flow** | Implement secure password reset via email token | `good-first-issue` |
| **Rate limiting** | Add API rate limiting to prevent abuse and denial-of-service attacks | `feature-request` |
| **Two-factor authentication** | Add optional 2FA for doctor and admin accounts | `feature-request` |

---

## 📊 Phase 5 — Advanced Features (Long Term)

| Feature | Description | Label |
|---|---|---|
| **Telemedicine support** | Add video consultation booking using a WebRTC integration | `feature-request` |
| **Multi-clinic support** | Extend the system to support multiple clinic branches | `feature-request` |
| **Prescription management** | Allow doctors to issue digital prescriptions linked to medical records | `feature-request` |
| **Reporting and exports** | Generate PDF and CSV reports for admin analytics | `good-first-issue` |
| **Mobile app** | Build a React Native mobile app for patients | `feature-request` |

---

## 💡 How to Contribute to the Roadmap

If you have a feature idea that is not listed here:
1. Open a **GitHub Issue** with the label `feature-request`
2. Describe the feature, why it is useful, and how it relates to existing functionality
3. The maintainer will review and add it to the roadmap if appropriate

See [contributing.md](./contributing.md) for full contribution guidelines.