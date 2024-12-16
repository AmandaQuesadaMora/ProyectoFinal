package com.segdyma.services;

import com.segdyma.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> getCategorias(boolean activos);

    Categoria getCategoria(Categoria categoria);

    Categoria getCategoriaById(Long id);

    void save(Categoria categoria);

    void delete(Categoria categoria);
    
    Categoria getCategoriaPorId(Long idCategoria);
}
