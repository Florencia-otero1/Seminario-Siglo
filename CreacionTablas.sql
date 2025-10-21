-- ==============================
-- TABLAS PARAMÉTRICAS
-- ==============================

CREATE TABLE Especie (
    id_especie INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE TipoReporte (
    id_tipo_reporte INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE EstadoReporte (
    id_estado_reporte INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- ==============================
-- TABLAS PRINCIPALES
-- ==============================

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('usuario', 'administrador', 'voluntario') DEFAULT 'usuario',
    estado ENUM('activo', 'bloqueado') DEFAULT 'activo'
);

CREATE TABLE Mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    id_especie INT NOT NULL,
    sexo ENUM('macho','hembra'),
    edad_aproximada INT,
    color VARCHAR(100),
    FOREIGN KEY (id_especie) REFERENCES Especie(id_especie)
);

CREATE TABLE Reporte (
    id_reporte INT AUTO_INCREMENT PRIMARY KEY,
    id_mascota INT NOT NULL,
    id_usuario INT NOT NULL,
    id_tipo_reporte INT NOT NULL,
    id_estado_reporte INT DEFAULT 1,
    descripcion TEXT,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    ubicacion VARCHAR(255),
    foto_url VARCHAR(255),
    FOREIGN KEY (id_mascota) REFERENCES Mascota(id_mascota),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_tipo_reporte) REFERENCES TipoReporte(id_tipo_reporte),
    FOREIGN KEY (id_estado_reporte) REFERENCES EstadoReporte(id_estado_reporte)
);

CREATE TABLE Difusion (
    id_difusion INT AUTO_INCREMENT PRIMARY KEY,
    id_reporte INT NOT NULL,
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    plataforma VARCHAR(50),
    estado ENUM('exitoso', 'error') DEFAULT 'exitoso',
    FOREIGN KEY (id_reporte) REFERENCES Reporte(id_reporte)
);

CREATE TABLE Donacion (
    id_donacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('aprobada', 'rechazada', 'pendiente') DEFAULT 'pendiente',
    comprobante_url VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE AuditoriaCambios (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_reporte INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    cambio_realizado VARCHAR(255),
    FOREIGN KEY (id_reporte) REFERENCES Reporte(id_reporte),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

-- ==============================
-- TABLAS ADICIONALES (DINÁMICAS)
-- ==============================

CREATE TABLE AtencionVeterinaria (
    id_atencion INT AUTO_INCREMENT PRIMARY KEY,
    id_mascota INT NOT NULL,
    fecha DATE NOT NULL,
    tipo_atencion VARCHAR(100) NOT NULL,
    costo DECIMAL(10,2),
    responsable VARCHAR(100),
    FOREIGN KEY (id_mascota) REFERENCES Mascota(id_mascota)
);

CREATE TABLE AsignacionDonacion (
    id_asignacion INT AUTO_INCREMENT PRIMARY KEY,
    id_donacion INT NOT NULL,
    destino VARCHAR(255),
    monto_asignado DECIMAL(10,2),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_donacion) REFERENCES Donacion(id_donacion)
);