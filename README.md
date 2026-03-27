Expense Tracker (Group 2)

A simple Android application for tracking daily expenses. This project is developed as part of a Mobile Development group assignment.

Project Overview

Expense Tracker is designed to help users:

Record daily expenses
Categorize transactions
View spending summaries
Manage personal finances

The project follows an incremental development approach, where features are implemented progressively.

Features
Implemented (Initial Version)

Login Screen

Basic validation (username and password must not be empty)

Home Screen

Displays a welcome message
Provides navigation to main features

Expense List

Displays all transactions
Shows "No records yet" when there is no data

Add Expense

Allows users to input expense details (name, amount, category, date)

Navigation

Supports navigation between activities
Includes back navigation functionality
In Progress
Swipe to delete transactions
Sorting and filtering functionality
Statistics page (total expenses, daily expenses, category summary)
Tech Stack

Language: Java
Platform: Android
IDE: Android Studio

Project Structure

activities

UI screens such as Login, Home, Expense List, Add Expense

models

Data models such as Transaction and Category

utils

Helper classes such as DataManager

resources

Layout files, strings, styles
Setup and Installation
Clone the repository:

git clone https://github.com/yudiyuan/group_2.git

Open the project in Android Studio
Sync Gradle
Run the application on an emulator or physical device

Development Notes

This is an early version of the application.

There is currently no database integration. Data is stored locally and temporarily.

The main focus of this version is to establish UI flow and core functionality.

Work is distributed across team members using Trello for task management.

Roadmap
Add persistent storage (SQLite or Room)
Improve user interface and user experience
Implement statistics visualization
Add proper authentication logic
Refactor the project to a more structured architecture such as MVVM
License

This project is for educational purposes only.

Acknowledgements

University of Limerick
Mobile Development Module
