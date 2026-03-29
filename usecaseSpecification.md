# USE-CASE-SPECIFICATIONS.md — PulsePoint Patient Appointment & Records System

---

## 1. Overview

This document provides detailed specifications for 8 critical use cases identified in the PulsePoint use case diagram. Each specification includes a full description, preconditions, postconditions, basic flow, and alternative flows to cover all possible scenarios including error paths.

---

## Use Case 1 — Register Account

| Field | Detail |
|---|---|
| **Use Case Name** | Register Account |
| **Actor(s)** | Patient |
| **Description** | Allows a new patient to create a personal account on the PulsePoint platform by providing their personal details. Once registered, the patient can log in and access all patient-facing features. |
| **Preconditions** | The patient does not already have an account. The patient has access to a web browser and a valid email address. |
| **Postconditions** | A new patient account is created and stored in the database. The patient is redirected to the login page with a success message. |

**Basic Flow:**
1. Patient navigates to the PulsePoint registration page
2. Patient enters their full name, email address, phone number, date of birth, and gender
3. Patient creates and confirms a password
4. Patient clicks the Register button
5. System validates all fields are correctly filled in
6. System checks that the email address is not already registered
7. System hashes the password and saves the new account to the database
8. System displays a registration success message and redirects the patient to the login page

**Alternative Flows:**

- **Alternative Flow 1 — Duplicate email address:** At step 6, if the email is already registered, the system displays the error "An account with this email already exists" and prompts the patient to log in or use a different email.
- **Alternative Flow 2 — Missing required fields:** At step 5, if any required field is empty, the system highlights the missing fields in red and displays "Please complete all required fields."
- **Alternative Flow 3 — Password mismatch:** At step 5, if the password and confirmation password do not match, the system displays "Passwords do not match. Please try again."
- **Alternative Flow 4 — Invalid email format:** At step 5, if the email format is invalid, the system displays "Please enter a valid email address."

---

## Use Case 2 — Login to System

| Field | Detail |
|---|---|
| **Use Case Name** | Login to System |
| **Actor(s)** | Patient, Doctor, Administrator, Receptionist |
| **Description** | Allows any registered user to authenticate themselves using their email and password. Upon successful login, the system redirects the user to their role-specific dashboard. |
| **Preconditions** | The user has a registered account. The user knows their email address and password. |
| **Postconditions** | The user is authenticated and a JWT token is issued. The user is redirected to their role-specific dashboard — Patient Portal, Doctor Dashboard, or Admin Control Panel. |

**Basic Flow:**
1. User navigates to the PulsePoint login page
2. User enters their email address and password
3. User clicks the Login button
4. System validates that the email exists in the database
5. System verifies the entered password against the stored hashed password
6. System generates a JWT token for the session
7. System identifies the user's role (Patient, Doctor, or Administrator)
8. System redirects the user to their role-specific dashboard

**Alternative Flows:**

- **Alternative Flow 1 — Incorrect password:** At step 5, if the password does not match, the system displays "Incorrect email or password" without specifying which field is wrong for security reasons.
- **Alternative Flow 2 — Unregistered email:** At step 4, if the email is not found, the system displays "Incorrect email or password."
- **Alternative Flow 3 — Deactivated account:** At step 4, if the account has been deactivated by an administrator, the system displays "Your account has been deactivated. Please contact the clinic."
- **Alternative Flow 4 — Session already active:** If the user is already logged in and navigates to the login page, the system redirects them directly to their dashboard.

---

## Use Case 3 — Book Appointment

| Field | Detail |
|---|---|
| **Use Case Name** | Book Appointment |
| **Actor(s)** | Patient, Receptionist |
| **Description** | Allows a patient or receptionist to book a medical appointment by selecting a doctor, choosing a date, and selecting an available time slot. An SMS confirmation is sent to the patient upon successful booking. |
| **Preconditions** | The user is logged in. At least one doctor is registered and has available time slots. |
| **Postconditions** | A new appointment record is created in the database. The selected time slot is marked as unavailable. The patient receives an SMS confirmation via Twilio. |

**Basic Flow:**
1. Patient navigates to the Book Appointment page
2. Patient searches for a doctor by name or specialisation
3. System displays a list of matching doctors with their profiles
4. Patient selects a doctor and views their available time slots
5. Patient selects a preferred date and time slot
6. Patient clicks Confirm Booking
7. System creates the appointment record in the database
8. System marks the selected time slot as unavailable in real time
9. System triggers the Twilio API to send an SMS confirmation to the patient
10. System displays a booking confirmation screen with appointment details

