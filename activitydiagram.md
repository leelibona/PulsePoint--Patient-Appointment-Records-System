# ACTIVITY-DIAGRAMS.md — Activity Workflow Modeling for PulsePoint

**Assignment:** 8 — Object State Modeling and Activity Workflow Modeling

---

## 1. Introduction

This document presents activity diagrams for 8 critical workflows in the PulsePoint system. Each diagram models the step-by-step flow of a process, including start and end nodes, actions, decision points, parallel actions, and swimlanes showing which actor is responsible for each step. All diagrams are aligned with the functional requirements from Assignment 4 and the user stories from Assignment 6.

---

## 2. Activity Diagram 1 — User Registration

```mermaid
flowchart TD
    Start([Start]) --> A[Patient navigates to registration page]

    subgraph Patient
        A --> B[Patient fills in registration form]
        B --> C[Patient clicks Register button]
    end

    subgraph System
        C --> D{Are all fields valid?}
        D -- No --> E[Highlight invalid fields with error messages]
        E --> B
        D -- Yes --> F{Is email already registered?}
        F -- Yes --> G[Display: Account already exists]
        G --> B
        F -- No --> H[Hash password using bcrypt]
        H --> I[Save new account to database]
        I --> J[Display registration success message]
        J --> K[Redirect patient to login page]
    end

    K --> End([End])
```

### Explanation
This diagram models the complete patient registration workflow. The Patient swimlane covers form input and submission, while the System swimlane handles all validation, security, and database operations. The decision nodes address two key alternative flows — invalid input and duplicate email — ensuring the system handles errors gracefully before saving any data.

**Functional Requirement Mapping:** Functional Requirement 01 — User Registration.
**User Story Mapping:** US001 — As a patient, I want to register an account.
**Stakeholder Concern Addressed:** Patient's need for a simple and fast registration process.

---

## 3. Activity Diagram 2 — User Login and Role-Based Redirect

```mermaid
flowchart TD
    Start([Start]) --> A[User navigates to login page]

    subgraph User
        A --> B[User enters email and password]
        B --> C[User clicks Login button]
    end

    subgraph System
        C --> D{Does email exist in database?}
        D -- No --> E[Display: Incorrect email or password]
        E --> B
        D -- Yes --> F{Is account active?}
        F -- No --> G[Display: Account has been deactivated]
        G --> End1([End])
        F -- Yes --> H{Does password match hash?}
        H -- No --> E
        H -- Yes --> I[Generate JWT token]
        I --> J{What is the user role?}
        J -- Patient --> K[Redirect to Patient Portal]
        J -- Doctor --> L[Redirect to Doctor Dashboard]
        J -- Administrator --> M[Redirect to Admin Control Panel]
    end

    K --> End2([End])
    L --> End2
    M --> End2
```

### Explanation
This diagram models the login workflow with role-based redirection. Three decision nodes handle the key validation steps — email existence, account status, and password verification. The final decision node routes the authenticated user to their role-specific dashboard. This ensures that every user sees only the interface relevant to their role.

**Functional Requirement Mapping:** Functional Requirement 02 — User Authentication and Role-Based Access.
**User Story Mapping:** US002 — As a patient, I want to log in to the system.
**Stakeholder Concern Addressed:** Security requirement that each role only accesses permitted features.

---

## 4. Activity Diagram 3 — Book Appointment

```mermaid
flowchart TD
    Start([Start]) --> A[Patient navigates to Book Appointment page]

    subgraph Patient
        A --> B[Patient searches for doctor by name or specialisation]
        B --> C[Patient selects a doctor from results]
        C --> D[Patient selects available date and time slot]
        D --> E[Patient clicks Confirm Booking]
    end

    subgraph System
        E --> F{Is the slot still available?}
        F -- No --> G[Display: Slot no longer available]
        G --> D
        F -- Yes --> H[Create appointment record in database]
        H --> I[Mark time slot as unavailable]
        I --> J[Trigger SMS confirmation via Twilio]
        J --> K[Display booking confirmation screen]
    end

    subgraph Twilio
        J --> L[Send SMS confirmation to patient]
        L --> M{Was SMS delivered?}
        M -- Yes --> N[Log delivery success]
        M -- No --> O[Schedule retry after 10 minutes]
    end

    K --> End([End])
    N --> End
    O --> End
```

