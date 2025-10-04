-- Tabla de usuarios
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('usuario', 'administrador', 'voluntario') DEFAULT 'usuario',
    estado ENUM('activo', 'bloqueado') DEFAULT 'activo'
);

-- Tabla de reportes de mascotas
CREATE TABLE Reporte (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('perdida', 'encontrada', 'callejero') NOT NULL,
    especie VARCHAR(50) NOT NULL,
    descripcion TEXT,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    ubicacion VARCHAR(255),
    foto_url VARCHAR(255),
    estado ENUM('pendiente', 'validado', 'difundido', 'resuelto') DEFAULT 'pendiente',
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario)
        REFERENCES Usuario (id_usuario)
);

-- Tabla de difusiones en redes sociales
CREATE TABLE Difusion (
    id_difusion INT AUTO_INCREMENT PRIMARY KEY,
    id_reporte INT NOT NULL,
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    red_social ENUM('Facebook', 'Instagram'),
    estado ENUM('exitoso', 'error') DEFAULT 'exitoso',
    FOREIGN KEY (id_reporte)
        REFERENCES Reporte (id_reporte)
);

-- Tabla de donaciones
CREATE TABLE Donacion (
    id_donacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    monto DECIMAL(10 , 2 ) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    medio_pago VARCHAR(50),
    estado ENUM('aprobada', 'rechazada', 'pendiente') DEFAULT 'pendiente',
    comprobante_url VARCHAR(255),
    FOREIGN KEY (id_usuario)
        REFERENCES Usuario (id_usuario)
);

-- Tabla de auditoría de cambios
CREATE TABLE AuditoriaCambios (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_reporte INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cambio_realizado VARCHAR(255),
    FOREIGN KEY (id_reporte)
        REFERENCES Reporte (id_reporte),
    FOREIGN KEY (id_usuario)
        REFERENCES Usuario (id_usuario)
);