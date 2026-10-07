INSERT INTO usuario (username, password, nombre_completo, rol) VALUES
('medico1', '$2a$10$ghxOZJ3OXJUsLQxlef8uKO6xrncqDdQaNzBSj4egRjzLpcwjd/4ti', 'Dr. Esteban Soto Vargas', 'MEDICO'),
('farma1', '$2a$10$ghxOZJ3OXJUsLQxlef8uKO6xrncqDdQaNzBSj4egRjzLpcwjd/4ti', 'Dra. Lucia Ramirez Solis', 'FARMACEUTICO');

INSERT INTO medicamento (codigo, nombre, stock, precio_unitario) VALUES
('MED-001', 'Acetaminofen 500mg', 150, 1200.00),
('MED-002', 'Amoxicilina 500mg', 80, 4500.00),
('MED-003', 'Ibuprofeno 400mg', 100, 2100.00),
('MED-004', 'Loratadina 10mg', 60, 1800.00),
('MED-005', 'Omeprazol 20mg', 45, 3200.00);

INSERT INTO receta_medica (codigo_receta, paciente_nombre, medico_id, estado, fecha_emision) VALUES
('REC-2026-001', 'Mariana Fallas Cordero', 1, 'PENDIENTE', CURRENT_TIMESTAMP),
('REC-2026-002', 'Carlos Guzman Arias', 1, 'DESPACHADA', CURRENT_TIMESTAMP);

INSERT INTO detalle_receta (receta_id, medicamento_id, cantidad, dosis_indicada) VALUES
(1, 1, 20, '1 tableta cada 8 horas por 5 dias'),
(1, 3, 15, '1 tableta cada 12 horas por 3 dias'),
(2, 2, 14, '1 capsula cada 12 horas por 7 dias');
