# REFLECTION.md � Challenges in Balancing Stakeholder Needs

**Project:** PulsePoint � Patient Appointment & Records System

---

## 1. Introduction

Gathering and balancing stakeholder requirements is one of the most complex aspects of software engineering. For PulsePoint, seven stakeholders were identified , each with distinct roles, priorities, and expectations. This reflection documents the key challenges encountered when trying to satisfy all stakeholders simultaneously, the trade-offs that had to be made, and the lessons learned throughout the requirements elicitation process.

---

## 2. Challenge 1 � Patient Convenience vs. Security Requirements

One of the most significant tensions encountered was between the **Patient's** desire for a fast, frictionless experience and the **Medical Regulators'** strict requirements for data security and access control.

Patients want to register quickly, log in without friction, and access their records instantly. However, compliance officers require strong authentication, encrypted data storage, session timeouts, and detailed audit logs , all of which add steps and complexity to the user experience.

**How it was balanced:**
The system was designed to use JWT-based authentication with a 24-hour token expiry , long enough to avoid forcing patients to log in multiple times in a day, but short enough to meet security standards. Password hashing using bcrypt and HTTPS encryption were implemented invisibly in the background so they do not impact the user experience at all. The audit logging was made fully automatic, requiring no input from the user.

**Lesson Learned:** Security and usability do not have to be in direct conflict. Many security measures can be implemented transparently at the backend without degrading the front-end experience.

---

## 3. Challenge 2 � Receptionist Workflow vs. Patient Self-Service

The **Receptionist** stakeholder presented an interesting conflict with the **Patient** stakeholder. Patients want full self-service booking capabilities ,  the ability to book, reschedule, and cancel without any staff involvement. However, receptionists are concerned about losing visibility and control over the appointment book, particularly for elderly or non-tech-savvy patients who may still need assistance.

If the system is designed purely for self-service, receptionists become redundant for booking tasks but are still needed for patients who cannot use the system. If the system requires receptionist approval for every booking, it defeats the purpose of self-service.

**How it was balanced:**
The system was designed to support both workflows simultaneously. Patients can self-book online, while receptionists retain access to the same booking interface to assist patients in person or over the phone. Both actions go through the same system, eliminating the risk of double bookings. This way, receptionists transition from manual booking clerks to support staff , reducing their workload rather than replacing them entirely.

**Lesson Learned:** When two stakeholders have conflicting workflows- designing for both simultaneously rather than prioritising one often leads to the most practical solution.

---

## 4. Challenge 3 � Doctor Autonomy vs. Administrator Control

**Doctors** expressed a need for autonomy over their own schedules , they want to block off time for breaks, set their own availability, and control how many appointments they take per day. **Administrators**, on the other hand, need oversight of all schedules to ensure the clinic runs efficiently and that no doctor is underbooked or overburdened.

Giving doctors full control over their availability could result in gaps in clinic coverage that administrators are unaware of. Giving administrators full control over doctor schedules could frustrate doctors and reduce their sense of professional autonomy.

**How it was balanced:**
The system was designed so that doctors can manage their own availability within boundaries set by the administrator � for example, an admin can define core clinic hours during which doctors must remain available, while doctors can adjust their slots within those hours. Administrators retain read-only visibility into all doctor schedules at all times.

**Lesson Learned:** Hierarchical permission structures - where one stakeholder sets the boundaries and another operates within them , are an effective way to balance competing control needs.

---

## 5. Challenge 4 � IT Staff Maintainability vs. Feature Richness

**IT Support Staff** consistently prioritised simplicity, clean architecture, and thorough documentation over feature volume. They were concerned that adding too many features , such as real-time analytics, SMS integration, and complex role-based access , would make the system harder to maintain and more prone to failure.

In contrast, the **Administrator** and **Patient** stakeholders pushed for a feature-rich platform with dashboards, filters, export tools, and automated notifications.

**How it was balanced:**
A modular architecture was adopted so that each feature is self-contained and can be updated, replaced, or removed without affecting the rest of the system. This gave IT staff the maintainability they needed while still allowing all requested features to be included. A clear API documentation requirement was also added as a non-functional requirement to address IT staff concerns about future integrations.

**Lesson Learned:** Architecture decisions � not just feature decisions , are a form of stakeholder compromise. A well-structured system can be both feature-rich and maintainable.

---

## 6. Challenge 5 � Compliance Requirements vs. Project Scope

