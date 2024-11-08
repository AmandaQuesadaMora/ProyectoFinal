package com.segdyma.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table (name="producto")
public class Producto implements Serializable{
    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    // private Long idCategoria; este valor ahora esta en el objeto categoria
    private String descripcion;
    private String detalle;
    private double precio;
    private int existencias;
    private String rutaImagen;
    private boolean activo;
  
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;
    
/*CREATE TABLE producto (
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
)*/
   
}
