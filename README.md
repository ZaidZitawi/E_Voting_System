# E-Voting System Usin Blockchain

## Overview

The E-Voting System is a secure and transparent voting platform designed specifically for university students. It allows students to participate in various elections such as club elections and student council elections through a mobile application. The system ensures the integrity and anonymity of votes by leveraging blockchain technology.

## Features

### General User Features
1. **User Registration & Authentication**:
   - Sign up using university email.
   - Email verification.
   - One account per user.

2. **Election Listing**:
   - View upcoming and ongoing elections.
   - Detailed election information displayed.

3. **Search & Filter Elections**:
   - Search elections by type (e.g., club, council).
   - Filter based on user eligibility.

4. **Profile Management**:
   - View and update user details.
   - Track election participation.

5. **Election Interaction**:
   - Like and comment on candidates' posts.

### Candidate Features
1. **Campaign Posts**:
   - Create and publish campaign posts.
   - View interactions (likes and comments) on posts.

2. **Live Election Results**:
   - View real-time voting counts.

3. **Profile Management**:
   - Manage campaign-related profile information.

### Admin Features
1. **Admin Login**:
   - Secure access to the admin platform.

2. **Election Management**:
   - Create, update, and delete elections.
   - Manage candidate assignments.

3. **Moderation & Monitoring**:
   - Monitor election activities and content moderation.

4. **Notifications**:
   - Send notifications to users about elections and updates.

### Blockchain Integration
1. **Secure Vote Storage**:
   - Immutable transactions for votes.
   - Private voter identity.

2. **Immutable Election Results**:
   - Store final results on the blockchain to prevent tampering.

## Database Design

The database design consists of several tables to store relevant data, including:

- **Users**: Contains user details (name, email, role, etc.).
- **Elections**: Stores election details (title, description, start/end dates, eligibility criteria).
- **Candidates**: Links candidates to elections and their profiles.
- **Posts**: Records candidate campaign posts.
- **Votes**: Captures votes associated with elections.
- **Comments**: Stores user comments on candidate posts.
- **Likes**: Tracks likes on posts.
- **Enrollment**: to track user's enrollments in different elections cohorts.

## Tech Stack

- **Backend**: Spring Boot
- **Frontend**: 
  - Mobile Application: Flutter
  - Admin Web Application: React.js
- **Database**: PostgreSQL
- **Blockchain**: Ethereum
- **APIs**: RESTful APIs for communication between frontend and backend.
- **Version Control**: Git

## Getting Started

To run the application locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/ZaidZitawi/E_Voting_System
