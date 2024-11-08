
package com.segdyma.dao;

import com.segdyma.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProductoDao extends JpaRepository<Producto,Long>{
    
}
