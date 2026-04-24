# Expense Tracker – Group 2

Expense Tracker is a native Android application developed as a group project for Mobile Development.  
The app helps users record, organise, and review personal expenses through a clean mobile interface.

This project was built using **Java** in **Android Studio** and focuses on practical mobile app development concepts such as UI design, local data storage, activity navigation, and CRUD operations.

---

## Team

**Group 2**

---

## Features

### User Account System
- User registration
- User login
- Local credential storage using SharedPreferences
- Password validation:
  - Minimum 8 characters
  - At least one uppercase letter
  - At least one number
  - At least one special character

### Expense Management
- Add new expenses
- Edit existing expenses
- Delete expenses
- Swipe to delete items
- View all expense records

### Smart Input UI
- Category selection using Spinner dropdown
- Date input with calendar picker
- Form validation for required fields

### Data Management
- Local storage for expenses
- Data remains available after restarting app

### Expense Organisation
- Sort expenses
- Filter expenses by category

### Statistics
- Total expenses overview
- Daily spending summary
- Category spending summary

### Navigation & UX
- Multi-screen navigation
- Back button support
- Improved layouts and responsive UI

---

## Technologies Used

- Java
- Android Studio
- XML Layout Design
- SharedPreferences
- RecyclerView
- Spinner
- Intent Navigation

---

## Project Structure

```text
app/
 ├── activities/
 │   ├── MainActivity
 │   ├── LoginActivity
 │   ├── RegisterActivity
 │   ├── HomeActivity
 │   ├── AddExpenseActivity
 │   ├── EditExpenseActivity
 │   └── ExpenseListActivity
 │
 ├── model/
 ├── utils/
 └── res/layout/

Screens Included
Welcome Screen
Login Screen
Register Screen
Home Dashboard
Add Expense Screen
Expense List Screen
Edit Expense Screen
Statistics Screen