**Medical Regulators** introduced requirements that significantly expanded the original project scope , particularly around audit logging, data retention policies, and encryption standards. Some of these requirements (such as 5-year log retention and full POPIA compliance) are typically addressed at the infrastructure and legal level, not just at the application level.

For a semester-long individual project, implementing full regulatory compliance was not entirely feasible within the available time and resources.

**How it was balanced:**
The requirements were documented in full to reflect real-world compliance needs, but the implementation scope was clearly defined in SPECIFICATION.md. Compliance-related non-functional requirements (such as audit logging and encryption) that are achievable within the project were marked as in-scope, while infrastructure-level concerns (such as legal data retention enforcement) were noted as future considerations.

**Lesson Learned:** In real-world projects, not all stakeholder requirements can be fully implemented in a first release. Transparent scoping , (clearly documenting what is in and out of scope) is a professional and honest approach to managing stakeholder expectations.

---

## 7. Overall Reflection

The process of identifying and balancing stakeholder needs for PulsePoint revealed that requirements engineering is rarely straightforward. Every stakeholder has legitimate concerns, and many of those concerns directly conflict with one another. The key insight from this process is that **there is rarely a perfect solution** , only well-reasoned trade-offs that prioritise the most critical needs while transparently documenting what could not be fully addressed.

The most valuable skill developed through this exercise was learning to look at the same system from multiple perspectives simultaneously � as a patient, as a doctor, as a compliance officer, and as a developer � and finding designs that serve all of them as effectively as possible.

---

## 8. Summary of Key Trade-offs

| Conflict | Stakeholders Involved | Resolution |
|---|---|---|
| Convenience vs. Security | Patient vs. Regulators | Background security measures that don't impact UX |
| Self-service vs. Staff Control | Patient vs. Receptionist | Dual-access booking system supporting both workflows |
| Doctor Autonomy vs. Admin Oversight | Doctor vs. Administrator | Bounded availability � doctors manage within admin-set hours |
| Feature Richness vs. Maintainability | Admin/Patient vs. IT Staff | Modular architecture isolating each feature |
| Full Compliance vs. Project Scope | Regulators vs. Individual Developer | In-scope compliance features documented; future items noted |

----------------------------------------------------------------------------------------------------------
# REFLECTION5.md — Challenges in Translating Requirements to Use Cases and Test Cases

**Project:** PulsePoint — Patient Appointment & Records System
**Assignment:** 5 — Use Case Modeling and Test Case Development

---

## 1. Introduction

Translating stakeholder requirements into use case diagrams, detailed use case specifications, and test cases is one of the most demanding phases of the software engineering process. While requirements describe *what* the system must do, use cases describe *how* users interact with the system to achieve those goals, and test cases define *how* we verify that those interactions work correctly. This reflection documents the key challenges encountered during this translation process for the PulsePoint system and the lessons learned along the way.

---

## 2. Challenge 1 — Deciding Which Use Cases Were Critical

The first challenge was deciding which use cases to include in the diagram and which 8 to expand into full specifications. PulsePoint has many possible interactions , a patient alone can register, log in, search for doctors, book, reschedule, cancel, view records, and receive SMS reminders. Including every possible interaction in a single diagram risks making it unreadable and overwhelming.

The approach taken was to prioritise use cases that directly addressed the highest-priority stakeholder concerns identified in Assignment 4. For example, booking an appointment was clearly a high-priority use case because it addressed the Patient's core pain point of inconvenient phone-based scheduling. Similarly, creating medical records was prioritised because it addressed the Doctor's concern about fragmented paper-based record keeping.

This process required careful cross-referencing between the stakeholder analysis, functional requirements, and the use case diagram — making it clear that requirements engineering is not a linear process but a constantly iterative one.

---

## 3. Challenge 2 — Modelling Include Relationships Correctly

One of the most technically challenging aspects of creating the use case diagram was correctly identifying and modelling the include relationships between use cases. An include relationship means that one use case always depends on another to function.

The most significant include relationship in PulsePoint is that almost every use case includes Login to System , because authentication is a prerequisite for nearly all system actions. Initially, drawing an include arrow from every use case to Login to System made the diagram extremely cluttered and difficult to read. The challenge was finding a balance between technical accuracy and visual clarity.

The solution was to show the most important include relationships , such as Book Appointment including Login to System and Book Appointment including Search for Doctor , while noting in the written explanation that authentication is a universal precondition across the system. This taught an important lesson about the difference between a technically complete diagram and a practically useful one.

