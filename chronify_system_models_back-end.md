# Chronify System - Software Engineering Models

## Table of Contents
1. [Context Model](#context-model)
2. [Business Process Model](#business-process-model)
3. [Use Case Diagram](#use-case-diagram)
4. [Use Case Descriptions](#use-case-descriptions)
5. [Sequence Diagrams](#sequence-diagrams)
6. [Class Diagram](#class-diagram)
7. [Data Flow Diagram](#data-flow-diagram)

---

## Context Model

The Context Model defines the system boundaries and identifies how the Chronify system interacts with its external environment.

```mermaid
graph TB
    User["👤 Mobile User<br/>Students"]
    Browser["🌐 Web Browser<br/>Frontend Client"]
    Chronify["📅 Chronify System<br/>Backend Server"]
    DB["🗄️ Database<br/>Persistence Layer"]
    Email["📧 Email Service<br/>External Service"]
    Clock["⏰ System Clock<br/>Event Trigger"]
    
    User -->|HTTP/REST API| Chronify
    Browser -->|HTTP/REST API| Chronify
    Chronify -->|Query/Update| DB
    Chronify -->|Send Reminders| Email
    Chronify -->|Check Time| Clock
    DB -->|Return Data| Chronify
    Email -->|Confirmation| User
    
    style Chronify fill:#4A90E2,stroke:#2E5C8A,stroke-width:3px,color:#fff
    style User fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style Browser fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style DB fill:#FF7043,stroke:#E64A19,stroke-width:2px,color:#fff
    style Email fill:#AB47BC,stroke:#6A1B9A,stroke-width:2px,color:#fff
    style Clock fill:#29B6F6,stroke:#0277BD,stroke-width:2px,color:#fff
```

### System Boundaries
- **Inside the System**: User authentication, course management, schedule management, notes, reminders
- **Outside the System**: Mobile/web client applications, database management system, email service, system clock

---

## Business Process Model

The Business Process Model shows the automated business processes that Chronify will handle, including the workflow and data relationships.

```mermaid
graph TD
    Start([User Starts App]) --> Auth{Authenticated?}
    Auth -->|No| Login[User Login/Register]
    Login --> ValidateUser[Validate Username & Password]
    ValidateUser --> GenerateToken[Generate JWT Token]
    GenerateToken --> Dashboard[User Dashboard]
    Auth -->|Yes| Dashboard
    
    Dashboard --> Choice{User Action}
    
    Choice -->|View Schedule| ViewSchedule[Query Schedules by Date]
    ViewSchedule --> DisplaySchedule[Display Schedule Events]
    DisplaySchedule --> Dashboard
    
    Choice -->|Add Schedule| AddSchedule[Create New Schedule Event]
    AddSchedule --> SaveToDB[Save to Database]
    SaveToDB --> Dashboard
    
    Choice -->|Manage Courses| ManageCourses[CRUD Course Operations]
    ManageCourses --> SaveCourse[Save to Database]
    SaveCourse --> Dashboard
    
    Choice -->|Take Notes| TakeNotes[Create/Edit Notes]
    TakeNotes --> SaveNotes[Save to Database]
    SaveNotes --> Dashboard
    
    Choice -->|Set Reminder| SetReminder[Create Reminder for Event]
    SetReminder --> SaveReminder[Save to Database]
    SaveReminder --> NotifyUser[System Notifies User at Time]
    NotifyUser --> Dashboard
    
    Dashboard --> Logout{Logout?}
    Logout -->|Yes| End([End Session])
    Logout -->|No| Dashboard
    
    style Start fill:#90EE90,stroke:#228B22,stroke-width:2px
    style End fill:#FFB6C6,stroke:#DC143C,stroke-width:2px
    style Dashboard fill:#87CEEB,stroke:#4682B4,stroke-width:2px
    style NotifyUser fill:#FFD700,stroke:#FF8C00,stroke-width:2px
```

---

## Use Case Diagram

```mermaid
graph TB
    subgraph "Chronify System"
        UC1["Login/Register"]
        UC2["Manage Profile"]
        UC3["Manage Courses"]
        UC4["Manage Schedules"]
        UC5["Manage Notes"]
        UC6["Manage Reminders"]
        UC7["View Dashboard"]
        UC8["System Notification"]
    end
    
    Student["Student User"]
    System["External System"]
    EmailSvc["Email Service"]
    Clock["System Clock"]
    
    Student -->|authenticate| UC1
    Student -->|update info| UC2
    Student -->|create/edit/delete| UC3
    Student -->|create/edit/delete/view| UC4
    Student -->|create/edit/delete| UC5
    Student -->|create/edit/delete| UC6
    Student -->|view| UC7
    
    UC3 -.->|include| UC7
    UC4 -.->|include| UC7
    UC5 -.->|include| UC7
    UC6 -.->|include| UC7
    
    UC1 -.->|prerequisite| UC2
    UC1 -.->|prerequisite| UC4
    
    UC6 -->|trigger| UC8
    UC8 -.->|notification| EmailSvc
    
    Clock -->|check time| UC8
    
    style Student fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style EmailSvc fill:#AB47BC,stroke:#6A1B9A,stroke-width:2px,color:#fff
    style Clock fill:#29B6F6,stroke:#0277BD,stroke-width:2px,color:#fff
    style UC1 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC2 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC3 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC4 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC5 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC6 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC7 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC8 fill:#FF7043,stroke:#E64A19,stroke-width:2px,color:#fff
```

---

## Use Case Descriptions

### UC1: Login/Register

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User |
| **Precondition** | User has a valid username/password or wants to create new account |
| **Postcondition** | User is authenticated with JWT token and can access system |
| **Main Flow** | 1. User enters login page<br/>2. User provides username and password<br/>3. System validates credentials against database<br/>4. System generates JWT token<br/>5. System returns token and user info |
| **Alternative Flow** | - User selects "Register"<br/>- User provides username and password<br/>- System checks if username exists<br/>- System creates new user account<br/>- System logs user in automatically |
| **Exception Handling** | - Invalid credentials: Return error message<br/>- User already exists: Return duplicate error<br/>- Database error: Return server error |

### UC2: Manage Profile

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User (authenticated) |
| **Precondition** | User must be logged in with valid token |
| **Postcondition** | User profile information is updated in system |
| **Main Flow** | 1. User navigates to profile page<br/>2. System retrieves user info from database<br/>3. System displays user profile form<br/>4. User modifies profile fields (nickname, avatar, gender, school)<br/>5. User submits updated info<br/>6. System validates and saves changes |
| **Alternative Flow** | - User cancels changes: No database update<br/>- User changes password: System re-encrypts and stores |
| **Exception Handling** | - Permission denied: Only own profile can be updated<br/>- Database error: Return error message<br/>- Invalid input: Return validation error |

### UC3: Manage Courses

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User (authenticated) |
| **Precondition** | User must be logged in |
| **Postcondition** | Course information is created/updated/deleted in system |
| **Main Flow** | 1. User navigates to courses section<br/>2. System displays list of user's courses<br/>3. User selects "Add Course"<br/>4. User fills course form (name, day, time, location, teacher, weeks, notes)<br/>5. System validates input<br/>6. System saves course to database<br/>7. System refreshes course list display |
| **Alternative Flow** | - Update Course: Select existing course → modify fields → save<br/>- Delete Course: Select course → confirm deletion → remove from database |
| **Exception Handling** | - Invalid time format: Show format error<br/>- Database conflict: Show error<br/>- Permission denied: Cannot modify other users' courses |

### UC4: Manage Schedules

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User (authenticated) |
| **Precondition** | User must be logged in |
| **Postcondition** | Schedule event is created/updated/deleted in system |
| **Main Flow** | 1. User navigates to schedules section<br/>2. System displays schedule view (calendar/list)<br/>3. User selects date or "Add Schedule"<br/>4. User enters event details (event name, date, time, location, people, priority)<br/>5. System validates input<br/>6. System saves schedule to database<br/>7. System updates display with new event |
| **Alternative Flow** | - Update Schedule: Select existing event → edit → save<br/>- Delete Schedule: Select event → confirm → remove<br/>- View by Date: User selects date → system filters and displays |
| **Exception Handling** | - Time conflict: Warn user of overlapping events<br/>- Invalid date: Show date error<br/>- Permission denied: Cannot modify other users' schedules |

### UC5: Manage Notes

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User (authenticated) |
| **Precondition** | User must be logged in |
| **Postcondition** | Note is created/updated/deleted in system |
| **Main Flow** | 1. User navigates to notes section<br/>2. System displays list of user's notes<br/>3. User selects "Create Note" or existing note<br/>4. User writes/edits note content<br/>5. System auto-saves changes<br/>6. User submits final version<br/>7. System stores note in database |
| **Alternative Flow** | - Search Notes: User enters keywords → system filters notes<br/>- Delete Note: Select note → confirm → remove |
| **Exception Handling** | - Database error: Show save error<br/>- Session expired: Require re-login<br/>- Permission denied: Cannot modify other users' notes |

### UC6: Manage Reminders

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User (authenticated) |
| **Precondition** | User must be logged in; Schedule event exists |
| **Postcondition** | Reminder is created/updated/deleted; System will notify user |
| **Main Flow** | 1. User views schedule event<br/>2. User selects "Set Reminder"<br/>3. User chooses reminder time (before event)<br/>4. System saves reminder to database<br/>5. System monitors time<br/>6. At reminder time, system triggers notification |
| **Alternative Flow** | - Multiple reminders: User can set multiple reminders per event<br/>- Delete Reminder: Select reminder → confirm → remove |
| **Exception Handling** | - Invalid time: Reminder time cannot be after event<br/>- Schedule not found: Cannot set reminder for non-existent event |

### UC7: View Dashboard

| Aspect | Description |
|--------|-------------|
| **Actor** | Student User (authenticated) |
| **Precondition** | User must be logged in |
| **Postcondition** | System displays personalized dashboard view |
| **Main Flow** | 1. User logs in successfully<br/>2. System retrieves user's data (courses, schedules, notes)<br/>3. System displays dashboard with:<br/>   - Today's schedule overview<br/>   - Upcoming courses<br/>   - Pending reminders<br/>   - Recent notes<br/>4. User can click on items to view details |
| **Alternative Flow** | - Filter by date: User selects date range<br/>- Sort by priority: User sorts schedules |
| **Exception Handling** | - No data available: Show empty state message<br/>- Database error: Show error message |

### UC8: System Notification

| Aspect | Description |
|--------|-------------|
| **Actor** | System (triggered by reminder/schedule) |
| **Precondition** | Reminder time has been reached; User is online |
| **Postcondition** | User receives notification; Email sent if configured |
| **Main Flow** | 1. System clock reaches reminder time<br/>2. System queries active reminders<br/>3. System checks if reminder is due<br/>4. System sends in-app notification to user<br/>5. System optionally sends email notification<br/>6. System logs notification event |
| **Alternative Flow** | - Offline notification: Store notification; deliver when user comes online |
| **Exception Handling** | - Email service unavailable: Log error; try retry mechanism<br/>- User deleted event: Skip notification |

---

## Sequence Diagrams

### Sequence 1: User Login Flow

```mermaid
sequenceDiagram
    actor User
    participant Browser as Frontend<br/>Browser
    participant API as AuthController
    participant Service as UserService
    participant Mapper as UserMapper
    participant DB as Database
    participant JwtUtil as JWT Util
    
    User->>Browser: Enter credentials
    Browser->>API: POST /api/v1/auth/login
    API->>Service: login(username, password)
    Service->>Mapper: findByUsername(username)
    Mapper->>DB: Query user by username
    DB-->>Mapper: User record
    Mapper-->>Service: User object
    Service->>Service: Verify password (MD5)
    alt Password Correct
        Service->>JwtUtil: generateToken(userId)
        JwtUtil-->>Service: JWT Token
        Service-->>API: User object + token
        API-->>Browser: {success, user, token}
        Browser-->>User: Login successful
    else Password Incorrect
        Service-->>API: null
        API-->>Browser: {error, message}
        Browser-->>User: Invalid credentials
    end
```

### Sequence 2: Create Schedule Event

```mermaid
sequenceDiagram
    actor User
    participant Browser as Frontend<br/>Browser
    participant API as ScheduleController
    participant Util as CurrentUserUtil
    participant Service as ScheduleService
    participant Mapper as ScheduleMapper
    participant DB as Database
    
    User->>Browser: Fill schedule form
    Browser->>API: POST /api/v1/schedules
    API->>Util: getCurrentUserId()
    Util-->>API: userId (from JWT)
    API->>API: Set userId on schedule
    API->>Service: add(schedule)
    Service->>Mapper: insert(schedule)
    Mapper->>DB: INSERT into schedules
    DB-->>Mapper: Inserted ID
    Mapper-->>Service: Schedule saved
    Service-->>API: Success
    API-->>Browser: {success, message}
    Browser-->>User: Schedule created
```

### Sequence 3: Trigger Reminder Notification

```mermaid
sequenceDiagram
    participant Clock as System Clock
    participant Service as ReminderService
    participant Mapper as ReminderMapper
    participant DB as Database
    participant Email as Email Service
    participant User as User Client
    
    Clock->>Service: Check due reminders
    Service->>Mapper: findDueReminders()
    Mapper->>DB: Query reminders WHERE reminderTime <= now()
    DB-->>Mapper: List of due reminders
    Mapper-->>Service: Reminder records
    loop For each due reminder
        Service->>Email: Send reminder email
        Email-->>User: Email delivered
        Service->>DB: Update reminder status
        Service->>User: Push notification
    end
    Service->>Service: Log notification events
```

### Sequence 4: View User Profile

```mermaid
sequenceDiagram
    actor User
    participant Browser as Frontend<br/>Browser
    participant API as UserController
    participant Util as CurrentUserUtil
    participant Service as UserService
    participant Mapper as UserMapper
    participant DB as Database
    
    User->>Browser: Navigate to profile
    Browser->>API: GET /api/v1/users/profile
    API->>Util: getCurrentUserId()
    Util-->>API: userId (from JWT Token)
    API->>Service: getUserInfo(userId)
    Service->>Mapper: findById(userId)
    Mapper->>DB: SELECT * FROM users WHERE id = ?
    DB-->>Mapper: User record
    Mapper-->>Service: User object
    Service-->>API: User object
    API-->>Browser: {success, user}
    Browser-->>User: Display profile page
```

### Sequence 5: Complete Schedule Management Workflow

```mermaid
sequenceDiagram
    actor Student as Student
    participant Dashboard as Dashboard
    participant Schedule as Schedule<br/>Service
    participant Course as Course<br/>Service
    participant Reminder as Reminder<br/>Service
    participant DB as Database
    participant Notify as Notification
    
    Student->>Dashboard: Open app
    Dashboard->>Schedule: Get schedules for today
    Schedule->>DB: Query schedules
    DB-->>Schedule: List of events
    Dashboard->>Course: Get courses for today
    Course->>DB: Query courses
    DB-->>Course: List of courses
    Dashboard->>Reminder: Get pending reminders
    Reminder->>DB: Query reminders
    DB-->>Reminder: List of reminders
    Dashboard-->>Student: Display overview
    
    Student->>Dashboard: Click add schedule
    Dashboard->>Schedule: Create new event
    Schedule->>DB: Insert schedule
    DB-->>Schedule: Success
    
    Student->>Dashboard: Set reminder for event
    Dashboard->>Reminder: Create reminder
    Reminder->>DB: Insert reminder
    DB-->>Reminder: Success
    
    Student->>Dashboard: Logout
    
    Note over Reminder: Later...
    Reminder->>Notify: Send notification at time
    Notify-->>Student: Remind notification
```

---

## Class Diagram

```mermaid
classDiagram
    class User {
        -Long id
        -String username
        -String password
        -String nickname
        -String avatar
        -String gender
        -String school
        -LocalDateTime createTime
        -LocalDateTime updateTime
        +login()
        +register()
        +updateProfile()
        +getInfo()
    }
    
    class Schedule {
        -Long id
        -String event
        -LocalDate date
        -LocalTime time
        -String location
        -String people
        -String priority
        -Long userId
        +add()
        +update()
        +delete()
        +getById()
        +list()
    }
    
    class Course {
        -Long id
        -String name
        -Integer dayOfWeek
        -String time
        -String location
        -String teacher
        -List<Integer> weeks
        -String notes
        -Long userId
        +add()
        +update()
        +delete()
        +getById()
        +list()
    }
    
    class Note {
        -Long id
        -String title
        -String content
        -LocalDateTime createTime
        -LocalDateTime updateTime
        -Long userId
        +add()
        +update()
        +delete()
        +getById()
        +list()
    }
    
    class Reminder {
        -Long id
        -String event
        -LocalDateTime reminderTime
        -Long scheduleId
        -Long userId
        +add()
        +update()
        +delete()
        +getById()
        +findDueReminders()
    }
    
    class PageResult {
        -Integer total
        -Integer pages
        -Integer pageNum
        -Integer pageSize
        -List<T> rows
    }
    
    class Result {
        -Integer code
        -String message
        -Object data
        +success()
        +error()
    }
    
    class UserService {
        -UserMapper mapper
        +login()
        +register()
        +getUserInfo()
        +updateProfile()
    }
    
    class ScheduleService {
        -ScheduleMapper mapper
        +add()
        +update()
        +delete()
        +getById()
        +list()
    }
    
    class CourseService {
        -CourseMapper mapper
        +add()
        +update()
        +delete()
        +getById()
        +list()
    }
    
    class NoteService {
        -NoteMapper mapper
        +add()
        +update()
        +delete()
        +getById()
        +list()
    }
    
    class ReminderService {
        -ReminderMapper mapper
        +add()
        +update()
        +delete()
        +getById()
        +findDueReminders()
        +sendNotifications()
    }
    
    class AuthController {
        -UserService userService
        +login()
        +register()
    }
    
    class UserController {
        -UserService userService
        +getProfile()
        +updateProfile()
    }
    
    class ScheduleController {
        -ScheduleService scheduleService
        +list()
        +add()
        +getById()
        +update()
        +delete()
    }
    
    class CourseController {
        -CourseService courseService
        +list()
        +add()
        +getById()
        +update()
        +delete()
    }
    
    class NoteController {
        -NoteService noteService
        +list()
        +add()
        +getById()
        +update()
        +delete()
    }
    
    class ReminderController {
        -ReminderService reminderService
        +list()
        +add()
        +getById()
        +update()
        +delete()
    }
    
    %% Relationships
    User "1" -- "*" Schedule: owns
    User "1" -- "*" Course: attends
    User "1" -- "*" Note: writes
    User "1" -- "*" Reminder: receives
    
    Schedule "1" -- "*" Reminder: has
    
    UserService --|> UserMapper: uses
    ScheduleService --|> ScheduleMapper: uses
    CourseService --|> CourseMapper: uses
    NoteService --|> NoteMapper: uses
    ReminderService --|> ReminderMapper: uses
    
    AuthController --|> UserService: calls
    UserController --|> UserService: calls
    ScheduleController --|> ScheduleService: calls
    CourseController --|> CourseService: calls
    NoteController --|> NoteService: calls
    ReminderController --|> ReminderService: calls
    
    style User fill:#E1F5FE,stroke:#0277BD,stroke-width:2px
    style Schedule fill:#F3E5F5,stroke:#6A1B9A,stroke-width:2px
    style Course fill:#E8F5E9,stroke:#2E7D32,stroke-width:2px
    style Note fill:#FFF3E0,stroke:#E65100,stroke-width:2px
    style Reminder fill:#FCE4EC,stroke:#C2185B,stroke-width:2px
    style Result fill:#F1F8E9,stroke:#558B2F,stroke-width:2px
    style PageResult fill:#F1F8E9,stroke:#558B2F,stroke-width:2px
```

---

## Data Flow Diagram

### Level 0: System Context

```mermaid
graph LR
    User["👤 User/Student<br/>(External Entity)"]
    System["📅 Chronify System<br/>(Process)"]
    DB["🗄️ Database<br/>(Data Store)"]
    Email["📧 Email Service<br/>(External Entity)"]
    
    User -->|Request:<br/>Login, Schedules,<br/>Courses, Notes| System
    System -->|Response:<br/>User Info, Events,<br/>Confirmation| User
    System -->|Store/Retrieve<br/>Data| DB
    DB -->|Persistent<br/>Data| System
    System -->|Notification<br/>Email| Email
    Email -->|Delivery<br/>Confirmation| System
    
    style User fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style System fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style DB fill:#FF7043,stroke:#E64A19,stroke-width:2px,color:#fff
    style Email fill:#AB47BC,stroke:#6A1B9A,stroke-width:2px,color:#fff
```

### Level 1: Main Processes

```mermaid
graph TB
    User["👤 User"]
    
    subgraph "Chronify System"
        P1["1.0<br/>User Authentication<br/>& Authorization"]
        P2["2.0<br/>Profile Management"]
        P3["3.0<br/>Course Management"]
        P4["4.0<br/>Schedule Management"]
        P5["5.0<br/>Note Management"]
        P6["6.0<br/>Reminder Management"]
        P7["7.0<br/>Notification Engine"]
    end
    
    DB["🗄️ Database<br/>D1: Users<br/>D2: Courses<br/>D3: Schedules<br/>D4: Notes<br/>D5: Reminders"]
    Email["📧 Email Service"]
    Clock["⏰ System Clock"]
    
    User -->|Credentials| P1
    P1 -->|Session Token| User
    P1 -->|Validate User| DB
    
    User -->|Profile Data| P2
    P2 -->|Profile Info| User
    P2 -->|Read/Write| DB
    
    User -->|Course Info| P3
    P3 -->|Course List| User
    P3 -->|CRUD| DB
    
    User -->|Schedule Events| P4
    P4 -->|Schedule Data| User
    P4 -->|CRUD| DB
    
    User -->|Note Content| P5
    P5 -->|Notes| User
    P5 -->|CRUD| DB
    
    User -->|Reminder Request| P6
    P6 -->|Reminder Status| User
    P6 -->|CRUD| DB
    
    Clock -->|Time Check| P7
    P7 -->|Query| DB
    DB -->|Due Reminders| P7
    P7 -->|Notification| User
    P7 -->|Send Email| Email
    
    style P1 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P2 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P3 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P4 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P5 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P6 fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P7 fill:#FF7043,stroke:#E64A19,stroke-width:2px,color:#fff
    style User fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style DB fill:#AB47BC,stroke:#6A1B9A,stroke-width:2px,color:#fff
    style Email fill:#FFB300,stroke:#FF8F00,stroke-width:2px,color:#fff
    style Clock fill:#29B6F6,stroke:#0277BD,stroke-width:2px,color:#fff
```

### Level 1: Detailed Process - Schedule Management (P4)

```mermaid
graph LR
    User["👤 User"]
    
    subgraph "Schedule Management Process"
        P4A["4.1<br/>Receive Schedule<br/>Request"]
        P4B["4.2<br/>Validate<br/>Input Data"]
        P4C["4.3<br/>Process<br/>Schedule"]
        P4D["4.4<br/>Generate<br/>Response"]
    end
    
    DB["🗄️ Database<br/>D3: Schedules"]
    Reminder["🔔 Reminder<br/>System"]
    
    User -->|Schedule Data| P4A
    P4A -->|Request Data| P4B
    P4B -->|Valid Data| P4C
    P4B -->|Invalid Data| P4D
    P4C -->|CRUD Operations| DB
    DB -->|Stored Data| P4C
    P4C -->|Event Trigger| Reminder
    P4C -->|Process Result| P4D
    P4D -->|Response| User
    
    style P4A fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P4B fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P4C fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style P4D fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style User fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style DB fill:#AB47BC,stroke:#6A1B9A,stroke-width:2px,color:#fff
    style Reminder fill:#FF7043,stroke:#E64A19,stroke-width:2px,color:#fff
```

### Data Storage Definition

| Data Store ID | Name | Description | Key Attributes |
|---|---|---|---|
| D1 | Users | User account and profile information | id, username, password, profile_data |
| D2 | Courses | Course schedule information | id, user_id, course_name, time, location |
| D3 | Schedules | Daily schedule events | id, user_id, event, date, time, priority |
| D4 | Notes | User notes and notes content | id, user_id, title, content, timestamps |
| D5 | Reminders | Reminder records | id, user_id, schedule_id, reminder_time |

---

## Architecture Overview

### Layered Architecture

```mermaid
graph TB
    subgraph "Presentation Layer"
        Web["🌐 Web Client"]
        Mobile["📱 Mobile Client"]
    end
    
    subgraph "API Layer"
        AC["AuthController"]
        UC["UserController"]
        SC["ScheduleController"]
        CC["CourseController"]
        NC["NoteController"]
        RC["ReminderController"]
    end
    
    subgraph "Business Logic Layer"
        US["UserService"]
        SS["ScheduleService"]
        CS["CourseService"]
        NS["NoteService"]
        RS["ReminderService"]
    end
    
    subgraph "Data Access Layer"
        UM["UserMapper"]
        SM["ScheduleMapper"]
        CM["CourseMapper"]
        NM["NoteMapper"]
        RM["ReminderMapper"]
    end
    
    subgraph "Infrastructure Layer"
        DB["🗄️ MySQL Database"]
        Cache["💾 Cache"]
        Logger["📝 Logger"]
    end
    
    Web --> AC
    Mobile --> AC
    Web --> UC
    Mobile --> UC
    
    AC -.->|HTTP| AC
    UC --> US
    SC --> SS
    CC --> CS
    NC --> NS
    RC --> RS
    
    US --> UM
    SS --> SM
    CS --> CM
    NS --> NM
    RS --> RM
    
    UM --> DB
    SM --> DB
    CM --> DB
    NM --> DB
    RM --> DB
    
    AC -.->|logs| Logger
    US -.->|logs| Logger
    SS -.->|logs| Logger
    
    US -.->|cache| Cache
    SS -.->|cache| Cache
    CS -.->|cache| Cache
    
    style Web fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style Mobile fill:#7CB342,stroke:#558B2F,stroke-width:2px,color:#fff
    style AC fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style UC fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style SC fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style CC fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style NC fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style RC fill:#4A90E2,stroke:#2E5C8A,stroke-width:2px,color:#fff
    style DB fill:#FF7043,stroke:#E64A19,stroke-width:2px,color:#fff
```

---

## Summary

This document provides a comprehensive software engineering model analysis of the Chronify system, including:

1. **Context Model**: Defines system boundaries and external interactions
2. **Business Process Model**: Shows automated workflows and data transformations
3. **Use Case Diagram**: Illustrates all system use cases and actor interactions
4. **Use Case Descriptions**: Details for each use case including main flow and alternatives
5. **Sequence Diagrams**: Interaction sequences for key system operations
6. **Class Diagram**: Object-oriented design showing classes, attributes, and relationships
7. **Data Flow Diagram**: Complete data movement through the system at different abstraction levels

These models collectively provide a complete technical specification for understanding, implementing, and maintaining the Chronify scheduling and time management application.
