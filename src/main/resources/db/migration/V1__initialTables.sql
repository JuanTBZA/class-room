-- =========================
-- ROLES AND USERS
-- =========================

CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE user_role (
    user_id INT REFERENCES user(id) ON DELETE CASCADE,
    role_id INT REFERENCES role(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- =========================
-- PERSONAL ENTITIES
-- =========================

CREATE TABLE teacher (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    user_id INT UNIQUE REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE student (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    headquarters VARCHAR(100),
    intended_major VARCHAR(100),
    user_id INT UNIQUE REFERENCES user(id) ON DELETE CASCADE
);

-- =========================
-- ACADEMIC ORGANIZATION
-- =========================

CREATE TABLE semester (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    start_date DATE,
    end_date DATE
);

CREATE TABLE shift (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20), -- morning, afternoon, evening
    modality VARCHAR(20), -- online, in-person
    semester_id INT REFERENCES semester(id) ON DELETE CASCADE
);

CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    teacher_id INT REFERENCES teacher(id) ON DELETE SET NULL
);

CREATE TABLE course_shift (
    id SERIAL PRIMARY KEY,
    course_id INT REFERENCES course(id) ON DELETE CASCADE,
    shift_id INT REFERENCES shift(id) ON DELETE CASCADE,
    day_of_week VARCHAR(20), -- Monday, Tuesday, etc.
    start_time TIME,
    end_time TIME
);

-- =========================
-- AUTOMATIC ENROLLMENT
-- =========================

CREATE TABLE enrollment (
    id SERIAL PRIMARY KEY,
    student_id INT REFERENCES student(id) ON DELETE CASCADE,
    semester_id INT REFERENCES semester(id) ON DELETE CASCADE,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE enrollment_detail (
    id SERIAL PRIMARY KEY,
    enrollment_id INT REFERENCES enrollment(id) ON DELETE CASCADE,
    shift_id INT REFERENCES shift(id) ON DELETE CASCADE
);

-- =========================
-- VIRTUAL CLASSROOM
-- =========================

CREATE TABLE course_session (
    id SERIAL PRIMARY KEY,
    course_shift_id INT REFERENCES course_shift(id) ON DELETE CASCADE,
    title VARCHAR(100),
    description TEXT,
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resource (
    id SERIAL PRIMARY KEY,
    course_session_id INT REFERENCES course_session(id) ON DELETE CASCADE,
    type VARCHAR(20), -- assignment, document, video
    title VARCHAR(100),
    description VARCHAR(500),
    file_url TEXT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE assignment_submission (
    id SERIAL PRIMARY KEY,
    resource_id INT REFERENCES resource(id) ON DELETE CASCADE,
    student_id INT REFERENCES student(id) ON DELETE CASCADE,
    file_url TEXT,
    submitted_at TIMESTAMP,
    grade DECIMAL(5,2)
);

-- ======================================
-- INDEXES FOR PERFORMANCE
-- ======================================

--CREATE INDEX idx_user_username ON user(username);
CREATE INDEX idx_enrollment_student_id ON enrollment(student_id);
CREATE INDEX idx_assignment_resource_id ON assignment_submission(resource_id);

-- ======================================
-- VALUE CONSTRAINTS
-- ======================================

-- Allowed values for shift name and modality
ALTER TABLE shift
    ADD CONSTRAINT chk_shift_name CHECK (name IN ('morning', 'afternoon', 'evening'));

ALTER TABLE shift
    ADD CONSTRAINT chk_modality CHECK (modality IN ('online', 'in-person'));

ALTER TABLE student
    ADD CONSTRAINT chk_headquarters CHECK (headquarters IN ('valle', 'trujillo', "huamachuco"));

-- Allowed resource types
ALTER TABLE resource
    ADD CONSTRAINT chk_resource_type CHECK (type IN ('assignment', 'document', 'video'));

-- ======================================
-- AVOID DUPLICATE ENROLLMENT PER SEMESTER
-- ======================================

ALTER TABLE enrollment
    ADD CONSTRAINT unq_student_semester UNIQUE(student_id, semester_id);
