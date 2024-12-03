
package com.segdyma.services;

import java.util.List;
import java.util.Map;
import com.segdyma.domain.CarritoItem;

public interface CarritoService {
    void agregarProducto(Long idProducto);
    void eliminarProducto(Long idItem);
    void actualizarCarrito(Map<Long, Integer> cantidades);
    List<CarritoItem> obtenerCarrito();
    double calcularTotal();
}
