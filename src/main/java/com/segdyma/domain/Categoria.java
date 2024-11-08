package com.segdyma.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table (name="categoria")
public class Categoria implements Serializable{
    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;
    
    @OneToMany
    @JoinColumn(name="id_categoria",updatable = false)
    private List<Producto> productos;
    
    /*
CREATE TABLE categoria (
    id_categoria INT NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(30) NOT NULL,
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id_categoria)
)*/
    
    
    
    
    
}
