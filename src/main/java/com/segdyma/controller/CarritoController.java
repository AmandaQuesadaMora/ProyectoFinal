
package com.segdyma.controller;

import com.segdyma.services.CarritoService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

 @Autowired
    private CarritoService carritoService;

    @GetMapping
    public String mostrarCarrito(Model model) {
        var carrito = carritoService.obtenerCarrito();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carritoService.calcularTotal());
        return "/carrito/listado";
    }

    @GetMapping("/agregar/{idProducto}")
    public String agregarProducto(@PathVariable Long idProducto) {
        carritoService.agregarProducto(idProducto);
        return "redirect:/carrito";
    }

    @GetMapping("/eliminar/{idItem}")
    public String eliminarProducto(@PathVariable Long idItem) {
        carritoService.eliminarProducto(idItem);
        return "redirect:/carrito";
    }

    @PostMapping("/actualizar")
    public String actualizarCarrito(@RequestParam Map<String, String> cantidades, Model model) {
        System.out.println("Cantidades recibidas: " + cantidades);

        Map<Long, Integer> cantidadesLong = new HashMap<>();
        for (Map.Entry<String, String> entry : cantidades.entrySet()) {
            try {
                Long idItem = Long.parseLong(entry.getKey());
                Integer cantidad = Integer.parseInt(entry.getValue());
                cantidadesLong.put(idItem, cantidad);
                System.out.println("Actualizando item " + idItem + " con cantidad " + cantidad);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        carritoService.actualizarCarrito(cantidadesLong);
        var carrito = carritoService.obtenerCarrito();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carritoService.calcularTotal());

        return "/carrito/listado";
    }

}