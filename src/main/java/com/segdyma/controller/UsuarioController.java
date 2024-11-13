package com.segdyma.controller;

import com.segdyma.domain.Usuario;
import com.segdyma.services.CategoriaService;
import com.segdyma.services.ProductoService;
import com.segdyma.services.UsuarioService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";  
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model) {
        Usuario usuario = usuarioService.findByUsername(username);

        if (usuario != null && usuario.getPassword().equals(password) && usuario.isActivo()) {
            // Usuario autenticado, redirige a la página principal
            return "redirect:/menu";
        } else {
            // Si la autenticación falla, muestra un mensaje de error
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/menu")
    public String mostrarMenu(Model model) {
        System.out.println("menu test");
        // Obtener todos los productos (sin filtros adicionales)
        var lista = productoService.getProductos(false);
        System.out.println(lista.size());
        // Limitar la lista a los primeros 3 productos
        var productosLimitados = lista.stream()
                .limit(3) // Limitar a los primeros 3 productos
                .collect(Collectors.toList());
        System.out.println("pl" + productosLimitados.size());
        // Pasar los productos limitados a la vista
        model.addAttribute("productosmenu", productosLimitados);

        // Aquí puedes seguir pasando otras variables como categorías si las necesitas
        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        // Retornar la vista que corresponde al menu
        return "menu"; // Página del menú principal después de iniciar sesión
    }
    
    
   
    // Mostrar el formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        // Validar si el usuario ya existe
        Usuario existente = usuarioService.findByUsername(usuario.getUsername());

        if (existente != null) {
            model.addAttribute("error", "El nombre de usuario ya existe.");
            return "registro";
        }

        // Validar la contraseña
        String password = usuario.getPassword();
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "La contraseña no puede estar vacía.");
            return "registro";
        }

        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{6,}$";
        if (!password.matches(passwordPattern)) {
            model.addAttribute("error", "La contraseña debe tener al menos una letra mayúscula, una letra minúscula, un carácter especial y tener mínimo 6 caracteres.");
            return "registro";
        }

        // Guardar el nuevo usuario en la base de datos
        usuario.setActivo(true);
        usuarioService.save(usuario);

        model.addAttribute("success", "Usuario registrado exitosamente.");
        return "registro";
    }
}