---

## 4. Challenge 3 — Writing Meaningful Alternative Flows

Writing the basic flow for each use case was relatively straightforward , it simply described the ideal success scenario step by step. The real challenge was writing meaningful alternative flows that covered realistic error paths without becoming repetitive or trivial.

For example, for the Book Appointment use case, it was easy to identify that a patient might search for a doctor and find no results. However, a more subtle alternative flow was the scenario where a time slot becomes unavailable between the moment the patient selects it and the moment they confirm the booking , a race condition that occurs in real booking systems with multiple concurrent users. Identifying this kind of scenario required thinking beyond the happy path and considering real-world system behaviour under concurrent usage.

This challenge highlighted that writing good alternative flows requires both technical knowledge of how systems behave and empathy for the range of situations real users encounter.

---

## 5. Challenge 4 — Tracing Test Cases Back to Requirements

Developing test cases that were genuinely traceable to the functional requirements from Assignment 4 was more difficult than expected. The temptation was to write generic test cases such as "verify the login page works" without linking them to a specific requirement. However, the assignment rubric required test cases to directly address stakeholder needs, which meant each test case had to be anchored to a named requirement.

To address this, every test case was mapped back to a specific functional requirement from Assignment 4 , for example, TC005 traces directly to Functional Requirement 03 (Appointment Booking) and TC010 traces to Functional Requirement 12 (Audit Trail Logging). This traceability ensured that the test cases were purposeful and covered the system's most critical behaviours rather than being arbitrarily selected.

---

## 6. Challenge 5 — Defining Non-Functional Test Scenarios

Defining test scenarios for non-functional requirements was particularly challenging because non-functional requirements deal with qualities like performance and security rather than specific features. Unlike functional tests where you can follow clear steps and check an expected output, non-functional tests require specialised tools and more complex setup.

For the performance test, determining a realistic load figure required referencing the scalability requirement from Assignment 4 which specified 500 concurrent users. For the security test, the challenge was designing a test that genuinely attempted to breach the role-based access control rather than simply confirming that a logged-in user can see their own records. This required thinking like an attacker — using a valid JWT token to attempt access to another user's data via direct API manipulation.

---

## 7. Conclusion

The process of translating requirements into use cases and test cases revealed that these are not mechanical tasks but require significant analytical thinking. Every decision , which use cases to include, how to model relationships, which alternative flows to document, and how to trace tests back to requirements , involves judgement calls that directly affect the quality of the final system. The most important lesson from this assignment is that good software engineering is not just about writing code, but about thoroughly understanding and modelling the system before a single line of code is written.

---
# REFLECTION6.md — Challenges in Agile Planning for PulsePoint

**Assignment:** 6 — Agile User Stories, Backlog, and Sprint Planning

---

## 1. Introduction

The agile approach provides a lot of flexibility, iterations, and improvements. Nevertheless, the implementation of agile principles into an individual project presented me with a particular kind of problems that one does not usually find in books on the topic, problems that do not arise due to conflicts within the group or with stakeholders but because of internal opposition and personal fears. This reflective paper describes my experience dealing with such issues while implementing an agile plan for PulsePoint.

---

## 2. Challenge 1 — Playing Multiple Scrum Roles Alone

In a real Agile team, the Product Owner, Scrum Master, and Developer are three different people who have different points of view and duties. The Product Owner puts business value first, the Scrum Master makes the process easier, and the Developer figures out how much work is needed based on how hard it is. One person had to play all three roles at the same time for PulsePoint.

This made me feel like I was always at war with myself. As the Product Owner, I wanted to add as many features as possible to the first sprint to show that the system was working. As the Developer, the truth was that one person could not realistically finish 14 user stories with complicated backend logic, database design, API development, and frontend implementation in two weeks. As the Scrum Master, it was your job to help make a sprint plan that was realistic and doable.

To solve this problem, we had to take a step back and really commit to the Agile principle of delivering a small but working increment instead of a big but incomplete one. It was harder than I thought it would be to admit that only 6 user stories could be done in Sprint 1. It felt like not delivering enough. Choosing a focused and doable sprint is a sign of Agile maturity, not weakness.
---

## 3. Challenge 2 — Estimating Story Points Without a Team

