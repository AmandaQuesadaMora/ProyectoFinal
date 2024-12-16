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
        // Imprimir las cantidades recibidas
        System.out.println("Cantidades recibidas: " + cantidades);

        // Convertir las claves de String a Long (sin los prefijos "cantidades_")
        Map<Long, Integer> cantidadesLong = new HashMap<>();
        for (Map.Entry<String, String> entry : cantidades.entrySet()) {
            try {
                // Extraer el idItem de la clave (que tendrá el formato "cantidades_1", "cantidades_2", etc.)
                Long idItem = Long.parseLong(entry.getKey().replace("cantidades_", ""));
                Integer cantidad = Integer.parseInt(entry.getValue());
                cantidadesLong.put(idItem, cantidad);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Actualizar el carrito
        carritoService.actualizarCarrito(cantidadesLong);

        // Obtener el carrito actualizado y el total
        var carrito = carritoService.obtenerCarrito();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carritoService.calcularTotal());

        // Redirigir al listado de carrito con los valores actualizados
        return "/carrito/listado";
    }

    // Método para mostrar la vista de pago
   @GetMapping("/pago")
    public String mostrarPago(Model model) {
       
        var carrito = carritoService.obtenerCarrito();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", carritoService.calcularTotal());
        
        return "carrito/pago";  // Asegúrate de que el archivo .html está en templates/carrito
    }
    
    @PostMapping("/realizarCompra")
    public String realizarCompra(Model model) {
        // Lógica para procesar el pago (simulada aquí)
        // Este sería el proceso real de pago (como con una API de pago)

        // Agregar mensajes para mostrar en la vista

        model.addAttribute("Se está procesando el pago...", "¡Su pago se ha realizado con éxito!");

        // Después de mostrar las alertas, redirige a la página principal (o donde desees)
        return "redirect:/menu"; // Redirige a la página principal
    }

    
    

}
