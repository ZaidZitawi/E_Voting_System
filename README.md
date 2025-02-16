# E-Voting System Using Blockchain Technology

## Introduction

This project is an **E-Voting System** that leverages **Blockchain Technology** to ensure secure, transparent, and immutable voting processes. The system is designed to simulate university elections, including clubs, student councils, and other types of elections. It consists of a **mobile application** for general users and candidates, and a **web platform** for administrators.

---

## Features

### General User Features

- **User Registration & Authentication**
  - Sign up using university email (`{StudentUniversityNumber}@student.birzeit.edu`).
  - Email verification via a code sent to the university email.
  - Single account per user enforcement.
  - Secure login with verified credentials.

- **Election Listing**
  - View ongoing and upcoming elections.
  - Elections displayed with details: title, type, dates, and description.
  - Ability to view election details on a separate page.

- **Search & Filter Elections**
  - Search elections by type (club, council, etc.).
  - Elections filtered based on user eligibility (department, college).
  - View election details even if ineligible to vote.

- **Profile Page**
  - View and update personal details (name, email, department).
  - Update profile picture and bio.
  - View participation history in elections.

- **Election Interaction**
  - View candidate posts in any election.
  - Like and comment on posts if participating in the election.

### Candidate Features

- **Campaign Posts**
  - Create and publish multiple campaign posts (text and media).
  - Posts visible to all users.
  - Interactions (likes/comments) from eligible voters.

- **Engagement Tracking**
  - View likes and comments on campaign posts.
  - Monitor audience engagement.

- **Live Election Results**
  - Access real-time voting counts.
  - Compare performance with other candidates.

- **Profile Management**
  - Set up candidate profile with campaign details.
  - Update background, goals, and policies.

### Admin Features

- **Admin Login**
  - Secure authentication for administrators.
  - Access to admin web platform.

- **Election Management**
  - Create new elections with eligibility criteria.
  - Update or delete existing elections.
  - Immediate reflection of changes in the mobile app.

- **Candidate Management**
  - Assign users as candidates.
  - Link candidates to specific elections.
  - Disqualify candidates if necessary.

- **Moderation & Monitoring**
  - Monitor candidate posts and user interactions.
  - Moderate content to ensure fairness.
  - View voting results and election activities.

- **Notifications**
  - Send notifications to users.
  - Inform about new elections and updates.

### Blockchain Integration

- **Vote Storage & Security**
  - Securely store votes on the blockchain.
  - Immutable transactions ensure integrity.
  - Publicly verifiable votes with private voter identity.

- **Election Results on Blockchain**
  - Store final results on the blockchain.
  - Prevent modification of election outcomes.
  - Ensure transparency and auditability.

---

## Technologies Used

- **Backend**: Spring Boot
- **Frontend (Mobile App)**: Flutter
- **Frontend (Admin Web Platform)**: React.js
- **Database**: Postgresql
- **Blockchain**: Amoy testnet "Polygon": Web3j & web3.js libraries
- **Authentication**: JWT Tokens
- **Security**: Spring Security
- **Others**:
  - Hibernate (ORM)
  - Maven (Build Tool)
  - Git & GitHub (Version Control)
  - Java Mail Sender
  - Firebase push Notifications service
  - Spring local hosting for images and media
  -  MapStruct for handling object mapping
  -   
  

---

## Architecture

The system follows a **Client-Server Architecture** with separate frontends for users and admins, and a backend API server.

- **Mobile Application**: Used by students and candidates for interaction with the system.
- **Admin Web Platform**: Used by administrators to manage the system.
- **API Server**: Handles all business logic and database interactions.
- **Blockchain Network**: Records votes and election results for integrity.


## Installation and Setup

### Prerequisites

- **Java 17** or higher
- **Node.js** (for frontend applications)
- **Postgresql** Database
- **Maven** Build Tool


1. **Clone the repository**

   ```bash
   git clone https://github.com/ZaidZitawi/E_Voting_System.git
