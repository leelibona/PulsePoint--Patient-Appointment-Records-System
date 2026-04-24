# Class Diagram – PulsePoint Patient Appointment & Records System  

---

## 1. Mermaid.js Class Diagram  

```mermaid
classDiagram

%% ======================
%% BASE CLASS (INHERITANCE)
%% ======================

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

%% ======================
%% CORE CLASSES
%% ======================

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

%% ======================
%% RELATIONSHIPS
%% ======================

%% ASSOCIATIONS
Patient "1" --> "0..*" Appointment : books
Doctor "1" --> "0..*" Appointment : attends

Doctor "1" --> "1..*" TimeSlot : owns
Appointment "1" --> "1" TimeSlot : uses

Appointment "1" --> "0..1" MedicalRecord : produces
Appointment "1" --> "1..*" SMSNotification : triggers

%% COMPOSITION (strong ownership)
Patient "1" *-- "0..*" MedicalRecord : owns

%% AGGREGATION (weak ownership)
User "1" o-- "0..*" AuditLog : generates

%% ASSOCIATION (audit tracking)
MedicalRecord "1" --> "1..*" AuditLog : trackedBy

%% ======================
%% NOTES
%% ======================

note for TimeSlot
A slot cannot be double-booked.
It transitions: Available → Reserved → Booked.
end note

note for Appointment
Cancellation and rescheduling must occur
at least 2 hours before appointment time.
end note

note for MedicalRecord
Medical records cannot be deleted.
All changes must be logged in AuditLog.
end note

note for SMSNotification
SMS reminders are sent 24 hours before
appointments and retried once if failed.
end note

