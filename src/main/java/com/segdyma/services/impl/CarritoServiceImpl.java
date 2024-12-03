
package com.segdyma.services.impl;

import com.segdyma.dao.CarritoItemsDao;
import com.segdyma.dao.ProductoDao;
import com.segdyma.domain.CarritoItem;
import com.segdyma.services.CarritoService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoItemsDao carritoItemsDao;

    @Autowired
    private ProductoDao productoDao;

    @Override
    public void agregarProducto(Long idProducto) {
        var producto = productoDao.findById(idProducto).orElse(null);
        if (producto != null) {
            CarritoItem item = new CarritoItem();
            item.setProducto(producto);
            item.setCantidad(1);
            carritoItemsDao.save(item);
        }
    }

    @Override
    public void eliminarProducto(Long idItem) {
        carritoItemsDao.deleteById(idItem);
    }

    @Override
    public void actualizarCarrito(Map<Long, Integer> cantidades) {
        for (var entry : cantidades.entrySet()) {
            var item = carritoItemsDao.findById(entry.getKey()).orElse(null);
            if (item != null) {
                item.setCantidad(entry.getValue());
                carritoItemsDao.save(item); // Guardar el cambio
            }
        }
    }

    @Override
    public List<CarritoItem> obtenerCarrito() {
        return carritoItemsDao.findAll();
    }


    @Override
    public double calcularTotal() {
        return carritoItemsDao.findAll().stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
    }
}