
package com.segdyma.dao;

import com.segdyma.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CategoriaDao extends JpaRepository<Categoria,Long>{
    
}