**Alternative Flows:**

- **Alternative Flow 1 — No available slots:** At step 4, if the selected doctor has no available slots, the system displays "No available appointments for this doctor. Please try another date or doctor."
- **Alternative Flow 2 — Slot taken during booking:** At step 7, if the selected slot was taken by another user between steps 5 and 7, the system displays "This slot is no longer available. Please select another time." and returns the patient to the slot selection screen.
- **Alternative Flow 3 — SMS delivery failure:** At step 9, if the Twilio API fails to send the SMS, the system logs the failure and retries once after 10 minutes. The booking is still confirmed regardless of SMS status.
- **Alternative Flow 4 — Doctor not found:** At step 3, if no doctors match the search query, the system displays "No doctors found matching your search. Please try different keywords."

---

## Use Case 4 — Reschedule Appointment

| Field | Detail |
|---|---|
| **Use Case Name** | Reschedule Appointment |
| **Actor(s)** | Patient, Receptionist |
| **Description** | Allows a patient or receptionist to change the date or time of an existing confirmed appointment to a different available slot with the same doctor. |
| **Preconditions** | The user is logged in. The patient has at least one confirmed upcoming appointment. The appointment is more than 2 hours away from the current time. |
| **Postconditions** | The appointment record is updated with the new date and time. The old slot is returned to the available pool. The patient receives an SMS notification confirming the reschedule. |

**Basic Flow:**
1. Patient navigates to My Appointments
2. Patient selects the appointment they wish to reschedule
3. Patient clicks the Reschedule button
4. System displays available time slots for the same doctor
5. Patient selects a new preferred date and time slot
6. Patient clicks Confirm Reschedule
7. System updates the appointment record with the new date and time
8. System releases the old time slot back to the available pool
9. System triggers the Twilio API to send an SMS notification confirming the reschedule
10. System displays a reschedule confirmation screen

**Alternative Flows:**

- **Alternative Flow 1 — Appointment within 2 hours:** At step 3, if the appointment is less than 2 hours away, the Reschedule button is disabled and the system displays "Appointments cannot be rescheduled within 2 hours of the scheduled time."
- **Alternative Flow 2 — No alternative slots available:** At step 4, if the doctor has no other available slots, the system displays "No alternative slots available for this doctor. You may cancel the appointment instead."
- **Alternative Flow 3 — New slot taken during reschedule:** At step 7, if the chosen new slot was taken by another user, the system displays "This slot is no longer available" and returns the patient to slot selection.

---

## Use Case 5 — Cancel Appointment

| Field | Detail |
|---|---|
| **Use Case Name** | Cancel Appointment |
| **Actor(s)** | Patient, Receptionist |
| **Description** | Allows a patient or receptionist to cancel an existing confirmed appointment. The cancelled slot is returned to the available pool and the patient is notified via SMS. |
| **Preconditions** | The user is logged in. The patient has at least one confirmed upcoming appointment. The appointment is more than 2 hours away from the current time. |
| **Postconditions** | The appointment status is updated to Cancelled. The time slot is returned to the available pool. The patient receives an SMS cancellation notification. |

**Basic Flow:**
1. Patient navigates to My Appointments
2. Patient selects the appointment they wish to cancel
3. Patient clicks the Cancel Appointment button
4. System displays a confirmation prompt: "Are you sure you want to cancel this appointment?"
5. Patient confirms the cancellation
6. System updates the appointment status to Cancelled in the database
7. System releases the time slot back to the available pool
8. System triggers the Twilio API to send an SMS cancellation notification to the patient
9. System displays a cancellation confirmation message

**Alternative Flows:**

- **Alternative Flow 1 — Appointment within 2 hours:** At step 3, if the appointment is less than 2 hours away, the Cancel button is disabled and the system displays "Appointments cannot be cancelled within 2 hours of the scheduled time. Please call the clinic directly."
- **Alternative Flow 2 — Patient declines confirmation:** At step 5, if the patient clicks No on the confirmation prompt, the system closes the prompt and no changes are made.
- **Alternative Flow 3 — SMS delivery failure:** At step 8, if the SMS fails, the system logs the failure and retries once. The cancellation is still processed regardless.

---

## Use Case 6 — Create Medical Record

