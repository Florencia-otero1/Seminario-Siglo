-- Consultas para evidenciar las tablas pobladas
SELECT * FROM Usuario;

-- Ver todos los reportes de mascotas
SELECT * FROM Reporte;

-- Ver todas las difusiones en redes sociales
SELECT * FROM Difusion;

-- Ver todas las donaciones
SELECT * FROM Donacion;

-- Ver todos los registros de auditoría
SELECT * FROM AuditoriaCambios;

-- Borrar todos los registros de todas las tablas
DELETE FROM AuditoriaCambios;

DELETE FROM Difusion;

DELETE FROM Donacion;

DELETE FROM Reporte;

DELETE FROM Usuario;