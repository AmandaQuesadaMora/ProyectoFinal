package com.segdyma.controller;

import com.segdyma.domain.CarritoItem;
import com.segdyma.services.CarritoService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
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
    public String confirmarCompra(Model model) {
        
        // Generar datos de factura simulados
        var carrito = carritoService.obtenerCarrito();
        double total = carritoService.calcularTotal();

        // Generar un número de guía aleatorio
        Random random = new Random();
        int numeroGuia = 100000 + random.nextInt(900000);

        // Estimar tiempo de entrega (2-5 días hábiles simulados)
        int tiempoEntrega = 2 + random.nextInt(4);

        // Agregar atributos al modelo
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("numeroGuia", numeroGuia);
        model.addAttribute("tiempoEntrega", tiempoEntrega);

        return "factura/confirmacion";
    }
    
    @PostMapping("/generar-factura")
public void generarFactura(HttpServletResponse response) throws IOException {
    // Obtener los productos del carrito
    List<CarritoItem> carrito = carritoService.obtenerCarrito();
    double subtotal = carritoService.calcularSubtotal();
    double impuestos = carritoService.calcularImpuestos();
    double total = carritoService.calcularTotal();

    // Crear el documento PDF
    PDDocument documento = new PDDocument();
    PDPage pagina = new PDPage(PDRectangle.A4);
    documento.addPage(pagina);

    PDPageContentStream contenido = new PDPageContentStream(documento, pagina);
    contenido.beginText();
    contenido.setFont(PDType1Font.HELVETICA, 14);
    contenido.newLineAtOffset(25, 750);

    // Información de la factura
    contenido.showText("Factura de Compra");
    contenido.newLineAtOffset(0, -20);
    contenido.showText("Subtotal: " + subtotal);
    contenido.newLineAtOffset(0, -20);
    contenido.showText("Impuestos (13%): " + impuestos);
    contenido.newLineAtOffset(0, -20);
    contenido.showText("Total: " + total);

    // Agregar los productos del carrito
    for (CarritoItem item : carrito) {
        contenido.newLineAtOffset(0, -20);
        contenido.showText(item.getProducto().getDescripcion() + " - " + 
                item.getCantidad() + " x " + 
                item.getProducto().getPrecio() + " = " + 
                (item.getProducto().getPrecio() * item.getCantidad()));
    }

    contenido.endText();
    contenido.close();

    // Configurar la respuesta HTTP para descargar el PDF
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");

    // Escribir el PDF al flujo de salida
    documento.save(response.getOutputStream());
    documento.close();
}
}
