package com.segdyma.services;

import com.segdyma.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> getUsuarios(boolean activos);
    Usuario getUsuario(Usuario usuario);
    void save(Usuario usuario);
    void delete(Usuario usuario);
    Usuario findByUsername(String username);  
}

