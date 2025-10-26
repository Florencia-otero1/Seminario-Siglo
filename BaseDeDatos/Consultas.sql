-- Consultas para evidenciar las tablas pobladas y su correcto funcionamiento 

-- Ver todos los usuarios
SELECT * FROM Usuario;

-- Ver todas las especies y tipos de reporte
SELECT * FROM Especie;
SELECT * FROM TipoReporte;
SELECT * FROM EstadoReporte;

-- Ver todas las mascotas con su especie
SELECT m.id_mascota, m.nombre AS nombre_mascota, e.nombre AS especie, m.sexo, m.edad_aproximada, m.color
FROM Mascota m
JOIN Especie e ON m.id_especie = e.id_especie;

-- Ver todos los reportes con info de usuario, mascota, tipo y estado
SELECT r.id_reporte, u.nombre AS usuario, m.nombre AS mascota, t.nombre AS tipo_reporte, s.nombre AS estado_reporte, r.descripcion, r.fecha, r.ubicacion, r.foto_url
FROM Reporte r
JOIN Usuario u ON r.id_usuario = u.id_usuario
JOIN Mascota m ON r.id_mascota = m.id_mascota
JOIN TipoReporte t ON r.id_tipo_reporte = t.id_tipo_reporte
JOIN EstadoReporte s ON r.id_estado_reporte = s.id_estado_reporte;

-- Ver difusiones con reporte y plataforma
SELECT d.id_difusion, r.id_reporte, m.nombre AS mascota, d.plataforma, d.fecha_publicacion, d.estado
FROM Difusion d
JOIN Reporte r ON d.id_reporte = r.id_reporte
JOIN Mascota m ON r.id_mascota = m.id_mascota;

-- Ver donaciones con usuario
SELECT d.id_donacion, u.nombre AS usuario, d.monto, d.fecha, d.estado, d.comprobante_url
FROM Donacion d
JOIN Usuario u ON d.id_usuario = u.id_usuario;

-- Ver auditoría de cambios con reportes y usuarios
SELECT a.id_auditoria, r.id_reporte, m.nombre AS mascota, u.nombre AS usuario, a.fecha, a.cambio_realizado
FROM AuditoriaCambios a
JOIN Reporte r ON a.id_reporte = r.id_reporte
JOIN Usuario u ON a.id_usuario = u.id_usuario
JOIN Mascota m ON r.id_mascota = m.id_mascota;

-- Ver atenciones veterinarias con mascotas
SELECT av.id_atencion, m.nombre AS mascota, av.fecha, av.tipo_atencion, av.costo, av.responsable
FROM AtencionVeterinaria av
JOIN Mascota m ON av.id_mascota = m.id_mascota;

-- Ver asignación de donaciones con montos y destino
SELECT ad.id_asignacion, d.id_donacion, u.nombre AS usuario, ad.destino, ad.monto_asignado, ad.fecha
FROM AsignacionDonacion ad
JOIN Donacion d ON ad.id_donacion = d.id_donacion
JOIN Usuario u ON d.id_usuario = u.id_usuario;
