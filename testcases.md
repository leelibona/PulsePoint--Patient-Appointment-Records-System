# TEST-CASES.md — PulsePoint Patient Appointment & Records System

---

## 1. Overview

This document defines test cases to validate the functional and non-functional requirements of the PulsePoint system. Each test case is directly traceable to a requirement defined in Assignment 4. The document includes 8 functional test cases and 2 non-functional test cases covering performance and security.

---

## 2. Functional Test Cases

| Test Case ID | Requirement | Description | Steps | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|---|
| TC001 | Functional Requirement 01 — User Registration | Verify that a new patient can successfully register an account | 1. Navigate to the registration page. 2. Enter full name, email, phone number, date of birth, and gender. 3. Enter and confirm a password. 4. Click Register. | Account is created successfully. Patient is redirected to the login page with a success message. | — | — |
| TC002 | Functional Requirement 01 — User Registration | Verify that the system rejects registration with a duplicate email address | 1. Navigate to the registration page. 2. Enter an email address already registered in the system. 3. Complete all other fields. 4. Click Register. | System displays the error "An account with this email already exists." Registration is not completed. | — | — |
| TC003 | Functional Requirement 02 — User Authentication and Role-Based Access | Verify that a patient can log in and is redirected to the Patient Portal | 1. Navigate to the login page. 2. Enter a valid patient email and password. 3. Click Login. | Patient is authenticated and redirected to the Patient Portal dashboard. JWT token is issued. | — | — |
| TC004 | Functional Requirement 02 — User Authentication and Role-Based Access | Verify that a user with invalid credentials cannot log in | 1. Navigate to the login page. 2. Enter a valid email but incorrect password. 3. Click Login. | System displays "Incorrect email or password." User is not granted access. JWT token is not issued. | — | — |
| TC005 | Functional Requirement 03 — Appointment Booking | Verify that a patient can successfully book an appointment | 1. Log in as a patient. 2. Navigate to Book Appointment. 3. Search for a doctor by name. 4. Select an available date and time slot. 5. Click Confirm Booking. | Appointment is created in the database. Confirmation screen is displayed. Selected time slot is marked unavailable. SMS confirmation is triggered via Twilio. | — | — |
| TC006 | Functional Requirement 04 — Appointment Rescheduling and Cancellation | Verify that a patient can reschedule an existing appointment | 1. Log in as a patient. 2. Navigate to My Appointments. 3. Select an upcoming appointment more than 2 hours away. 4. Click Reschedule. 5. Select a new available time slot. 6. Click Confirm Reschedule. | Appointment is updated with the new date and time. Old slot is returned to the available pool. SMS notification confirming the reschedule is sent to the patient. | — | — |
| TC007 | Functional Requirement 06 — Electronic Medical Records Creation | Verify that a doctor can create a medical record after a consultation | 1. Log in as a doctor. 2. Navigate to the appointment schedule. 3. Select a completed appointment. 4. Click Create Medical Record. 5. Enter diagnosis, consultation notes, and prescription. 6. Click Save Record. | Medical record is saved and linked to the patient and appointment. Appointment status is updated to Completed. Success message is displayed. | — | — |
| TC008 | Functional Requirement 09 — Administrator User Management | Verify that an administrator can create a new doctor account | 1. Log in as an administrator. 2. Navigate to User Management. 3. Click Add New Doctor. 4. Enter doctor name, email, specialisation, and phone number. 5. Click Save. | New doctor account is created and appears in the system. Doctor can log in with the provided credentials. Confirmation message is displayed to the admin. | — | — |
| TC009 | Functional Requirement 10 — Administrator Analytics Dashboard | Verify that the analytics dashboard displays correct appointment data | 1. Log in as an administrator. 2. Navigate to the Analytics Dashboard. 3. Observe the displayed metrics. 4. Apply a filter by date range. 5. Observe the updated metrics. | Dashboard displays total appointments, completion rate, and no-show rate. Metrics update correctly when the date filter is applied. All data loads within 3 seconds. | — | — |
| TC010 | Functional Requirement 12 — Audit Trail Logging | Verify that accessing a medical record generates an audit log entry | 1. Log in as a patient. 2. Navigate to Medical Records. 3. Open a specific medical record. 4. Log in as an administrator. 5. Navigate to Audit Logs. 6. Search for the log entry from step 3. | An audit log entry exists showing the patient user ID, the record accessed, the action performed, and the timestamp of access. | — | — |