Planning Poker and other methods that involve team members debating and agreeing on effort estimates are common ways to estimate story points in Agile. When I worked alone, there was no one to question or check my estimates, which made them very likely to be wrong.

It was easy to think that tasks that seemed easy, like making a login form, would take less work than they actually did, and that tasks that seemed hard, like integrating the Twilio API, would take more work than they actually did. These biases could go unchecked and lead to a sprint plan that was either too optimistic or too cautious if there wasn't a team to push back.

To handle this, each task was split up into the smallest parts possible before hours were given. Instead of estimating "implement authentication" as a single 10-hour task, it was broken down into smaller tasks for the API endpoint, bcrypt integration, JWT token generation, and React UI, each with its own estimate. This detailed method cut down on bias and gave a more accurate total of 57 hours across 15 tasks for Sprint 1.
---


# 4. Challenge 3 — Prioritising Features Without External Stakeholder Input

When real stakeholders can confirm which features are really important, MoSCoW prioritization works best. There were no real patients, doctors, or administrators to talk to about PulsePoint because it was a single project. All decisions about what to put first had to be based on the stakeholder analysis from Assignment 4 and your own judgment.

It was hardest to decide whether to include the analytics dashboard in Sprint 1. It was tempting to add it as a developer because it is fun to build. But when you really think like a product owner, it becomes clear that an administrator can't use analytics until there are real appointments in the system. This means that there need to be patients, doctors, booking functionality, and medical records first. Because of this logical dependency, the analytics dashboard was put in the "Should-have" category instead of the "Must-have" category, and it was put off until a later sprint.

This experience showed that good prioritization means putting aside personal preferences and focusing on what gives the end user the most value at each stage of development.

---

## 5. Challenge 4 — Maintaining Traceability Across Three Assignments

One of the most technically demanding aspects of Assignment 6 was ensuring that every user story traced back to a specific functional requirement from Assignment 4 and a specific use case from Assignment 5. This required constantly cross-referencing three separate documents and ensuring that nothing was invented from scratch.

There were times when we didn't want to check if our user stories matched up with what we were supposed to be doing. It would have been easier to just write them without worrying about it. But we had a traceability matrix at the end of our AGILE-PLANNING.md document that kept us in line. This matrix helped us make sure our user stories were good quality. If a story didn't match up with something we were supposed to be doing, we either changed it or got rid of it. This way, our backlog was based on what our stakeholders actually needed, not just what we thought they needed.

---

## 6. Conclusion

Planning as a solo developer is really different from doing it in a team. The hardest part isn't about the tech ,it's about being honest with yourself. You need to be disciplined, humble, and aware of your own biases when you're making plans and prioritizing tasks. I've learned a lot from working on PulsePoint, and I now see why certain planning steps, like sprint planning and retrospectives, are so important. They're not just formalities, they help keep your development on track, realistic, and focused on what really matters. When you're working alone, it can be tough to stay grounded and make sure you're delivering something valuable. But with the right mindset and tools, you can make it work. It's all about being self-aware, recognizing your strengths and weaknesses, and being willing to adjust your plans accordingly. By doing so, you can create a development process that's tailored to your needs and helps you achieve your goals. In the end, planning as a solo developer requires a unique set of skills and mindset. It's not just about following a set of rules or procedures , it's about being adaptable, resilient, and committed to delivering high-quality results. With practice and experience, you can develop the skills and habits needed to succeed as a solo developer, and make the most out of your planning process.

# REFLECTION7.md — Challenges in Selecting and Customising the GitHub Project Template

**Assignment:** 7 — GitHub Project Templates and Kanban Board Implementation

---

## 1. Introduction

Setting up a Kanban board on GitHub Projects seemed straightforward at first — choose a template, add some columns, and populate it with tasks. In practice, however, the process raised several interesting challenges around template selection, customisation decisions, and understanding how GitHub's project management tools compare to more established tools like Trello and Jira. This reflection documents those challenges honestly and draws lessons from the experience.

---

## 2. Challenge 1 — Choosing the Right Template

The first challenge was selecting the most appropriate template from GitHub's available options. GitHub offers several templates including Team Planning, Kanban, Feature Release, Bug Tracker, Iterative Development, Roadmap, and Team Retrospective. Each template is designed for a different context, and choosing the wrong one would result in a board that does not reflect the actual development workflow.

