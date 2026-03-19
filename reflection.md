# REFLECTION.md — Challenges in Balancing Stakeholder Needs

**Project:** PulsePoint — Patient Appointment & Records System

---

## 1. Introduction

Gathering and balancing stakeholder requirements is one of the most complex aspects of software engineering. For PulsePoint, seven stakeholders were identified , each with distinct roles, priorities, and expectations. This reflection documents the key challenges encountered when trying to satisfy all stakeholders simultaneously, the trade-offs that had to be made, and the lessons learned throughout the requirements elicitation process.

---

## 2. Challenge 1 — Patient Convenience vs. Security Requirements

One of the most significant tensions encountered was between the **Patient's** desire for a fast, frictionless experience and the **Medical Regulators'** strict requirements for data security and access control.

Patients want to register quickly, log in without friction, and access their records instantly. However, compliance officers require strong authentication, encrypted data storage, session timeouts, and detailed audit logs , all of which add steps and complexity to the user experience.

**How it was balanced:**
The system was designed to use JWT-based authentication with a 24-hour token expiry , long enough to avoid forcing patients to log in multiple times in a day, but short enough to meet security standards. Password hashing using bcrypt and HTTPS encryption were implemented invisibly in the background so they do not impact the user experience at all. The audit logging was made fully automatic, requiring no input from the user.

**Lesson Learned:** Security and usability do not have to be in direct conflict. Many security measures can be implemented transparently at the backend without degrading the front-end experience.

---

## 3. Challenge 2 — Receptionist Workflow vs. Patient Self-Service

The **Receptionist** stakeholder presented an interesting conflict with the **Patient** stakeholder. Patients want full self-service booking capabilities ,  the ability to book, reschedule, and cancel without any staff involvement. However, receptionists are concerned about losing visibility and control over the appointment book, particularly for elderly or non-tech-savvy patients who may still need assistance.

If the system is designed purely for self-service, receptionists become redundant for booking tasks but are still needed for patients who cannot use the system. If the system requires receptionist approval for every booking, it defeats the purpose of self-service.

**How it was balanced:**
The system was designed to support both workflows simultaneously. Patients can self-book online, while receptionists retain access to the same booking interface to assist patients in person or over the phone. Both actions go through the same system, eliminating the risk of double bookings. This way, receptionists transition from manual booking clerks to support staff , reducing their workload rather than replacing them entirely.

**Lesson Learned:** When two stakeholders have conflicting workflows- designing for both simultaneously rather than prioritising one often leads to the most practical solution.

---

## 4. Challenge 3 — Doctor Autonomy vs. Administrator Control

**Doctors** expressed a need for autonomy over their own schedules , they want to block off time for breaks, set their own availability, and control how many appointments they take per day. **Administrators**, on the other hand, need oversight of all schedules to ensure the clinic runs efficiently and that no doctor is underbooked or overburdened.

Giving doctors full control over their availability could result in gaps in clinic coverage that administrators are unaware of. Giving administrators full control over doctor schedules could frustrate doctors and reduce their sense of professional autonomy.

**How it was balanced:**
The system was designed so that doctors can manage their own availability within boundaries set by the administrator — for example, an admin can define core clinic hours during which doctors must remain available, while doctors can adjust their slots within those hours. Administrators retain read-only visibility into all doctor schedules at all times.

**Lesson Learned:** Hierarchical permission structures - where one stakeholder sets the boundaries and another operates within them , are an effective way to balance competing control needs.

---

## 5. Challenge 4 — IT Staff Maintainability vs. Feature Richness

**IT Support Staff** consistently prioritised simplicity, clean architecture, and thorough documentation over feature volume. They were concerned that adding too many features , such as real-time analytics, SMS integration, and complex role-based access , would make the system harder to maintain and more prone to failure.

In contrast, the **Administrator** and **Patient** stakeholders pushed for a feature-rich platform with dashboards, filters, export tools, and automated notifications.

**How it was balanced:**
A modular architecture was adopted so that each feature is self-contained and can be updated, replaced, or removed without affecting the rest of the system. This gave IT staff the maintainability they needed while still allowing all requested features to be included. A clear API documentation requirement was also added as a non-functional requirement to address IT staff concerns about future integrations.

**Lesson Learned:** Architecture decisions — not just feature decisions , are a form of stakeholder compromise. A well-structured system can be both feature-rich and maintainable.

---

## 6. Challenge 5 — Compliance Requirements vs. Project Scope

**Medical Regulators** introduced requirements that significantly expanded the original project scope , particularly around audit logging, data retention policies, and encryption standards. Some of these requirements (such as 5-year log retention and full POPIA compliance) are typically addressed at the infrastructure and legal level, not just at the application level.

For a semester-long individual project, implementing full regulatory compliance was not entirely feasible within the available time and resources.

**How it was balanced:**
The requirements were documented in full to reflect real-world compliance needs, but the implementation scope was clearly defined in SPECIFICATION.md. Compliance-related non-functional requirements (such as audit logging and encryption) that are achievable within the project were marked as in-scope, while infrastructure-level concerns (such as legal data retention enforcement) were noted as future considerations.

**Lesson Learned:** In real-world projects, not all stakeholder requirements can be fully implemented in a first release. Transparent scoping , (clearly documenting what is in and out of scope) is a professional and honest approach to managing stakeholder expectations.

---

## 7. Overall Reflection

The process of identifying and balancing stakeholder needs for PulsePoint revealed that requirements engineering is rarely straightforward. Every stakeholder has legitimate concerns, and many of those concerns directly conflict with one another. The key insight from this process is that **there is rarely a perfect solution** , only well-reasoned trade-offs that prioritise the most critical needs while transparently documenting what could not be fully addressed.

The most valuable skill developed through this exercise was learning to look at the same system from multiple perspectives simultaneously — as a patient, as a doctor, as a compliance officer, and as a developer — and finding designs that serve all of them as effectively as possible.

---

## 8. Summary of Key Trade-offs

| Conflict | Stakeholders Involved | Resolution |
|---|---|---|
| Convenience vs. Security | Patient vs. Regulators | Background security measures that don't impact UX |
| Self-service vs. Staff Control | Patient vs. Receptionist | Dual-access booking system supporting both workflows |
| Doctor Autonomy vs. Admin Oversight | Doctor vs. Administrator | Bounded availability — doctors manage within admin-set hours |
| Feature Richness vs. Maintainability | Admin/Patient vs. IT Staff | Modular architecture isolating each feature |
| Full Compliance vs. Project Scope | Regulators vs. Individual Developer | In-scope compliance features documented; future items noted |