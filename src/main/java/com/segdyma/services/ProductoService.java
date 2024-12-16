
package com.segdyma.services;

import com.segdyma.domain.Producto;
import java.util.List;


public interface ProductoService {
    //Se obtiene un arraylist con los registros de la tabla categoría, todos los registros o solo los activos

public List<Producto> getProductos (boolean activos);


   // Se obtiene un Producto, a partir del id de un producto
    public Producto getProducto(Producto producto);
    
    // Se inserta un nuevo producto si el id del producto esta vacío
    // Se actualiza un producto si el id del producto NO esta vacío
    public void save(Producto producto);
    
    // Se elimina el producto que tiene el id pasado por parámetro
    public void delete(Producto producto);
    
    List<Producto> getProductosPorCategoria(Long idCategoria);
}
