-- =========================
-- ROLES AND USERS
-- =========================

-- Tabla role
CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       dni VARCHAR(8) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       enabled BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       role_id INTEGER NOT NULL,
                       FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Tabla token
CREATE TABLE token (
                       id SERIAL PRIMARY KEY,
                       value TEXT,
                       token_type VARCHAR(25) DEFAULT 'BEARER',
                       is_revoked BOOLEAN NOT NULL,
                       is_expired BOOLEAN NOT NULL,
                       user_id BIGINT,
                       CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Tabla authority
CREATE TABLE authority (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla intermedia role_authorities
CREATE TABLE role_authorities (
                                  role_id INTEGER NOT NULL,
                                  authority_id INTEGER NOT NULL,
                                  PRIMARY KEY (role_id, authority_id),
                                  FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
                                  FOREIGN KEY (authority_id) REFERENCES authority(id) ON DELETE CASCADE
);


-- =========================
-- PERSONAL ENTITIES
-- =========================

CREATE TABLE teacher (
                         id SERIAL PRIMARY KEY,
                         user_id INT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                         contract_date_start DATE,
                         contract_date_end DATE,
                         specialization VARCHAR(100)
);

CREATE TABLE student (
                         id SERIAL PRIMARY KEY,
                         user_id INT UNIQUE REFERENCES users(id) ON DELETE CASCADE,
                         university_headquarters VARCHAR(100),
                         intended_major VARCHAR(100)
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

CREATE TABLE shift (    --turno dia/tarde/noche son 5 dias por turno / seria como el periodo/ciclo
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(20), -- morning, afternoon, evening
                       modality VARCHAR(20), -- online, in-person
                       price NUMERIC(10,2) NOT NULL,
                       semester_id INT REFERENCES semester(id) ON DELETE CASCADE
);


CREATE TABLE course (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100),
                        description TEXT,
);

CREATE TABLE scheduled_course (   --basicamente el horario del curso que se dicta en cierto dia y que profesor lo dicta
                                  id SERIAL PRIMARY KEY,
                                  course_id INT REFERENCES course(id) ON DELETE CASCADE,
                                  shift_id INT REFERENCES shift(id) ON DELETE CASCADE,
                                  teacher_id INT REFERENCES teacher(id) ON DELETE CASCADE,
                                  day_of_week VARCHAR(20), -- Monday, Tuesday, etc.
                                  start_time TIME,
                                  end_time TIME
);

-- =========================
-- AUTOMATIC ENROLLMENT
-- =========================

CREATE TABLE coupon (
                        id SERIAL PRIMARY KEY,
                        code VARCHAR(50) UNIQUE NOT NULL,
                        description TEXT,
                        coupon_type VARCHAR(20) NOT NULL,
                        amount NUMERIC(10,2) NOT NULL,
                        max_uses INT DEFAULT 1,
                        used_count INT DEFAULT 0,
                        valid_from DATE,
                        valid_until DATE,
                        is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE enrollment (
                            id SERIAL PRIMARY KEY,
                            student_id INT REFERENCES student(id) ON DELETE CASCADE,
                            enrollment_status VARCHAR(50),
                            payment_status VARCHAR(50),
                            file_voucher_url TEXT,
                            total_amount NUMERIC(10, 2) NOT NULL,
                            created_by INT REFERENCES student(id) ON DELETE CASCADE,
                            payment_method varchar(50),
                            enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE enrollment_detail (
                                   id SERIAL PRIMARY KEY,
                                   enrollment_id INT REFERENCES enrollment(id) ON DELETE CASCADE,
                                   shift_id INT REFERENCES shift(id) ON DELETE CASCADE,
                                   coupon_id INT REFERENCES coupon(id) ON DELETE CASCADE,
                                   base_price NUMERIC(10, 2) NOT NULL,
                                   applied_discount NUMERIC(10, 2) DEFAULT 0.00,
                                   total NUMERIC(10, 2) NOT NULL,
                                   applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- =========================
-- VIRTUAL CLASSROOM
-- =========================

CREATE TABLE course_student(  --estudiantes que llevan el curso
                               scheduled_course_id INT REFERENCES scheduled_course(id) ON DELETE CASCADE,
                               student_id INT REFERENCES student(id) ON DELETE CASCADE
);

CREATE TABLE course_session (
                                id SERIAL PRIMARY KEY,
                                scheduled_course_id INT REFERENCES scheduled_course(id) ON DELETE CASCADE,
                                title VARCHAR(100),
                                description TEXT,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE resource (
                          id SERIAL PRIMARY KEY,
                          course_session_id INT REFERENCES course_session(id) ON DELETE CASCADE,
                          type VARCHAR(20), -- assignment, document, video
                          title VARCHAR(100),
                          description VARCHAR(500),
                          file_url TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE assignment_resource (
                                     id INT PRIMARY KEY REFERENCES resource(id) ON DELETE CASCADE,
                                     limit_date TIMESTAMP NOT NULL,
                                     allow_late_submission BOOLEAN DEFAULT FALSE,
                                     visibility_start TIMESTAMP
);


CREATE TABLE assignment_submission (
                                       id SERIAL PRIMARY KEY,
                                       assignment_resource_id INT REFERENCES assignment_resource(id) ON DELETE CASCADE,
                                       student_id INT REFERENCES student(id) ON DELETE CASCADE,
                                       file_url TEXT,
                                       submitted_at TIMESTAMP,
                                       grade DECIMAL(5,2)
);

-- ======================================
-- VALUE CONSTRAINTS
-- ======================================

-- Validate time of course
ALTER TABLE scheduled_course
    ADD CONSTRAINT chk_time_range CHECK (start_time < end_time);

