-- Insert sample students
INSERT INTO students (name, email, date_of_birth, created_at) VALUES
('Alice Johnson', 'alice.johnson@example.com', '2001-03-15', NOW()),
('Bob Smith', 'bob.smith@example.com', '2000-07-22', NOW()),
('Charlie Brown', 'charlie.brown@example.com', '2002-01-10', NOW()),
('Diana Prince', 'diana.prince@example.com', '2001-11-05', NOW()),
('Ethan Hunt', 'ethan.hunt@example.com', '2000-09-30', NOW());

-- Insert sample courses
INSERT INTO courses (name, course_code, credits, created_at) VALUES
('Introduction to Computer Science', 'CS101', 3, NOW()),
('Data Structures and Algorithms', 'CS201', 4, NOW()),
('Database Systems', 'CS301', 3, NOW()),
('Web Development', 'CS202', 3, NOW());

-- Insert sample enrollments
INSERT INTO enrollments (student_id, course_id, grade, enrolled_at) VALUES
(1, 1, 92.5, NOW()),
(1, 2, 88.0, NOW()),
(1, 3, 95.0, NOW()),
(2, 1, 78.5, NOW()),
(2, 2, 82.0, NOW()),
(3, 1, 90.0, NOW()),
(3, 3, 87.5, NOW()),
(4, 2, 91.0, NOW()),
(4, 4, 89.5, NOW()),
(5, 1, 85.0, NOW());
