-- Insertar datos de prueba
-- Tabla Usuario
INSERT INTO Usuario (nombre, email, contrasena, rol, estado) VALUES
('Ana Pérez', 'ana.perez@gmail.com', 'contra1', 'usuario', 'activo'),
('Juan López', 'juan.lopez@gmail.com', 'contra2', 'administrador', 'activo'),
('María Gómez', 'maria.gomez@gmail.com', 'contra3', 'voluntario', 'bloqueado');

-- Tabla Reporte
INSERT INTO Reporte (tipo, especie, descripcion, fecha, ubicacion, foto_url, estado, id_usuario) VALUES
('perdida', 'perro', 'Perro marrón con collar rojo, perdido en plaza central.', '2025-09-15 14:30:00', 'Plaza Central, Ciudad', 'https://example.com/foto1.jpg', 'pendiente', 1),
('encontrada', 'gato', 'Gato blanco encontrado en la calle San Martín.', '2025-09-20 10:15:00', 'Calle San Martín 123', 'https://example.com/foto2.jpg', 'validado', 2),
('callejero', 'perro', 'Cachorro abandonado cerca de la terminal de ómnibus.', '2025-09-25 18:45:00', 'Terminal de ómnibus, Ciudad', 'https://example.com/foto3.jpg', 'difundido', 3);

-- Tabla Difusion
INSERT INTO Difusion (id_reporte, fecha_publicacion, red_social, estado) VALUES
(1, '2025-09-16 09:00:00', 'Facebook', 'exitoso'),
(2, '2025-09-21 15:30:00', 'Instagram', 'exitoso'),
(3, '2025-09-26 12:45:00', 'Facebook', 'error');

-- Tabla Donacion
INSERT INTO Donacion (id_usuario, monto, fecha, medio_pago, estado, comprobante_url) VALUES
(1, 1500.00, '2025-09-17 11:20:00', 'Tarjeta de crédito', 'aprobada', 'https://example.com/comprobante1.pdf'),
(2, 500.50, '2025-09-22 16:10:00', 'MercadoPago', 'pendiente', 'https://example.com/comprobante2.pdf'),
(3, 250.00, '2025-09-27 09:45:00', 'Transferencia bancaria', 'rechazada', 'https://example.com/comprobante3.pdf');

-- Tabla AuditoriaCambios
INSERT INTO AuditoriaCambios (id_reporte, id_usuario, fecha, cambio_realizado) VALUES
(1, 2, '2025-09-16 10:00:00', 'Actualizó la descripción del reporte.'),
(2, 1, '2025-09-21 16:00:00', 'Cambió el estado a validado.'),
(3, 3, '2025-09-26 13:00:00', 'Adjuntó foto del animal.');