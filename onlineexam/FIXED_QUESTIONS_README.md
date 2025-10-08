# Online Exam System - Fixed Question Set

## Changes Made

### 1. Fixed Question Set (15 Questions)
- **No randomization**: Questions are always shown in the same order
- **No repetition**: Same 15 questions every test
- **Fixed order**: Questions 1-15 from the database in ID order

### 2. Leaderboard Improvements
- **Larger container**: Increased max-width from 700px to 1000px
- **Better visibility**: Changed gradient text to solid dark color with text shadow
- **Improved readability**: Enhanced contrast for better visibility

### 3. Database Questions
The system now uses exactly 15 predetermined questions:

1. What is 2+2?
2. Which programming language is platform independent?
3. What does HTML stand for?
4. Which data structure uses LIFO principle?
5. What is the time complexity of binary search?
6. Which HTTP method is used to retrieve data?
7. What does SQL stand for?
8. Which of these is NOT a JavaScript framework?
9. What is the default port for HTTP?
10. Which keyword is used to create a class in Java?
11. What is 10 * 3 + 2?
12. Which of these is a NoSQL database?
13. What does CSS stand for?
14. Which sorting algorithm has the best average case time complexity?
15. What is the capital of India?

### 4. Test Timer (Future Enhancement)
- Timer should run for the entire test duration
- Not per individual question
- Test start time stored in session

## Usage
1. Every user gets the same 15 questions in the same order
2. No questions are repeated within a test
3. Leaderboard shows results with improved visibility
4. Questions are stored in database and loaded consistently

## Database Setup
Run the `schema.sql` file to create tables and insert the fixed 15 questions.