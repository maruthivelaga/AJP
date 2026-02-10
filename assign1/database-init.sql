-- Student Interest Database Initialization Script
-- This script creates the database and provides sample data

-- Create database (if not exists)
CREATE DATABASE IF NOT EXISTS student_interest_db;
USE student_interest_db;

-- Note: Tables will be auto-created by Hibernate with hibernate.hbm2ddl.auto=update
-- This script is just for reference and manual database creation if needed

-- Sample data insertion (run this after starting the application once)

-- Insert sample interests
INSERT INTO interests (title, description, category) VALUES
('Football', 'Team sport played with a ball on a rectangular field', 'Sports'),
('Basketball', 'Indoor team sport with hoops and ball', 'Sports'),
('Cricket', 'Bat and ball game played between two teams', 'Sports'),
('Programming', 'Software development and coding', 'Technology'),
('Web Development', 'Creating websites and web applications', 'Technology'),
('AI/Machine Learning', 'Artificial Intelligence and Machine Learning', 'Technology'),
('Painting', 'Visual art creation using colors and canvas', 'Arts'),
('Music', 'Musical performance and composition', 'Arts'),
('Photography', 'Capturing moments through camera', 'Arts'),
('Reading', 'Books and literature', 'Literature'),
('Writing', 'Creative and technical writing', 'Literature'),
('Gaming', 'Video games and eSports', 'Entertainment'),
('Cooking', 'Culinary arts and food preparation', 'Lifestyle'),
('Traveling', 'Exploring new places and cultures', 'Lifestyle');

-- Insert sample students
INSERT INTO students (name, age, email) VALUES
('John Doe', 20, 'john.doe@example.com'),
('Jane Smith', 22, 'jane.smith@example.com'),
('Mike Johnson', 21, 'mike.johnson@example.com'),
('Emily Brown', 19, 'emily.brown@example.com'),
('David Wilson', 23, 'david.wilson@example.com');

-- Insert student-interest relationships (Many-to-Many)
-- Note: Replace IDs with actual IDs from your database
-- John Doe interested in Football, Programming, and Gaming
INSERT INTO student_interests (student_id, interest_id) VALUES
(1, 1), -- Football
(1, 4), -- Programming
(1, 12); -- Gaming

-- Jane Smith interested in Painting, Music, and Photography
INSERT INTO student_interests (student_id, interest_id) VALUES
(2, 7), -- Painting
(2, 8), -- Music
(2, 9); -- Photography

-- Mike Johnson interested in Basketball, Web Development
INSERT INTO student_interests (student_id, interest_id) VALUES
(3, 2), -- Basketball
(3, 5); -- Web Development

-- Emily Brown interested in Reading, Writing, Cooking
INSERT INTO student_interests (student_id, interest_id) VALUES
(4, 10), -- Reading
(4, 11), -- Writing
(4, 13); -- Cooking

-- David Wilson interested in Cricket, AI/ML, Traveling
INSERT INTO student_interests (student_id, interest_id) VALUES
(5, 3), -- Cricket
(5, 6), -- AI/ML
(5, 14); -- Traveling

-- Queries for testing

-- Get all students with their interests
SELECT s.student_id, s.name, s.email, i.title, i.category
FROM students s
LEFT JOIN student_interests si ON s.student_id = si.student_id
LEFT JOIN interests i ON si.interest_id = i.interest_id
ORDER BY s.student_id;

-- Count students per interest category
SELECT i.category, COUNT(DISTINCT si.student_id) as student_count
FROM interests i
LEFT JOIN student_interests si ON i.interest_id = si.interest_id
GROUP BY i.category
ORDER BY student_count DESC;

-- Get students interested in a specific category
SELECT DISTINCT s.name, s.email
FROM students s
JOIN student_interests si ON s.student_id = si.student_id
JOIN interests i ON si.interest_id = i.interest_id
WHERE i.category = 'Technology';

-- Get all interests for a specific student
SELECT i.title, i.category, i.description
FROM interests i
JOIN student_interests si ON i.interest_id = si.interest_id
JOIN students s ON si.student_id = s.student_id
WHERE s.email = 'john.doe@example.com';