The initial instinct was to select the **Team Planning** template because it had the most columns and appeared the most comprehensive. However, on closer inspection, Team Planning is designed for larger teams managing work across multiple sprints and team members. For a solo project like PulsePoint, the additional columns added unnecessary complexity rather than clarity.

The **Kanban** template was ultimately chosen because it is the only template with built-in WIP limit support — a core Kanban principle explicitly mentioned in the assignment brief. Its default columns of To Do, In Progress, and Done provided a clean starting point that could be extended with the custom Testing and Blocked columns to match the PulsePoint workflow.

The lesson learned here is that more features do not always mean a better tool. Choosing the simplest template that meets the project's actual needs is always better than choosing the most feature-rich one.

---

## 3. Challenge 2 — Deciding Which Custom Columns to Add

The assignment required adding at least two custom columns to the board. The challenge was deciding which columns would genuinely improve the workflow rather than adding columns for the sake of it.

Several options were considered — Review, Deployed, Backlog, and On Hold — before settling on **Testing** and **Blocked**. Testing was chosen because the Definition of Done from Assignment 6 explicitly requires all tasks to be tested before they are considered complete. Without a Testing column, there would be no way to distinguish between tasks that are finished and tasks that are finished and verified — a critical distinction in software development.

Blocked was chosen because PulsePoint's Sprint 1 tasks have clear dependencies. For example, the appointment booking API cannot be built until the doctor search API is complete, and the SMS integration cannot be implemented until the booking API exists. A Blocked column makes these dependency issues immediately visible on the board rather than hiding them inside the In Progress column.

---

## 4. Challenge 3 — Setting Up Issues and Labels on GitHub

One of the most practically challenging parts of this assignment was setting up GitHub Issues correctly. GitHub does not come with all the labels needed for a development project — labels like `feature` and `in-progress` had to be created manually before they could be applied to issues.

Additionally, linking issues to the project board required navigating between the repository's Issues tab and the Projects tab, which was initially confusing. Understanding that issues are created in the repository but managed on the project board — and that these are two separate but connected spaces — took some time to grasp.

This experience highlighted that GitHub Projects is a powerful tool but has a steeper learning curve than it initially appears. The interface is not entirely intuitive for first-time users, and understanding the relationship between repositories, issues, and projects requires hands-on exploration.

---

## 5. Comparing GitHub Projects to Trello and Jira

Having worked with GitHub Projects for this assignment, it is worth comparing it to two other widely used project management tools — Trello and Jira.

**Trello** is the simplest of the three. It uses a straightforward card-and-column interface that anyone can learn in minutes. Its strength is simplicity — drag and drop cards across columns with no setup required. However, Trello lacks deep integration with code repositories, meaning there is no automatic connection between a Trello card and a GitHub commit or pull request. For a software development project, this disconnect is a significant limitation.

**Jira** is the most powerful of the three and is the industry standard for large Agile teams. It supports full sprint planning, burndown charts, velocity tracking, epics, and detailed reporting. However, Jira is complex and expensive for small projects. Its interface can be overwhelming, and setting it up correctly for a solo project requires more effort than the value it delivers.

**GitHub Projects** sits comfortably between the two. It integrates directly with the repository, meaning issues, pull requests, and commits are all connected to the board without any additional configuration. It is free, accessible, and sufficient for managing a semester-long solo project. Its main limitation compared to Jira is the lack of advanced reporting features like burndown charts and velocity tracking, which would be valuable for monitoring sprint progress.

For PulsePoint at this stage of development, GitHub Projects is the most appropriate tool — it provides enough structure to manage the sprint without the overhead of Jira or the disconnect of Trello.

---

## 6. Conclusion

This assignment demonstrated that effective project management is not just about having the right tools — it is about understanding how to use them appropriately for the context. The process of selecting a template, customising the board, and populating it with Sprint 1 tasks brought the Agile planning from Assignment 6 to life in a practical, visual way. The challenges encountered — from template selection to label creation — were all valuable learning experiences that deepened the understanding of how professional software teams manage their work.

---
# REFLECTION8.md — Challenges in Object State Modeling and Activity Workflow Modeling

**Assignment:** 8 — Object State Modeling and Activity Workflow Modeling

---

## 1. Introduction

Creating state transition diagrams and activity diagrams for PulsePoint was a significantly more demanding task than the previous assignments. While earlier assignments focused on describing what the system should do through requirements and user stories, this assignment required modelling exactly how the system behaves at a dynamic level — capturing every state an object can be in and every step a workflow can follow. This reflection documents the key challenges encountered during this process and the lessons learned from comparing the two diagram types.

