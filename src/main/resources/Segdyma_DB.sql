DROP SCHEMA IF EXISTS Segdyma_DB;
DROP USER IF EXISTS usuario_admin;
CREATE DATABASE Segdyma_DB;
USE Segdyma_DB;

CREATE USER 'usuario_admin'@'%' IDENTIFIED BY 'adminsegdyma.';
GRANT ALL PRIVILEGES ON Segdyma_DB.* TO 'usuario_admin'@'%';
FLUSH PRIVILEGES;

CREATE TABLE usuario (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(512) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    correo VARCHAR(75) NULL,
    telefono VARCHAR(15) NULL,
    activo BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id_usuario)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE categoria (
    id_categoria INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(30) NOT NULL,
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id_categoria)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE producto (
    id_producto INT NOT NULL AUTO_INCREMENT,
    id_categoria INT NOT NULL,
    descripcion VARCHAR(30) NOT NULL,
    detalle VARCHAR(1600) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    existencias INT NOT NULL,
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id_producto),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE carrito (
    id_carrito INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_carrito),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE carrito_items (
    id_item INT NOT NULL AUTO_INCREMENT,
    id_carrito INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (id_item),
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

-- Insertar datos de ejemplo

-- Insertar usuarios
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, activo) VALUES
('usuario1', 'contraseña1', 'Juan', 'Castro', 'juan@segdyma.com', '555-1234', TRUE),
('usuario2', 'contraseña2', 'Ana', 'Lopez', 'ana@segdyma.com', '555-5678', TRUE),
('admin', 'adminpass', 'Carlos', 'Martinez', 'admin@segdyma.com', '555-9876', TRUE);

-- Insertar categorías
INSERT INTO categoria (descripcion, ruta_imagen, activo) VALUES
('Drenaje', 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
('Tuberias', 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
('Tanques', 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE);

-- Insertar productos
INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias, ruta_imagen, activo) VALUES
(1, 'GEOTEXTIL NO TEJIDO Tipo GT131-105G', 'Geotextil no tejido para drenaje.', 100.00, 10, 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
(1, 'GEODREN Tipo TN 220-2-6', 'Sistema de drenaje Geodren.', 150.00, 15, 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
(2, 'TUBERÍA FORTERA', 'Tubería Fortera para diversas aplicaciones.', 200.00, 20, 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
(2, 'TUBERÍA SANITARIA GRIS', 'Tubería sanitaria gris para sistemas de plomería.', 250.00, 25, 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
(3, 'TriCapa', 'Tanque TriCapa para almacenamiento.', 300.00, 30, 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE),
(3, 'BiCapa', 'Tanque BiCapa para almacenamiento.', 350.00, 35, 'https://img.freepik.com/vector-premium/pendiente_592324-18443.jpg?w=740', TRUE);

-- Crear tablas adicionales (opcional)

CREATE TABLE factura (
    id_factura INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    fecha DATE NOT NULL,
    total DECIMAL(10, 2),
    estado INT,  -- Ej: 1=Activa, 2=Pagada, 3=Anulada
    PRIMARY KEY (id_factura),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE venta (
    id_venta INT NOT NULL AUTO_INCREMENT,
    id_factura INT NOT NULL,
    id_producto INT NOT NULL,
    precio DECIMAL(10, 2),
    cantidad INT,
    PRIMARY KEY (id_venta),
    FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE rol (
    id_rol INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20),
    id_usuario INT,
    PRIMARY KEY (id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE ruta (
    id_ruta INT AUTO_INCREMENT NOT NULL,
    patron VARCHAR(255) NOT NULL,
    rol_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_ruta)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE carrito_item (
    id_item BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    cantidad INT NOT NULL,
    CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);
