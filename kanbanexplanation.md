# KANBAN-EXPLANATION.md — Kanban Board Definition and Purpose
**Assignment:** 7 — GitHub Project Templates and Kanban Board Implementation

---

## 1. What is a Kanban Board?

A Kanban board is a visual project management tool that represents the flow of work across different stages of completion. The word "Kanban" comes from Japanese and translates to "visual signal" or "card" — reflecting the core idea that every task is represented as a card that moves across the board as work progresses.

At its simplest, a Kanban board consists of columns that represent stages in a workflow  such as **To Do**, **In Progress**, and **Done** and cards that represent individual tasks or work items. As a developer picks up a task, its card moves from one column to the next, giving the entire team an instant, real-time picture of what is being worked on, what is waiting, and what has been completed.

Unlike traditional project management approaches that plan everything upfront, Kanban is designed to be flexible and responsive. Work items are pulled into the next stage only when there is capacity to handle them, rather than being pushed onto developers regardless of their current workload.

---

## 2. How the PulsePoint Kanban Board Visualises Workflow

The PulsePoint Kanban board on GitHub Projects is organised into five columns, each representing a distinct stage in the development workflow:

| Column | Purpose |
|---|---|
| **To Do** | All Sprint 1 tasks that have been defined but not yet started. This column represents the full sprint backlog at the beginning of the sprint. |
| **In Progress** | Tasks that are currently being actively developed. A task moves here when a developer begins working on it. |
| **Testing** | Tasks where development is complete but the feature or API endpoint is being tested and validated against the acceptance criteria defined in Assignment 5. |
| **Blocked** | Tasks that cannot proceed because of an unresolved dependency or issue. For example, the appointment booking API cannot be built until the doctor search API is complete. |
| **Done** | Tasks that have been fully completed, tested, and verified against their acceptance criteria. These tasks satisfy the Definition of Done defined in Assignment 6. |

This column structure makes the entire Sprint 1 workflow visible at a glance. Anyone viewing the board can immediately see how many tasks are waiting, how many are in active development, how many are blocked, and how many have been completed — without needing to read through any documents or ask anyone for a status update.

---

## 3. How the Board Limits Work-In-Progress (WIP)

One of the most important principles of Kanban is limiting the amount of work that can be in any stage at the same time. This is known as a **Work-In-Progress (WIP) limit**. WIP limits prevent developers from starting too many tasks simultaneously, which leads to context switching, reduced focus, and tasks that are started but never finished.

For the PulsePoint Kanban board, the following WIP limits have been applied:

| Column | WIP Limit | Reason |
|---|---|---|
| **To Do** | No limit | All planned tasks should be visible from the start |
| **In Progress** | Maximum 3 tasks | Ensures focused development without overwhelming the solo developer |
| **Testing** | Maximum 2 tasks | Prevents a backlog of untested features building up |
| **Blocked** | No limit | Blocked tasks need to remain visible regardless of quantity |
| **Done** | No limit | Completed tasks should always be recorded |

By limiting In Progress to 3 tasks, the PulsePoint board ensures that development remains focused. If the In Progress column reaches its limit, no new tasks can be started until one is moved to Testing or Done — encouraging tasks to be completed before new ones are begun.

---

## 4. How the Board Supports Agile Principles

The PulsePoint Kanban board supports the following core Agile principles:

### 4.1 Continuous Delivery
The board encourages a steady flow of completed work by making bottlenecks immediately visible. If tasks pile up in the Testing column, it signals that testing needs attention before more development begins — preventing an end-of-sprint rush where everything is developed but nothing is verified.

### 4.2 Transparency and Visibility
Every task from the Sprint 1 plan in Assignment 6 is represented as a GitHub Issue on the board with a label, an assigned owner, and a current status. This gives complete visibility into the state of the sprint at any point in time — a core Agile value.

### 4.3 Adaptability
If a task becomes blocked or requirements change mid-sprint, the Kanban board makes it easy to respond. Blocked tasks are immediately visible in the Blocked column, and new tasks can be added to To Do without disrupting the rest of the board.

### 4.4 Incremental Progress
Rather than waiting until all features are complete before delivering value, the board encourages moving individual tasks through to Done as quickly as possible. Each completed task represents a small, verifiable increment of working functionality — aligned with the Agile principle of delivering working software frequently.

### 4.5 Focus on Flow
Kanban measures progress by the flow of tasks from left to right across the board. A healthy board shows a steady movement of cards through each column. A board where cards are stuck in In Progress or Blocked for extended periods signals a problem that needs to be addressed — making issues visible before they become critical.

---

## 5. PulsePoint Board Customisation Choices

Two custom columns were added to the default Kanban template to better suit the PulsePoint development workflow:

- **Testing** was added because the Definition of Done requires all API endpoints and UI components to be tested before a story is considered complete. Without this column, completed development work and verified work would both sit in Done, making it impossible to distinguish between tasks that are finished and tasks that are finished and tested.

- **Blocked** was added because PulsePoint has several tasks with dependencies — for example, the booking API depends on the doctor search API, and the SMS integration depends on the booking API. Having a dedicated Blocked column makes these dependency issues immediately visible rather than hiding them inside the In Progress column where they would inflate the WIP count unfairly.