---

## 2. Challenge 1 — Choosing the Right Level of Granularity

The most persistent challenge throughout this assignment was deciding how much detail to include in each diagram. Every object and workflow in PulsePoint could theoretically be broken down into dozens of micro-states or steps, but a diagram that is too detailed becomes unreadable and loses its value as a communication tool. On the other hand, a diagram that is too high-level fails to capture important edge cases and alternative flows.

For the Appointment state diagram, the initial version only had four states — Pending, Confirmed, Completed, and Cancelled. However, on reflection, this missed several important real-world scenarios such as the NoShow state, the Rescheduled state, and the Failed state when a slot becomes unavailable during the booking process. Adding these states made the diagram more accurate but also more complex.

The balance was found by asking a simple question for each proposed state or step: "Does this state or action change what the system can do next?" If the answer was yes, it was included. If it was simply a sub-step within an existing state, it was left out. This principle of meaningful granularity — including only states and steps that affect system behaviour — produced diagrams that were both detailed enough to be useful and simple enough to be readable.

---

## 3. Challenge 2 — Modelling Parallel Actions in Activity Diagrams

Several of PulsePoint's workflows involve actions that happen simultaneously rather than sequentially. For example, when a patient successfully books an appointment, the system displays a confirmation screen to the patient at the same time as it sends an SMS via Twilio. These concurrent actions were challenging to represent clearly in Mermaid's flowchart syntax, which is primarily designed for sequential flows.

The solution was to use separate swimlanes for each actor — Patient, System, and Twilio — and show the parallel paths branching from the same trigger point. This made it visually clear that the SMS sending and the confirmation screen display are independent actions triggered by the same event, rather than one waiting for the other to complete.

This experience highlighted an important real-world design consideration — if the SMS delivery is slow or fails, it should not delay the confirmation screen from appearing to the patient. Modelling the parallel actions explicitly in the diagram helped clarify this architectural requirement in a way that a written requirement alone could not.

---

## 4. Challenge 3 — Aligning Diagrams with Agile User Stories

One of the assignment requirements was to ensure every diagram traced back to a specific user story from Assignment 6. This proved challenging for some diagrams because user stories are written from a user's perspective — "As a patient, I want to book an appointment" — while activity diagrams model the system's internal behaviour in detail, including steps the user never sees such as database writes, password hashing, and audit log creation.

The challenge was bridging the gap between the high-level user intent expressed in a user story and the low-level system behaviour captured in an activity diagram. The approach taken was to map each diagram to the user story that most directly motivated it, while acknowledging in the explanation that the diagram covers additional system behaviour beyond what the user story explicitly describes.

This process reinforced the Agile principle that user stories are not technical specifications — they are expressions of user need. The technical detail lives in the diagrams and implementation, not in the story itself.

---

## 5. Comparing State Diagrams and Activity Diagrams

State transition diagrams and activity diagrams serve fundamentally different purposes, and understanding the distinction between them was one of the most valuable lessons from this assignment.

A **state transition diagram** focuses on a single object and asks: "What states can this object be in, and what causes it to change from one state to another?" It is object-centric. The Appointment state diagram, for example, does not care about who triggers the transition — it only cares that the Appointment object moves from Confirmed to Cancelled when a cancellation event occurs.

An **activity diagram** focuses on a process and asks: "What steps are involved in completing this workflow, and who is responsible for each step?" It is process-centric and actor-aware. The Book Appointment activity diagram shows not just what happens, but who does each thing — the Patient selects a slot, the System validates availability, and Twilio sends the SMS.

In practice, the two diagram types complement each other. The state diagram answers "what can happen to this object?" while the activity diagram answers "how does it happen, and who makes it happen?" Together they provide a complete picture of the system's dynamic behaviour — one from the perspective of the data, and one from the perspective of the process.

---

## 6. Conclusion

This assignment deepened the understanding of dynamic system modelling in ways that static diagrams like C4 architecture and use case diagrams cannot. Seeing an Appointment object move through eight distinct states, or watching a booking workflow branch across three swimlanes with parallel actions and retry logic, makes the system feel tangible and real in a way that a list of requirements never could. The challenges of granularity, parallel actions, and traceability to user stories all pushed towards more thoughtful and precise modelling — skills that will be directly applicable when the actual implementation of PulsePoint begins.

---

