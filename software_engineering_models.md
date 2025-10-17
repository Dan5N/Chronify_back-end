# Software Engineering Structured and Behavioral Models Analysis

## 1. Structural Models

### 1.1 Static Models vs Dynamic Models

#### Static Models
Static models show the organization of the system design. They represent the system components and their relationships in a static state.

#### Dynamic Models
Dynamic models show the organization of the system when it is executing. They may differ significantly from static models, representing the actual runtime structure (e.g., collections of interacting threads).

### 1.2 Key Components

#### Class Diagram
Class diagrams are used when developing an object-oriented system model to show the classes in a system and the associations between these classes.

**Core Components:**
- **Object Class**: A generic definition of objects of the same type in the system
- **Association**: Links between classes showing relationships with multiplicity

### 1.3 Building Class Diagram (Sim City Example)

#### Step 1: Identify Objects
From the game description, extract nouns: Player, City, Budget, Blank Map, Building, etc.

#### Step 2: Identify Relationships
From the description, extract verbs: "given", "extended", "added to"

#### Step 3: Draw Basic Class Diagram
Use rectangles for classes and lines for relationships

#### Step 4: Determine Cardinality Ratios
- Player ↔ City: 1:1 relationship
- City ↔ Building: 1:1..* relationship

### 1.4 Class Structure

UML class rectangles are divided into three parts:
```
+------------------+
|    Class Name     |
+------------------+
|   Attributes      |
| (name, type)     |
+------------------+
|   Operations      |
|   (methods)       |
+------------------+
```

### 1.5 Important Relationship Types

#### Generalization
**Purpose**: Managing complexity by abstracting common attributes and operations into superclasses.
**UML Notation**: Hollow arrow pointing to superclass

#### Aggregation
**Purpose**: Represents "whole-part" relationships
**UML Notation**: Diamond symbol on the "whole" end of association

## 2. Behavioral Models

### 2.1 Definition
Behavioral models show what happens or what is supposed to happen when a system responds to stimuli from its environment.

### 2.2 Types of Stimuli

#### Data-Driven Stimuli
Triggered when data becomes available for processing
*Example: Billing system receiving call information

#### Event-Driven Stimuli
Triggered by specific events, with or without associated data
*Example: Telephone exchange system responding to "handset activated" event

### 2.3 System Types

| System Type | Drive Type | Characteristics | Example |
|-------------|-----------|----------------|----------|
| Data Processing System | Data-driven | Focus on data input, processing, output | Mobile billing system |
| Real-time System | Event-driven | Focus on rapid event response | Fixed-line telephone exchange |

### 2.4 Behavioral Modeling Approaches

#### Data-Driven Modeling
**Tool**: Data Flow Diagrams (DFD)
**Advantages**: Easy to understand, track and record data flow
**Components**:
- Circles/rounded rectangles: Processing activities
- Rectangles: Data objects
- Arrows: Data flow between activities and objects

#### Sequence Modeling
**Tool**: UML Sequence Diagrams
**Characteristics**: Time-ordered object interactions, focus on system objects rather than activities

## 3. System Modeling in Software Engineering

### 3.1 System Modeling Definition

System modeling is the process of building abstract models of systems, where each model represents the system from a particular perspective using graphical symbols based on UML diagram types.

### 3.2 Modeling Object Types

#### Existing Systems
- Purpose: Understand existing system functionality
- Focus: Stakeholder discussions about system improvements

#### New Systems
- Purpose: Explain proposed requirements to stakeholders
- Focus: Design discussion and implementation documentation

### 3.3 Model Perspectives

| Perspective | Purpose | Example |
|------------|---------|---------|
| External (Context) | Show system boundaries and environment | System dependencies |
| Interaction | Show interactions (user-system, system-system) | Use cases, sequence diagrams |
| Structural | Show system components and data structures | Class diagrams |
| Behavioral | Show system responses to events | State charts, activity diagrams |

## 4. Context Models

### 4.1 Core Objective
Define system boundaries early in the specification process. Determine "what's inside the system" vs "what's in the environment" through stakeholder collaboration.

### 4.2 Key Decision Points
- Should business process automation be included?
- Should new functionality address overlaps with existing systems?

## 5. Business Process Models

### 5.1 Purpose
Used with simple context models to describe automated business processes that the system will handle.

### 5.2 Difference from Context Models
Context models show external automated systems but don't specify relationship types. Business process models consider these relationships as they affect system requirements and design.

## 6. Interaction Models

### 6.1 Three Types of Interactions

#### User Interactions
User input and system output
*Example: Receptionist querying patient information*

#### System-to-System Interactions
Planned system with other environmental systems
*Example: Mentcare system transmitting data to patient record system*

#### Component Interactions
Software system internal component communications
*Example: UI component communicating with database component*

### 6.2 Modeling Significance
- Help identify user requirements
- Highlight potential communication issues
- Assess if proposed architecture meets performance and reliability requirements

### 6.3 Interaction Modeling Methods

#### Use Case Modeling
Models interactions between the system and external agents (users or other systems).

#### Sequence Diagrams
Model interactions between system components including external agents, showing interaction steps for specific use cases in time order.

**Structure:**
- Top: List of participants
- Life lines: Vertical dashed lines below each participant
- Interaction arrows: Annotated arrows showing object calls, parameters, and return values

## 7. Event-Driven Modeling

### 7.1 Core Logic
Shows system responses to internal/external events based on "finite system states" and "event-triggered state transitions".

### 7.2 UML Support
Implemented through state charts (state machines) showing system states and triggering events.

### 7.3 State Model Optimization
Use "superstates" to encapsulate multiple independent states to manage the problem of rapidly growing state numbers.

## 8. Model-Driven Architecture (MDA)

### 8.1 Core Concept
MDA is a model-centric software design and implementation approach using UML model subsets to describe systems and build models at different abstraction levels.

### 8.2 Three Model Levels

#### Computation Independent Model (CIM)
- Also known as domain models
- Describes key domain abstractions used in the system

#### Platform Independent Model (PIM)
- Simulates system operation without specific implementation details

#### Platform Specific Model (PSM)
- Modified platform-independent models for each application

### 8.3 MDA Model Transformation
**Process:** CIM → PIM → PSM → Executable Code
**Method:** Transformations automatically defined and applied through "transformers"