| Field | Detail |
|---|---|
| **Use Case Name** | Create Medical Record |
| **Actor(s)** | Doctor |
| **Description** | Allows a doctor to create an electronic medical record for a patient following a consultation. The record includes the diagnosis, consultation notes, and any prescription details. |
| **Preconditions** | The doctor is logged in. The appointment associated with the record has a status of In Progress or Completed. |
| **Postconditions** | A new medical record is saved in the database and linked to both the patient and the appointment. The appointment status is updated to Completed. |

**Basic Flow:**
1. Doctor navigates to their appointment schedule
2. Doctor selects the appointment for which they want to create a record
3. Doctor clicks Create Medical Record
4. System displays the medical record form pre-filled with patient name and appointment date
5. Doctor enters the diagnosis, consultation notes, and prescription details
6. Doctor clicks Save Record
7. System validates that all required fields are completed
8. System saves the medical record to the database linked to the patient and appointment
9. System updates the appointment status to Completed
10. System displays a success message confirming the record has been saved

**Alternative Flows:**

- **Alternative Flow 1 — Missing required fields:** At step 7, if any required field (diagnosis or consultation notes) is empty, the system highlights the empty fields and displays "Please complete all required fields before saving."
- **Alternative Flow 2 — Record already exists:** At step 3, if a record already exists for the selected appointment, the system displays the existing record in edit mode rather than a blank form, with a notice: "A record already exists for this appointment. You are now editing it."
- **Alternative Flow 3 — Session timeout during entry:** If the doctor's session expires while filling in the form, the system saves a draft of the entered data and prompts the doctor to log in again to continue.

---

## Use Case 7 — View Medical Records

| Field | Detail |
|---|---|
| **Use Case Name** | View Medical Records |
| **Actor(s)** | Patient, Doctor |
| **Description** | Allows a patient to view their own complete medical history or a doctor to view the medical history of a patient they are treating. Records are displayed in reverse chronological order. |
| **Preconditions** | The user is logged in. For patients, at least one medical record exists linked to their account. For doctors, the patient must be associated with an upcoming or past appointment with that doctor. |
| **Postconditions** | The requested medical records are displayed on screen. The access event is logged in the audit trail. |

**Basic Flow:**
1. User navigates to the Medical Records section
2. System retrieves all medical records associated with the user's account
3. System displays records in reverse chronological order showing date, doctor name, diagnosis, and prescription
4. User selects a specific record to view full details
5. System displays the full record including consultation notes and prescription
6. System logs the access event in the audit trail with the user ID and timestamp

**Alternative Flows:**

- **Alternative Flow 1 — No records found:** At step 2, if no medical records exist for the patient, the system displays "No medical records found. Records will appear here after your first consultation."
- **Alternative Flow 2 — Unauthorised access attempt:** If a patient attempts to access another patient's records via a direct URL, the system returns a 403 Forbidden error and logs the attempt in the audit trail.
- **Alternative Flow 3 — Doctor accessing unrelated patient:** If a doctor attempts to access records of a patient with whom they have no appointment history, the system denies access and displays "You are not authorised to view this patient's records."

---

## Use Case 8 — View Analytics Dashboard

| Field | Detail |
|---|---|
| **Use Case Name** | View Analytics Dashboard |
| **Actor(s)** | Administrator |
| **Description** | Allows the administrator to view a real-time analytics dashboard displaying key clinic metrics including total appointments, appointment status breakdown, no-show rates, and doctor performance data. |
| **Preconditions** | The administrator is logged in. The system has at least one appointment record in the database. |
| **Postconditions** | The analytics dashboard is displayed with up-to-date metrics. The administrator can optionally export a report. |

**Basic Flow:**
1. Administrator navigates to the Admin Control Panel
2. Administrator clicks on the Analytics Dashboard section
3. System queries the database for all appointment and user data
4. System calculates metrics including total appointments, completion rate, no-show rate, and revenue per doctor
5. System displays the metrics on the dashboard with visual charts and summary cards
6. Dashboard data automatically refreshes every 60 seconds
7. Administrator optionally applies filters by date range, doctor, or appointment status
8. System updates the displayed metrics based on the selected filters
9. Administrator optionally clicks Export Report
10. System generates and downloads a summary report in PDF or CSV format

**Alternative Flows:**

- **Alternative Flow 1 — No data available:** At step 3, if the database contains no appointment records, the system displays "No data available yet. Analytics will appear once appointments are recorded."
- **Alternative Flow 2 — Export failure:** At step 10, if the report generation fails, the system displays "Report export failed. Please try again." and logs the error.
- **Alternative Flow 3 — Filter returns no results:** At step 8, if the applied filters return no matching records, the system displays "No results found for the selected filters. Try adjusting your search criteria."