CREATE TABLE enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    grade DECIMAL(5,2) NOT NULL,
    enrolled_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) 
        REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) 
        REFERENCES courses(id),
    CONSTRAINT chk_grade CHECK (grade >= 0.0 AND grade <= 100.0),
    CONSTRAINT unique_student_course UNIQUE (student_id, course_id),
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id)
);
