
INSERT INTO semester (name, start_date, end_date)
VALUES
('2025-1', '2025-01-01', '2025-02-21'),
('2025-2', '2025-03-01', '2025-12-31');


-- Insert roles si no existen
INSERT INTO role (name) VALUES
                            ('ROLE_USER'),
                            ('ROLE_ADMIN'),
                            ('ROLE_TEACHER'),
                            ('ROLE_STUDENT')
    ON CONFLICT (name) DO NOTHING;

-- Insert authorities si no existen
INSERT INTO authority (name) VALUES
                                 ('READ_TEACHER'),
                                 ('WRITE_TEACHER'),

                                 ('READ_STUDENT'),
                                 ('USER_STUDENT')
    ON CONFLICT (name) DO NOTHING;





---