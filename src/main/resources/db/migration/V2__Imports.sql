
INSERT INTO semester (name, start_date, end_date)
VALUES
('2025-1', '2025-01-01', '2025-02-21'),
('2025-2', '2025-03-01', '2025-12-31');


-- Insert roles si no existen
INSERT INTO role (name) VALUES
                            ('ROLE_USER'),
                            ('ROLE_ADMIN')
    ON CONFLICT (name) DO NOTHING;

-- Insert authorities si no existen
INSERT INTO authority (name) VALUES
                                 ('USER_READ'),
                                 ('USER_WRITE'),
                                 ('USER_DELETE'),
                                 ('USER_UPDATE')
    ON CONFLICT (name) DO NOTHING;

-- Insert relación ROLE_USER - USER_READ
INSERT INTO role_authorities (role_id, authority_id)
SELECT r.id, a.id
FROM role r, authority a
WHERE r.name = 'ROLE_USER' AND a.name = 'USER_READ'
    ON CONFLICT DO NOTHING;

-- Insert relaciones ROLE_ADMIN - ALL AUTHORITIES
INSERT INTO role_authorities (role_id, authority_id)
SELECT r.id, a.id
FROM role r, authority a
WHERE r.name = 'ROLE_ADMIN' AND a.name IN (
                                           'USER_READ', 'USER_WRITE', 'USER_DELETE', 'USER_UPDATE'
    )
    ON CONFLICT DO NOTHING;



---