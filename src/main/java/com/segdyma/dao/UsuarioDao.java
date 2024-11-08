
package com.segdyma.dao;

import com.segdyma.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UsuarioDao extends JpaRepository<Usuario,Long>{
    
}
