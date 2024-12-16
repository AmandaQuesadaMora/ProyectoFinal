
package com.segdyma.dao;

import com.segdyma.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.List;

public interface ProductoDao extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria_IdCategoria(Long idCategoria);
}
