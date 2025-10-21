-- ==============================
-- TABLAS PARAMÉTRICAS
-- ==============================
INSERT INTO Especie (nombre) VALUES 
('Perro'), 
('Gato');

INSERT INTO TipoReporte (nombre) VALUES
('perdida'),
('encontrada'),
('callejero');

INSERT INTO EstadoReporte (nombre) VALUES
('pendiente'),
('validado'),
('difundido'),
('resuelto');

-- ==============================
-- USUARIO
-- ==============================
INSERT INTO Usuario (nombre, email, contrasena, rol, estado) VALUES
('Ana Pérez', 'ana.perez@gmail.com', 'contra1', 'usuario', 'activo'),
('Juan López', 'juan.lopez@gmail.com', 'contra2', 'administrador', 'activo'),
('María Gómez', 'maria.gomez@gmail.com', 'contra3', 'voluntario', 'bloqueado');

-- ==============================
-- MASCOTA
-- ==============================
INSERT INTO Mascota (nombre, id_especie, sexo, edad_aproximada, color) VALUES
('Perro marrón', 1, 'macho', 3, 'Marrón'),
('Gato blanco', 2, 'hembra', 2, 'Blanco'),
('Cachorro callejero', 1, 'macho', 1, 'Marrón');

-- ==============================
-- REPORTE
-- ==============================
INSERT INTO Reporte (id_mascota, id_usuario, id_tipo_reporte, id_estado_reporte, descripcion, fecha, ubicacion, foto_url) VALUES
(1, 1, 1, 1, 'Perro marrón con collar rojo, perdido en plaza central.', '2025-09-15', 'Plaza Central, Ciudad', 'https://example.com/foto1.jpg'),
(2, 2, 2, 2, 'Gato blanco encontrado en la calle San Martín.', '2025-09-20', 'Calle San Martín 123', 'https://example.com/foto2.jpg'),
(3, 3, 3, 3, 'Cachorro abandonado cerca de la terminal de ómnibus.', '2025-09-25', 'Terminal de ómnibus, Ciudad', 'https://example.com/foto3.jpg');

-- ==============================
-- DIFUSION
-- ==============================
INSERT INTO Difusion (id_reporte, fecha_publicacion, plataforma, estado) VALUES
(1, '2025-09-16', 'Facebook', 'exitoso'),
(2, '2025-09-21', 'Instagram', 'exitoso'),
(3, '2025-09-26', 'Facebook', 'error');

-- ==============================
-- DONACION
-- ==============================
INSERT INTO Donacion (id_usuario, monto, fecha, estado, comprobante_url) VALUES
(1, 1500.00, '2025-09-17', 'aprobada', 'https://example.com/comprobante1.pdf'),
(2, 500.50, '2025-09-22', 'pendiente', 'https://example.com/comprobante2.pdf'),
(3, 250.00, '2025-09-27', 'rechazada', 'https://example.com/comprobante3.pdf');

-- ==============================
-- AUDITORIA DE CAMBIOS
-- ==============================
INSERT INTO AuditoriaCambios (id_reporte, id_usuario, fecha, cambio_realizado) VALUES
(1, 2, '2025-09-16', 'Actualizó la descripción del reporte.'),
(2, 1, '2025-09-21', 'Cambió el estado a validado.'),
(3, 3, '2025-09-26', 'Adjuntó foto del animal.');

-- ==============================
-- ASIGNACION DONACION 
-- ==============================
INSERT INTO AtencionVeterinaria (id_mascota, fecha, tipo_atencion, costo, responsable) VALUES
(1, '2025-09-18', 'Vacunación anual', 300.00, 'Dr. López'),
(2, '2025-09-23', 'Revisión general', 200.00, 'Dra. Gómez'),
(3, '2025-09-28', 'Castración', 500.00, 'Dr. Pérez');

-- ==============================
-- ATENCION VETERINARIA
-- ==============================
INSERT INTO AsignacionDonacion (id_donacion, destino, monto_asignado, fecha) VALUES
(1, 'Refugio Callejero', 1000.00, '2025-09-19'),
(1, 'Atención Veterinaria', 500.00, '2025-09-19'),
(2, 'Refugio Callejero', 300.00, '2025-09-23'),
(3, 'Atención Veterinaria', 250.00, '2025-09-28');
