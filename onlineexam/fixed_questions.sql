-- Insert exactly 15 fixed questions (no repetition, same every time)
DELETE FROM questions; -- Clear existing questions to ensure exactly 15

INSERT INTO questions (ID, TEXT, OPTIONA, OPTIONB, OPTIONC, OPTIOND, CORRECTANSWER) VALUES 
(questions_seq.NEXTVAL, 'What is the capital of France?', 'Paris', 'London', 'Berlin', 'Madrid', 'A'),
(questions_seq.NEXTVAL, 'Which planet is known as the Red Planet?', 'Mars', 'Venus', 'Jupiter', 'Saturn', 'A'),
(questions_seq.NEXTVAL, 'What is 2 + 2?', '3', '4', '5', '6', 'B'),
(questions_seq.NEXTVAL, 'Who wrote Romeo and Juliet?', 'Shakespeare', 'Dickens', 'Austen', 'Wilde', 'A'),
(questions_seq.NEXTVAL, 'What is the largest ocean on Earth?', 'Pacific', 'Atlantic', 'Indian', 'Arctic', 'A'),
(questions_seq.NEXTVAL, 'What year did World War II end?', '1943', '1944', '1945', '1946', 'C'),
(questions_seq.NEXTVAL, 'What is the chemical symbol for gold?', 'Au', 'Ag', 'Fe', 'Cu', 'A'),
(questions_seq.NEXTVAL, 'How many continents are there?', '5', '6', '7', '8', 'C'),
(questions_seq.NEXTVAL, 'What is the smallest prime number?', '1', '2', '3', '0', 'B'),
(questions_seq.NEXTVAL, 'Who painted the Mona Lisa?', 'Da Vinci', 'Picasso', 'Van Gogh', 'Monet', 'A'),
(questions_seq.NEXTVAL, 'What is the speed of light approximately?', '300,000 km/s', '150,000 km/s', '450,000 km/s', '600,000 km/s', 'A'),
(questions_seq.NEXTVAL, 'What is the largest mammal?', 'Blue Whale', 'Elephant', 'Giraffe', 'Hippo', 'A'),
(questions_seq.NEXTVAL, 'What language is spoken in Brazil?', 'Portuguese', 'Spanish', 'English', 'French', 'A'),
(questions_seq.NEXTVAL, 'What is the currency of Japan?', 'Yen', 'Won', 'Yuan', 'Rupee', 'A'),
(questions_seq.NEXTVAL, 'How many sides does a hexagon have?', '6', '5', '7', '8', 'A');

COMMIT;