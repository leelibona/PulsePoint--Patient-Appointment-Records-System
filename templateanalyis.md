# TEMPLATE-ANALYSIS.md — GitHub Project Template Analysis and Selection

**Assignment:** 7 — GitHub Project Templates and Kanban Board Implementation

---

## 1. Introduction

GitHub Projects offers several pre-built templates designed to help development teams manage their workflows visually. As part of the Agile planning process for PulsePoint, this document evaluates four GitHub project templates, compares their features, and justifies the selection of the most suitable template for managing the PulsePoint sprint backlog and Kanban workflow.

---

## 2. GitHub Project Templates Comparison

| Feature | Team Planning | Kanban | Feature Release | Bug Tracker |
|---|---|---|---|---|
| **Default Columns** | Backlog, Ready, In Progress, In Review, Done | To Do, In Progress, Done | Backlog, Ready, In Progress, In Review, Done, Released | New, Needs Triage, High Priority, Low Priority, Closed |
| **Custom Columns** | Manually added | Manually added | Manually added | Manually added |
| **Automation** | Auto-closes items when issues are closed. Supports iteration tracking. | Auto-moves items based on WIP limits. Supports status updates. | Auto-moves to Released when a release is published. Items auto-close on issue close. | Auto-moves to Closed when a bug is closed. Triage workflow is manual. |
| **Issue Linking** | Supported — designed for linking issues to team members and milestones | Supported — issues linked directly to board cards | Supported — issues linked to release milestones | Supported — bugs logged as issues with priority labels |
| **Labels Support** | Yes — supports priority and type labels | Yes — supports WIP labels and status labels | Yes — supports feature and release labels | Yes — designed around priority labels (High, Low, Triage) |
| **Milestone Support** | Yes — built around sprint milestones and iterations | Yes — milestones can be linked to cards | Yes — built around release milestones | Yes — milestones used for bug fix versions |
| **WIP Limits** | Not built in — managed manually | Built in — core feature of the template | Not built in — managed manually | Not built in — managed manually |
| **Best For** | Teams planning work across multiple sprints with multiple members | Solo or small teams tracking task progress with WIP limits | Teams managing a product release pipeline from backlog to deployment | Teams tracking and triaging bug reports and technical debt |
| **Agile Suitability** | High — supports sprint planning and backlog management but designed for larger teams | High — WIP limits and visual workflow align perfectly with Agile and Scrum principles | Medium — better suited for release management than active sprint development | Low — focused on bug management, not feature development |
| **Complexity** | Moderate — more columns than needed for a solo sprint | Simple — clean and focused on task flow | Moderate — release-focused columns add unnecessary complexity | Moderate — triage-focused, not ideal for feature tracking |
| **Recommended For PulsePoint** | Partially | ✅ Yes | No | No |

---

## 3. Justification for Chosen Template — Kanban

The **Kanban** template was selected as the most suitable template for the PulsePoint project for the following reasons:

### 3.1 Built-in WIP Limits Reduce Overload
The Kanban template is the only template that has **Work-In-Progress (WIP) limits built in** as a core feature. Since PulsePoint is a solo project, it is easy to take on too many tasks simultaneously and lose focus. WIP limits enforce discipline by restricting how many tasks can be In Progress at any one time, ensuring each task gets proper attention before a new one is started.

### 3.2 Clean and Focused Workflow
The default columns — **To Do, In Progress, Done** — map directly to the Sprint 1 task statuses defined in Assignment 6. All 15 tasks from the Sprint 1 backlog can be added immediately as GitHub Issues and tracked across these columns without any restructuring.

### 3.3 Support for Custom Columns
The Kanban template allows additional columns to be added easily. For PulsePoint, two custom columns were added — **Testing** and **Blocked** — to reflect the full development lifecycle and handle tasks that are waiting on dependencies.

### 3.4 Issue and Label Integration
The template supports GitHub Issues with labels such as `feature`, `bug`, `enhancement`, and `in-progress`. This allows each Sprint 1 task to be labelled by type, making it easy to filter and prioritise work on the board.

### 3.5 Why Not the Others
- **Team Planning** was rejected because it is designed for larger teams with multiple members and has more columns than necessary for a solo sprint — adding unnecessary complexity.
- **Feature Release** was rejected because it focuses on release pipeline management rather than active sprint development. The Released column is not relevant at this early stage of the project.
- **Bug Tracker** was rejected because PulsePoint is currently in the feature development phase, not bug management. This template is better suited for a later maintenance phase once the system is built and deployed.

---

## 4. Customisation Plan for PulsePoint Kanban Board

Based on the selected Automated Kanban template, the following customisations were made to better reflect the PulsePoint development workflow:

| Column | Purpose | WIP Limit |
|---|---|---|
| **To Do** | All Sprint 1 tasks not yet started | No limit |
| **In Progress** | Tasks currently being developed | Maximum 3 tasks |
| **Testing** | Tasks completed and awaiting testing or validation | Maximum 2 tasks |
| **Blocked** | Tasks that cannot proceed due to a dependency or issue | No limit |
| **Done** | Tasks fully completed and verified | No limit |

### Justification for Custom Columns:
- **Testing** was added because the Definition of Done in Assignment 6 requires all API endpoints to be tested before a story is considered complete. Having a dedicated Testing column makes it visible when tasks are awaiting validation rather than conflating them with Done.
- **Blocked** was added to make blockers immediately visible on the board. In a solo project, tasks can become blocked when a dependency is not yet complete — for example, the booking API cannot be built until the doctor search API is finished. The Blocked column prevents these tasks from cluttering the In Progress column and signals that attention is needed.