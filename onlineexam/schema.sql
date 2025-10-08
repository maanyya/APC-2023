CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    password VARCHAR2(50) NOT NULL
);

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER users_trigger
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
   SELECT users_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

INSERT INTO users (username, password) VALUES ('testuser', 'testpass');

CREATE TABLE questions (
    id NUMBER PRIMARY KEY,
    text VARCHAR2(255) NOT NULL,
    optionA VARCHAR2(100) NOT NULL,
    optionB VARCHAR2(100) NOT NULL,
    optionC VARCHAR2(100) NOT NULL,
    optionD VARCHAR2(100) NOT NULL,
    correctAnswer VARCHAR2(10) NOT NULL
);

CREATE SEQUENCE questions_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER questions_trigger
BEFORE INSERT ON questions
FOR EACH ROW
BEGIN
   SELECT questions_seq.NEXTVAL INTO :new.id FROM dual;
END;
/


INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What is 2+2?', '3', '4', '5', '6', 'B');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which programming language is platform independent?', 'C', 'Java', 'Python', 'JavaScript', 'B');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What does HTML stand for?', 'Hyper Text Markup Language', 'High Tech Modern Language', 'Home Tool Markup Language', 'Hyperlink and Text Markup Language', 'A');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which data structure uses LIFO principle?', 'Queue', 'Stack', 'Array', 'Tree', 'B');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What is the time complexity of binary search?', 'O(n)', 'O(log n)', 'O(n²)', 'O(1)', 'B');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which HTTP method is used to retrieve data?', 'POST', 'PUT', 'GET', 'DELETE', 'C');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What does SQL stand for?', 'Structured Query Language', 'Simple Query Language', 'Sequential Query Language', 'Standard Query Language', 'A');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which of these is NOT a JavaScript framework?', 'React', 'Angular', 'Vue.js', 'Django', 'D');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What is the default port for HTTP?', '443', '21', '80', '8080', 'C');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which keyword is used to create a class in Java?', 'class', 'Class', 'new', 'create', 'A');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What is 10 * 3 + 2?', '32', '35', '30', '25', 'A');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which of these is a NoSQL database?', 'MySQL', 'PostgreSQL', 'MongoDB', 'Oracle', 'C');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What does CSS stand for?', 'Computer Style Sheets', 'Cascading Style Sheets', 'Creative Style Sheets', 'Colorful Style Sheets', 'B');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('Which sorting algorithm has the best average case time complexity?', 'Bubble Sort', 'Selection Sort', 'Quick Sort', 'Insertion Sort', 'C');

INSERT INTO questions (text, optionA, optionB, optionC, optionD, correctAnswer)
VALUES ('What is the capital of India?', 'Mumbai', 'New Delhi', 'Kolkata', 'Chennai', 'B');


CREATE TABLE scores (
    id NUMBER PRIMARY KEY,
    username VARCHAR2(50) NOT NULL,
    score NUMBER NOT NULL,
    total_questions NUMBER NOT NULL,
    percentage NUMBER(5,2) NOT NULL,
    exam_date DATE DEFAULT SYSDATE
);

CREATE SEQUENCE scores_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER scores_trigger
BEFORE INSERT ON scores
FOR EACH ROW
BEGIN
   SELECT scores_seq.NEXTVAL INTO :new.id FROM dual;
END;
/

COMMIT;
