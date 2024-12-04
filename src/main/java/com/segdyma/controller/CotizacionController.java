package com.segdyma.controller;

import com.segdyma.domain.CarritoItem;
import com.segdyma.services.CarritoService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.OutputStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cotizacion")
public class CotizacionController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public String mostrarCotizacion(Model model) {
        // Obtener los productos del carrito
        var carrito = carritoService.obtenerCarrito();
        double subtotal = carritoService.calcularSubtotal();
        double impuestos = carritoService.calcularImpuestos();
        double total = carritoService.calcularTotal();

        // Pasar los datos al modelo
        model.addAttribute("carrito", carrito);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("impuestos", impuestos);
        model.addAttribute("total", total);
        return "/cotizacion/cotizacion";
    }

    @PostMapping("/generar")
    public void generarCotizacion(HttpServletResponse response) throws IOException {
        // Obtener los productos del carrito
        var carrito = carritoService.obtenerCarrito();
        double subtotal = carritoService.calcularSubtotal();
        double impuestos = carritoService.calcularImpuestos();
        double total = carritoService.calcularTotal();

        // Crear el documento PDF
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.A4);
        documento.addPage(pagina);
        PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

        // Configurar el contenido (añadir detalles del cliente, productos, total, etc.)
        contenido.beginText();
        contenido.setFont(PDType1Font.HELVETICA, 14);
        contenido.newLineAtOffset(25, 750);
        contenido.showText("Cotización");
        contenido.newLineAtOffset(0, -20);
        contenido.showText("Subtotal: " + subtotal);
        contenido.newLineAtOffset(0, -20);
        contenido.showText("Impuestos (13%): " + impuestos);
        contenido.newLineAtOffset(0, -20);
        contenido.showText("Total: " + total);

        // Agregar los productos
        for (CarritoItem item : carrito) {
            contenido.newLineAtOffset(0, -20);
            contenido.showText(item.getProducto().getDescripcion() + " - " + item.getCantidad() + " x " + item.getProducto().getPrecio() + " = " + (item.getProducto().getPrecio() * item.getCantidad()));
        }

        contenido.endText();
        contenido.close();

        // Establecer el tipo de contenido para la respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=cotizacion.pdf");

        // Escribir el PDF al flujo de salida
        documento.save(response.getOutputStream());
        documento.close();
    }
}