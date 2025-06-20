INSERT INTO role (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_TEACHER'),
(4, 'ROLE_STUDENT')
ON CONFLICT (id) DO NOTHING;
-- Inserción de usuarios con ID explícito Y CON `enabled`
INSERT INTO users (id, name, dni, password, email, role_id, enabled)
VALUES
-- Profesores
(1, 'Luis Torres', '10000001', '$2a$10$abcdef1', 'luis.torres@example.com', 3, true),
(2, 'María Valdez', '10000002', '$2a$10$abcdef2', 'maria.valdez@example.com', 3, true),
(3, 'José Pérez', '10000003', '$2a$10$abcdef3', 'jose.perez@example.com', 3, true),
(4, 'Laura Gómez', '10000004', '$2a$10$abcdef4', 'laura.gomez@example.com', 3, true),
(5, 'Carlos Ruiz', '10000005', '$2a$10$abcdef5', 'carlos.ruiz@example.com', 3, true),
(6, 'Ana Salas', '10000006', '$2a$10$abcdef6', 'ana.salas@example.com', 3, true),
(7, 'Pedro Chávez', '10000007', '$2a$10$abcdef7', 'pedro.chavez@example.com', 3, true),
(8, 'Lucía Mendoza', '10000008', '$2a$10$abcdef8', 'lucia.mendoza@example.com', 3, true),
(9, 'Raúl Ríos', '10000009', '$2a$10$abcdef9', 'raul.rios@example.com', 3, true),
(10, 'Carmen Díaz', '10000010', '$2a$10$abcdef10', 'carmen.diaz@example.com', 3, true),

-- Estudiantes
(11, 'Andrés León', '10000011', '$2a$10$ghijkl1', 'andres.leon@example.com', 4, true),
(12, 'Sofía Vera', '10000012', '$2a$10$ghijkl2', 'sofia.vera@example.com', 4, true),
(13, 'Mateo López', '10000013', '$2a$10$ghijkl3', 'mateo.lopez@example.com', 4, true),
(14, 'Valeria Paredes', '10000014', '$2a$10$ghijkl4', 'valeria.paredes@example.com', 4, true),
(15, 'Bruno Díaz', '10000015', '$2a$10$ghijkl5', 'bruno.diaz@example.com', 4, true),
(16, 'Camila Aguirre', '10000016', '$2a$10$ghijkl6', 'camila.aguirre@example.com', 4, true),
(17, 'Fernando Torres', '10000017', '$2a$10$ghijkl7', 'fernando.torres@example.com', 4, true),
(18, 'Daniela Rivas', '10000018', '$2a$10$ghijkl8', 'daniela.rivas@example.com', 4, true),
(19, 'Esteban Núñez', '10000019', '$2a$10$ghijkl9', 'esteban.nunez@example.com', 4, true),
(20, 'Paula Calderón', '10000020', '$2a$10$ghijkl10', 'paula.calderon@example.com', 4, true)
    ON CONFLICT (id) DO NOTHING;

-- Insertar en teacher con id fijo
INSERT INTO teacher (id, user_id, contract_date_start, contract_date_end, specialization)
VALUES
    (1, 1, '2024-03-01', '2025-03-01', 'Matemática'),
    (2, 2, '2024-03-01', '2025-03-01', 'Física'),
    (3, 3, '2024-03-01', '2025-03-01', 'Lengua'),
    (4, 4, '2024-03-01', '2025-03-01', 'Historia'),
    (5, 5, '2024-03-01', '2025-03-01', 'Biología'),
    (6, 6, '2024-03-01', '2025-03-01', 'Arte'),
    (7, 7, '2024-03-01', '2025-03-01', 'Química'),
    (8, 8, '2024-03-01', '2025-03-01', 'Educación Física'),
    (9, 9, '2024-03-01', '2025-03-01', 'Informática'),
    (10,10, '2024-03-01', '2025-03-01', 'Filosofía')
    ON CONFLICT (id) DO NOTHING;

-- Insertar en student con id fijo
INSERT INTO student (id, user_id, university_headquarters, intended_major)
VALUES
    (1, 11, 'Filial Chepén', 'Ingeniería de Sistemas'),
    (2, 12, 'Filial Trujillo', 'Medicina'),
    (3, 13, 'Filial Chepén', 'Contabilidad'),
    (4, 14, 'Filial Trujillo', 'Ingeniería Industrial'),
    (5, 15, 'Filial Chepén', 'Derecho'),
    (6, 16, 'Filial Trujillo', 'Psicología'),
    (7, 17, 'Filial Chepén', 'Arquitectura'),
    (8, 18, 'Filial Trujillo', 'Economía'),
    (9, 19, 'Filial Chepén', 'Administración'),
    (10,20, 'Filial Trujillo', 'Enfermería')
    ON CONFLICT (id) DO NOTHING;