### Explanation
This diagram models the full appointment booking workflow across three swimlanes — Patient, System, and Twilio. The parallel paths after the booking is confirmed show that the confirmation screen is displayed to the patient at the same time as the SMS is being sent — these are concurrent actions. The Twilio swimlane shows the SMS delivery and retry logic running independently of the main booking flow.

**Functional Requirement Mapping:** Functional Requirement 03 — Appointment Booking, Functional Requirement 08 — SMS Reminders.
**User Story Mapping:** US004 — As a patient, I want to book an appointment. US007 — As a patient, I want to receive an SMS reminder.
**Stakeholder Concern Addressed:** Patient's need for convenient self-service booking and automated confirmation.

---

## 5. Activity Diagram 4 — Reschedule Appointment

```mermaid
flowchart TD
    Start([Start]) --> A[Patient navigates to My Appointments]

    subgraph Patient
        A --> B[Patient selects appointment to reschedule]
        B --> C{Is appointment more than 2 hours away?}
        C -- No --> D[Display: Cannot reschedule within 2 hours]
        D --> End1([End])
        C -- Yes --> E[Patient clicks Reschedule button]
        E --> F[Patient selects new date and time slot]
        F --> G[Patient clicks Confirm Reschedule]
    end

    subgraph System
        G --> H{Is the new slot available?}
        H -- No --> I[Display: Slot no longer available]
        I --> F
        H -- Yes --> J[Update appointment with new date and time]
        J --> K[Release old slot back to available pool]
        K --> L[Trigger SMS notification via Twilio]
        L --> M[Display reschedule confirmation screen]
    end

    subgraph Twilio
        L --> N[Send SMS reschedule notification to patient]
    end

    M --> End2([End])
    N --> End2
```

### Explanation
This diagram models the appointment rescheduling workflow. The guard condition "more than 2 hours away" is implemented as a decision node in the Patient swimlane, immediately blocking the reschedule attempt if the condition is not met. The System swimlane handles the slot availability check, database update, and old slot release as sequential steps before triggering the SMS notification.

**Functional Requirement Mapping:** Functional Requirement 04 — Appointment Rescheduling and Cancellation.
**User Story Mapping:** US005 — As a patient, I want to reschedule an existing appointment.
**Stakeholder Concern Addressed:** Patient's need for flexibility in managing their appointments.

---

## 6. Activity Diagram 5 — Cancel Appointment

```mermaid
flowchart TD
    Start([Start]) --> A[Patient navigates to My Appointments]

    subgraph Patient
        A --> B[Patient selects appointment to cancel]
        B --> C{Is appointment more than 2 hours away?}
        C -- No --> D[Display: Cannot cancel within 2 hours]
        D --> End1([End])
        C -- Yes --> E[Patient clicks Cancel Appointment]
        E --> F[System displays confirmation prompt]
        F --> G{Does patient confirm cancellation?}
        G -- No --> H[Close prompt — no changes made]
        H --> End2([End])
        G -- Yes --> I[Patient confirms cancellation]
    end

    subgraph System
        I --> J[Update appointment status to Cancelled]
        J --> K[Release time slot back to available pool]
        K --> L[Trigger SMS cancellation notification via Twilio]
        L --> M[Display cancellation confirmation message]
    end

    subgraph Twilio
        L --> N[Send SMS cancellation notification to patient]
    end

    M --> End3([End])
    N --> End3
```

### Explanation
This diagram models the cancellation workflow with two guard conditions — the 2-hour restriction and the patient confirmation prompt. The confirmation prompt prevents accidental cancellations, which was identified as an important usability requirement. The System swimlane ensures the slot is immediately released back to the available pool so other patients can book it.

**Functional Requirement Mapping:** Functional Requirement 04 — Appointment Rescheduling and Cancellation.
**User Story Mapping:** US006 — As a patient, I want to cancel an appointment.
**Stakeholder Concern Addressed:** Patient's need for control over their appointments and receptionist's need for accurate slot availability.

---

## 7. Activity Diagram 6 — Create Medical Record

```mermaid
flowchart TD
    Start([Start]) --> A[Doctor navigates to appointment schedule]

    subgraph Doctor
        A --> B[Doctor selects completed appointment]
        B --> C[Doctor clicks Create Medical Record]
        C --> D[Doctor enters diagnosis, consultation notes and prescription]
        D --> E[Doctor clicks Save Record]
    end

    subgraph System
        E --> F{Are all required fields completed?}
        F -- No --> G[Highlight missing fields with error message]
        G --> D
        F -- Yes --> H[Save medical record to database]
        H --> I[Link record to patient profile and appointment]
        I --> J[Update appointment status to Completed]
        J --> K[Log record creation in audit trail]
        K --> L[Display success message to doctor]
    end

    subgraph AuditSystem
        K --> M[Create audit log entry with doctor ID and timestamp]
    end

    L --> End([End])
    M --> End
```

