package com.segdyma.controller;

import com.segdyma.domain.Usuario;
import com.segdyma.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

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
    public String mostrarMenu() {
        return "menu"; // Página del menú principal después de iniciar sesión
    }
}
