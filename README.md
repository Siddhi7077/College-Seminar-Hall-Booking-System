#  Seminar Hall Booking System

A web-based **Seminar Hall Booking System** developed to simplify the management of seminar halls, events, registrations, and bookings within a college environment.

The system provides separate functionality for **Admin, Teacher, and Student** users, allowing them to perform tasks according to their roles.

---

##  About the Project

The Seminar Hall Booking System is designed to make the process of managing seminar halls and college events more organized and efficient.

It helps in:

- Managing seminar halls and their capacities
- Managing teachers
- Creating and managing events
- Checking auditorium availability
- Registering students for events
- Booking seats
- Generating and printing event tickets
- Reducing scheduling conflicts

The system also provides different dashboards and functionalities for Admin, Teacher, and Student users.

---

##  User Roles

###  Admin

The Admin is responsible for managing the system.

Admin can:

- Login to the system
- Add teachers
- View teachers
- Edit teacher details
- Delete teachers
- Add auditoriums
- View auditoriums
- Edit auditorium details
- Delete auditoriums
- View events

---

### Teacher

Teachers can manage events and seminar hall bookings.

Teacher can:

- Login to the system
- Add events
- View events
- Edit events
- Delete events
- Book an auditorium for an event
- Check the auditorium schedule before booking

---

###  Student

Students can interact with available events and register for them.

Student can:

- Sign up
- Login
- View available/upcoming events
- View event details
- Register for an event
- Book a seat
- Generate and print an event ticket

---

## Key Features

-  User login and authentication
-  Role-based access for Admin, Teacher and Student
-  Auditorium management
-  Teacher management
-  Event management
-  Event and auditorium viewing
-  Event registration
-  Seat booking
-  Ticket generation and printing
-  Auditorium schedule checking
-  Event posters and auditorium images

---

## System Design

The project was designed using several UML diagrams to represent the system structure and workflow:

- Entity Relationship (ER) Diagram
- Use Case Diagram
- Class Diagram
- Activity Diagram
- Sequence Diagram

The ER diagram represents entities such as **Admin, Teacher, Student, Event, and Auditorium**, along with their relationships. The use-case and activity diagrams describe the actions performed by each user role. :contentReference[oaicite:1]{index=1}

---

## Application Workflow

### General Flow

```text
                    Seminar Hall Booking System
                              │
             ┌────────────────┼────────────────┐
             │                │                │
           Admin            Teacher          Student
             │                │                │
       Manage Teachers    Manage Events    View Events
       Manage Auditoriums      │          Register
       View Events             │          Book Seat
             │           Book Auditorium    │
             │                │          Print Ticket
             └────────────────┼────────────────┘
                              │
                         MySQL Database