### Explanation
This diagram models the medical record creation workflow with parallel actions — the audit log entry is created at the same time as the success message is displayed to the doctor. The System swimlane handles all data persistence and status updates sequentially, while the AuditSystem swimlane runs concurrently to ensure every record creation is logged for compliance purposes.

**Functional Requirement Mapping:** Functional Requirement 06 — Electronic Medical Records Creation, Functional Requirement 12 — Audit Trail Logging.
**User Story Mapping:** US010 — As a doctor, I want to create an electronic medical record.
**Stakeholder Concern Addressed:** Doctor's need for efficient digital record keeping and regulator's need for a complete audit trail.

---

## 8. Activity Diagram 7 — View Medical Records

```mermaid
flowchart TD
    Start([Start]) --> A[User navigates to Medical Records section]

    subgraph User
        A --> B{What is the user role?}
        B -- Patient --> C[Patient requests their own records]
        B -- Doctor --> D[Doctor selects a patient from appointment list]
    end

    subgraph System
        C --> E{Does patient have any records?}
        E -- No --> F[Display: No records found]
        F --> End1([End])
        E -- Yes --> G[Retrieve records from database]

        D --> H{Does doctor have appointment history with patient?}
        H -- No --> I[Display: Unauthorised access denied]
        I --> End2([End])
        H -- Yes --> G

        G --> J[Display records in reverse chronological order]
        J --> K[User selects a specific record to view]
        K --> L[Display full record details]
        L --> M[Log access event in audit trail]
    end

    subgraph AuditSystem
        M --> N[Create audit log entry with user ID and timestamp]
    end

    L --> End3([End])
    N --> End3
```

### Explanation
This diagram models the medical records viewing workflow for both patients and doctors. The initial role decision node splits the flow into two paths — a patient viewing their own records and a doctor viewing a patient's records. Both paths converge at the database retrieval step. The AuditSystem swimlane runs in parallel to log every record access, regardless of the user role.

**Functional Requirement Mapping:** Functional Requirement 07 — Patient Medical Records Access, Functional Requirement 12 — Audit Trail Logging.
**User Story Mapping:** US008 — As a patient, I want to view my medical records. US014 — As a regulator, I want to access audit logs.
**Stakeholder Concern Addressed:** Patient's right to access their own records and regulator's need for a complete access audit trail.

---

## 9. Activity Diagram 8 — Admin Analytics Dashboard

```mermaid
flowchart TD
    Start([Start]) --> A[Administrator navigates to Analytics Dashboard]

    subgraph Administrator
        A --> B[Admin views default dashboard metrics]
        B --> C{Does admin want to filter data?}
        C -- Yes --> D[Admin selects filter: date range, doctor, or status]
        D --> E[Admin clicks Apply Filter]
        C -- No --> F[Admin views unfiltered metrics]
        E --> F
        F --> G{Does admin want to export a report?}
        G -- No --> End1([End])
        G -- Yes --> H[Admin clicks Export Report]
    end

    subgraph System
        A --> I[Query database for all appointment and user data]
        I --> J[Calculate metrics: total appointments, completion rate, no-show rate]
        J --> K[Display metrics on dashboard with charts]
        E --> L[Re-query database with applied filters]
        L --> K
        H --> M{Does report generate successfully?}
        M -- No --> N[Display: Export failed. Please try again]
        N --> H
        M -- Yes --> O[Generate PDF or CSV report]
        O --> P[Trigger file download for admin]
    end

    P --> End2([End])
```

### Explanation
This diagram models the admin analytics workflow including filtering and report export. The parallel structure between the Administrator and System swimlanes shows that the system begins querying the database as soon as the admin navigates to the dashboard — the data retrieval does not wait for any user action. The export flow includes a retry path for failed report generation, addressing the alternative flow identified in the use case specifications.

**Functional Requirement Mapping:** Functional Requirement 10 — Administrator Analytics Dashboard.
**User Story Mapping:** US012 — As an administrator, I want to view a real-time analytics dashboard.
**Stakeholder Concern Addressed:** Administrator's need for real-time operational visibility and the ability to generate exportable reports.