---

## 3. Non-Functional Test Cases

### Non-Functional Test Case 1 — Performance

| Field | Detail |
|---|---|
| **Test Case ID** | TC-NFR-01 |
| **Requirement** | Non-Functional Requirement — Performance 01: All key pages shall load within 2 seconds under normal network conditions. |
| **Description** | Simulate 500 concurrent users accessing the appointment booking page simultaneously and verify that the page loads within the required 2-second threshold. |
| **Test Type** | Load Test |
| **Tool** | Apache JMeter or k6 |

**Steps:**
1. Configure the load testing tool to simulate 500 concurrent users
2. Set all users to simultaneously navigate to the appointment booking page
3. Record the response time for each request
4. Calculate the average, minimum, and maximum response times
5. Identify any requests that exceeded the 2-second threshold

**Expected Result:**
- Average page load time is under 2 seconds for all 500 concurrent users
- No more than 5% of requests exceed the 2-second threshold
- The system remains stable with no crashes or timeouts during the test

**Actual Result:** —

**Status:** —

---

### Non-Functional Test Case 2 — Security

| Field | Detail |
|---|---|
| **Test Case ID** | TC-NFR-02 |
| **Requirement** | Non-Functional Requirement — Security 03: All patient medical records shall be accessible only to the patient themselves and their assigned doctor, enforced at the API level through role-based access control. |
| **Description** | Verify that an authenticated patient cannot access another patient's medical records by manipulating the URL or API request, and that the system correctly enforces role-based access control at the API level. |
| **Test Type** | Security / Penetration Test |
| **Tool** | Postman or OWASP ZAP |

**Steps:**
1. Log in as Patient A and retrieve their JWT token
2. Identify the API endpoint for medical records (e.g. GET /records/:patientId)
3. Using Patient A's JWT token, send a GET request to /records/:patientBId where patientBId is the ID of a different patient
4. Observe the system response
5. Repeat the test using a doctor's JWT token for a patient they have no appointment history with
6. Observe the system response

**Expected Result:**
- In step 3, the system returns a 403 Forbidden response and does not return any of Patient B's records
- In step 5, the system returns a 403 Forbidden response for the unrelated patient's records
- All unauthorised access attempts are logged in the audit trail with the user ID and timestamp
- No patient data is exposed under any unauthorised request

**Actual Result:** —

**Status:** —

---

## 4. Test Summary Table

| Test Case ID | Type | Requirement Tested | Expected Outcome |
|---|---|---|---|
| TC001 | Functional | User Registration — success path | Account created, redirected to login |
| TC002 | Functional | User Registration — duplicate email | Error message displayed, registration blocked |
| TC003 | Functional | Authentication — valid credentials | Patient redirected to Patient Portal |
| TC004 | Functional | Authentication — invalid credentials | Access denied, error message displayed |
| TC005 | Functional | Appointment Booking | Appointment created, SMS sent |
| TC006 | Functional | Appointment Rescheduling | Appointment updated, SMS sent |
| TC007 | Functional | Electronic Medical Records Creation | Record saved, appointment marked complete |
| TC008 | Functional | Administrator User Management | Doctor account created successfully |
| TC009 | Functional | Administrator Analytics Dashboard | Correct metrics displayed within 3 seconds |
| TC010 | Functional | Audit Trail Logging | Access event logged with user ID and timestamp |
| TC-NFR-01 | Performance | Page load under 2 seconds for 500 users | Average response time under 2 seconds |
| TC-NFR-02 | Security | Role-based medical record access control | 403 Forbidden returned for unauthorised access |