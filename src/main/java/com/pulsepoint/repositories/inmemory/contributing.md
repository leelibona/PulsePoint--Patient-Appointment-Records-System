# CONTRIBUTING.md — Contributing to PulsePoint

Thank you for your interest in contributing to **PulsePoint — Patient Appointment & Records System**! This guide will help you get set up, understand the coding standards, and submit your first contribution.

---

## 1. Prerequisites

Before you start, make sure you have the following installed:

| Tool | Version | Download |
|---|---|---|
| **Java** | 17 or higher | https://adoptium.net |
| **Maven** | 3.8 or higher | https://maven.apache.org |
| **Git** | Latest | https://git-scm.com |
| **VS Code or IntelliJ IDEA** | Latest | Your preference |

---

## 2. Getting Started — Local Setup

### Step 1 — Fork the repository
Click the **Fork** button at the top right of the repository page. This creates your own copy of PulsePoint under your GitHub account.

### Step 2 — Clone your fork
```bash
git clone https://github.com/YOUR_USERNAME/PulsePoint--Patient-Appointment-Records-System.git
cd PulsePoint--Patient-Appointment-Records-System
```

### Step 3 — Add the upstream remote
```bash
git remote add upstream https://github.com/leelibona/PulsePoint--Patient-Appointment-Records-System.git
```

### Step 4 — Install dependencies
```bash
mvn clean install
```

### Step 5 — Run all tests to verify setup
```bash
mvn clean test
```
All tests should pass before you start making changes.

### Step 6 — Run the application locally
```bash
mvn spring-boot:run
```
- API available at: `http://localhost:8080/api`
- Swagger UI at: `http://localhost:8080/swagger-ui.html`

---

## 3. How to Pick an Issue

1. Go to the **Issues** tab on the repository
2. Filter by label:
   - 🟢 `good-first-issue` — simple tasks perfect for new contributors
   - 🔵 `feature-request` — new features being planned
   - 🔴 `bug` — known bugs that need fixing
3. Leave a comment on the issue saying **"I'd like to work on this"** so others know it's taken
4. Wait for a maintainer to assign it to you

---

## 4. Coding Standards

### 4.1 Java Style
- Use **4 spaces** for indentation — no tabs
- Class names in **PascalCase** — e.g. `PatientService`
- Method names in **camelCase** — e.g. `bookAppointment()`
- Constants in **UPPER_SNAKE_CASE** — e.g. `MAX_RETRY_COUNT`
- Every public class and method must have a **Javadoc comment**

### 4.2 Package Structure
All new code must go in the correct package:
```
com.pulsepoint.models/          ← domain classes
com.pulsepoint.services/        ← business logic
com.pulsepoint.repositories/    ← data access interfaces
com.pulsepoint.api.controllers/ ← REST endpoints
```

### 4.3 Testing Requirements
- Every new method must have at least **one unit test**
- Tests go in the `/tests` directory mirroring the source structure
- Use **JUnit 5** for all tests
- Test method names must be descriptive:
```java
// Good
@Test
public void testBookAppointment_ThrowsException_WhenSlotAlreadyBooked()

// Bad
@Test
public void test1()
```

### 4.4 REST API Standards
- Use correct HTTP methods: `GET` for reads, `POST` for creates, `PUT` for updates, `DELETE` for deletes
- Return correct HTTP status codes: `200`, `201`, `204`, `400`, `404`, `409`
- All endpoints must be documented in `docs/openapi.yaml`

---

## 5. Submitting a Pull Request

### Step 1 — Create a new branch
Always branch from `main`:
```bash
git checkout main
git pull upstream main
git checkout -b feature/your-feature-name
```

Use descriptive branch names:
- `feature/add-prescription-endpoint`
- `fix/appointment-double-booking`
- `docs/update-api-documentation`

### Step 2 — Make your changes
Write your code following the coding standards above.

### Step 3 — Run tests before committing
```bash
mvn clean test
```
All tests must pass before you commit.

### Step 4 — Commit your changes
Use clear, descriptive commit messages:
```bash
git add .
git commit -m "Add prescription endpoint to MedicalRecordController"
```

Link to the issue in your commit message where possible:
```bash
git commit -m "Close #12: Add prescription endpoint to MedicalRecordController"
```

### Step 5 — Push your branch
```bash
git push origin feature/your-feature-name
```

### Step 6 — Open a Pull Request
1. Go to your fork on GitHub
2. Click **"Compare & pull request"**
3. Fill in the PR template:
   - **Title**: Clear and descriptive
   - **Description**: What does this PR do? Which issue does it close?
   - **Testing**: What tests did you add or run?
4. Click **"Create pull request"**

### Step 7 — Wait for review
- The CI pipeline will run automatically — make sure all checks pass
- A maintainer will review your PR and may request changes
- Once approved, your PR will be merged into `main`

---

## 6. PR Checklist

Before submitting your PR, make sure:

- [ ] Code follows the coding standards above
- [ ] All existing tests still pass (`mvn clean test`)
- [ ] New tests added for new functionality
- [ ] Javadoc comments added for new public methods
- [ ] API changes documented in `docs/openapi.yaml`
- [ ] CHANGELOG.md updated with a description of your changes
- [ ] Branch is up to date with `main`

---

## 7. Code of Conduct

- Be respectful and constructive in all comments and reviews
- Focus feedback on the code, not the person
- Welcome contributors of all experience levels
- If you see a problem, open an issue rather than silently fixing it

---

## 8. Need Help?

If you are stuck or have questions:
- Open a **GitHub Discussion** in the Discussions tab
- Comment on the relevant issue
- Tag `@leelibona` in your PR for a review

We appreciate every contribution, no matter how small!