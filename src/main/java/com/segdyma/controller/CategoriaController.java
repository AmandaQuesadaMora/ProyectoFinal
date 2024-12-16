package com.segdyma.controller;

import com.segdyma.domain.Categoria;
import com.segdyma.services.CategoriaService;
import com.segdyma.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/{idCategoria}/productos")
    public String verProductosPorCategoria(@PathVariable Long idCategoria, Model model) {
        // Obtén la lista de productos por categoría
        var productos = productoService.getProductosPorCategoria(idCategoria);

        // Agrega los productos al modelo
        model.addAttribute("productos", productos);

        // Agrega la categoría actual al modelo (opcional)
        var categoria = categoriaService.getCategoriaPorId(idCategoria);
        model.addAttribute("categoria", categoria);

        return "categoria/productos";
    }
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);
        return "categoria/listado"; 
    }

}
