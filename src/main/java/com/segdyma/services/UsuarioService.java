
package com.segdyma.services;

import com.segdyma.domain.Usuario;
import com.segdyma.domain.Usuario;
import java.util.List;


public interface UsuarioService {
    //Se obtiene un arraylist con los registros de la tabla categoría, todos los registros o solo los activos

public List<Usuario> getUsuarios (boolean activos);


   // Se obtiene un Usuario, a partir del id de un usuario
    public Usuario getUsuario(Usuario usuario);
    
    // Se inserta un nuevo usuario si el id del usuario esta vacío
    // Se actualiza un usuario si el id del usuario NO esta vacío
    public void save(Usuario usuario);
    
    // Se elimina el usuario que tiene el id pasado por parámetro
    public void delete(Usuario usuario);
}
