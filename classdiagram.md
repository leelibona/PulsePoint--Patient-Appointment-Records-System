# Class Diagram – PulsePoint Patient Appointment & Records System  

---

## 1. Mermaid.js Class Diagram  

```mermaid
classDiagram

## ======================
## BASE CLASS (INHERITANCE)
## ======================

class User {
    -userId: String
    -fullName: String
    -email: String
    -passwordHash: String
    -phone: String
    -role: String
    -status: String
    +register()
    +login()
    +logout()
    +deactivate()
    +reactivate()
}

class Patient {
    -dateOfBirth: Date
    -gender: String
    -profileStatus: String
    +updateProfile()
    +viewMedicalRecords()
    +searchDoctors()
}

class Doctor {
    -specialisation: String
    -qualifications: String
    +viewSchedule()
    +markAppointmentStatus()
    +createMedicalRecord()
}

class Administrator {
    +createDoctor()
    +manageUsers()
}

User <|-- Patient
User <|-- Doctor
User <|-- Administrator

## ======================
## CORE CLASSES
## ======================

class Appointment {
    -appointmentId: String
    -status: String
    -createdAt: DateTime
    +book()
    +confirm()
    +reschedule()
    +cancel()
    +markNoShow()
    +startConsultation()
    +complete()
}

class TimeSlot {
    -slotId: String
    -date: Date
    -startTime: String
    -endTime: String
    -status: String
    +reserve()
    +book()
    +release()
    +markCompleted()
}

class MedicalRecord {
    -recordId: String
    -diagnosis: String
    -notes: String
    -prescription: String
    -status: String
    -createdAt: DateTime
    -updatedAt: DateTime
    +save()
    +update()
    +flagForReview()
    +resolveFlag()
}

class SMSNotification {
    -notificationId: String
    -message: String
    -status: String
    -scheduledAt: DateTime
    -sentAt: DateTime
    -retryCount: int
    +send()
    +retry()
    +logFailure()
}

class AuditLog {
    -logId: String
    -action: String
    -affectedRecordId: String
    -timestamp: DateTime
    -status: String
    +createEntry()
    +archive()
    +escalate()
}

## ======================
## RELATIONSHIPS
## ======================

## ASSOCIATIONS
Patient "1" --> "0..*" Appointment : books
Doctor "1" --> "0..*" Appointment : attends

Doctor "1" --> "1..*" TimeSlot : owns
Appointment "1" --> "1" TimeSlot : uses

Appointment "1" --> "0..1" MedicalRecord : produces
Appointment "1" --> "1..*" SMSNotification : triggers

## COMPOSITION (strong ownership)
Patient "1" *-- "0..*" MedicalRecord : owns

## AGGREGATION (weak ownership)
User "1" o-- "0..*" AuditLog : generates

## ASSOCIATION (audit tracking)
MedicalRecord "1" --> "1..*" AuditLog : trackedBy

## ======================
## NOTES
## ======================

Note for TimeSlot
A slot cannot be double-booked.
It transitions: Available → Reserved → Booked.
end note

Note for Appointment
Cancellation and rescheduling must occur
at least 2 hours before appointment time.
end note

Note for MedicalRecord
Medical records cannot be deleted.
All changes must be logged in AuditLog.
end note

Note for SMSNotification
SMS reminders are sent 24 hours before
appointments and retried once if failed.
end note

## Explanation of Key Design Decisions
1. Inheritance (User → Patient, Doctor, Administrator)
A base User class was introduced to encapsulate shared attributes such as login credentials and contact details. This reduces duplication and ensures consistency across all system users.

2. Appointment as a Central Coordination Class
The Appointment class acts as the core interaction point between Patients and Doctors. It also connects to TimeSlot, MedicalRecord, and SMSNotification, making it the central workflow controller of the system.

3. Composition for Medical Records
A composition relationship exists between Patient and MedicalRecord because:
Medical records cannot exist without a patient
They are strongly owned and lifecycle-dependent

4. Aggregation for Audit Logs

AuditLog uses aggregation with User:
Logs are created by users
But they exist independently for compliance (POPIA requirement)

5. TimeSlot as a Constraint Mechanism
The TimeSlot entity ensures:
No double booking
Real-time availability control
Proper scheduling enforcement

This aligns directly with your business rules.

6. Multiplicity Enforcement

Multiplicity ensures real-world constraints:

One Patient → many Appointments
One Appointment → one TimeSlot
One Appointment → optional MedicalRecord
7. Notes for Business Rules

Important system constraints (like no deletion of records, retry rules, and booking restrictions) are included as notes to:

Improve diagram clarity
Help markers understand design decisions quickly