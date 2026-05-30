# PROTECTION.md — Branch Protection Rules for PulsePoint

**Project:** PulsePoint — Patient Appointment & Records System
**Assignment:** 13 — Implementing CI/CD with GitHub Actions

---

## 1. Overview

Branch protection rules are a set of enforced policies applied to the `main` branch of the PulsePoint repository. They ensure that no untested, unreviewed, or broken code can enter the main codebase. This document explains what rules have been applied and why each one matters for the quality and integrity of the PulsePoint system.

---

## 2. Branch Protection Rules Applied

The following rules have been configured under:
**GitHub → Settings → Branches → Branch protection rules → main**

| Rule | Setting | Status |
|---|---|---|
| Require pull request before merging | Enabled | ✅ |
| Required approvals | Minimum 1 approval | ✅ |
| Require status checks to pass | CI workflow must pass | ✅ |
| Require branches to be up to date | Enabled | ✅ |
| Do not allow bypassing the above settings | Enabled | ✅ |
| Restrict direct pushes to main | No direct pushes allowed | ✅ |

---

## 3. Why Each Rule Matters

### 3.1 Require Pull Request Before Merging

**Rule:** All changes to `main` must be submitted through a Pull Request — no direct commits allowed.

**Why it matters for PulsePoint:**
Direct pushes to `main` bypass all quality checks. In a healthcare system like PulsePoint, a single untested change to the appointment booking logic or medical records system could introduce bugs that affect real patient data. Requiring pull requests ensures every change is deliberate, visible, and reviewable before it reaches production.

---

### 3.2 Require at Least 1 Approval

**Rule:** At least one team member must review and approve a Pull Request before it can be merged.

**Why it matters for PulsePoint:**
Code review is one of the most effective ways to catch logic errors, security vulnerabilities, and business rule violations before they reach the main branch. For PulsePoint specifically, a second pair of eyes on changes to the authentication system, medical record access control, or audit logging is essential — these are areas where mistakes have compliance and legal consequences.

---

### 3.3 Require Status Checks to Pass (CI Workflow)

**Rule:** The GitHub Actions CI workflow must complete successfully — meaning all unit tests and integration tests must pass — before a PR can be merged.

**Why it matters for PulsePoint:**
PulsePoint has 91 unit and integration tests across the service layer, repository layer, creational patterns, and API controllers. Requiring all tests to pass before merging guarantees that:
- No appointment double-booking bug can be introduced
- The 2-hour cancellation rule remains enforced
- Repository CRUD operations continue to work correctly
- All 22 REST API endpoints return the correct HTTP status codes

If a developer accidentally breaks the `AppointmentService` logic, the CI pipeline will catch it immediately and block the merge — protecting the integrity of the system.

---

### 3.4 Require Branches to Be Up to Date

**Rule:** A branch must be up to date with `main` before it can be merged.

**Why it matters for PulsePoint:**
If two developers work on separate features simultaneously, their changes could conflict in subtle ways. Requiring branches to be up to date ensures the CI tests run against the latest combined state of all code — not just the individual feature branch in isolation. This prevents "works on my machine" integration failures.

---

### 3.5 Do Not Allow Bypassing Rules

**Rule:** Even repository administrators cannot bypass the protection rules.

**Why it matters for PulsePoint:**
Without this setting, administrators could override all the rules in an emergency and push directly to `main`. In a healthcare system, this creates a dangerous precedent. Enforcing rules for all users — including administrators — ensures the policies are consistent and auditable, which is important for compliance with healthcare data regulations like POPIA.

---

## 4. How the Branch Protection Works with CI/CD

The branch protection rules and the CI/CD pipeline work together as a quality gate:

```
Developer pushes code to feature branch
        ↓
GitHub Actions CI workflow triggers automatically
        ↓
All 91 tests run (unit + integration)
        ↓
    Tests pass?
   /           \
 YES             NO
  ↓               ↓
PR can be       PR is blocked
reviewed        — merge button
and approved    is disabled
  ↓
Code merges to main
  ↓
CD pipeline builds JAR artifact
```

This means broken code can never reach `main` — it is blocked at the CI level before any human review even takes place.

---

## 5. Benefits for PulsePoint

| Benefit | Description |
|---|---|
| **Code Quality** | Every change is tested and reviewed before reaching production |
| **Security** | Authentication and access control changes cannot bypass review |
| **Compliance** | Audit trail of all changes via PR history satisfies POPIA requirements |
| **Collaboration** | Clear process for contributing code in future team expansion |
| **Reliability** | Broken builds never reach main — system stays stable |