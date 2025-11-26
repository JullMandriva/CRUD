/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro1.CRUD.controler;

import com.registro1.CRUD.model.Usuario;
import com.registro1.CRUD.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author jull
 */

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // ==== Página de login ====
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        return "login";
    }

    // ==== Procesar login ====
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String nombreUsuario,
            @RequestParam String password,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttrs) {

        boolean valido = usuarioService.validarLogin(nombreUsuario, password);

        //Credenciales incorrectas
        if (!valido) {
            redirectAttrs.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/login";
        }

        //Buscar usuario por nombre o por email
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorNombreUsuario(nombreUsuario);

        if (usuarioOpt.isEmpty()) {
            usuarioOpt = usuarioService.buscarPorEmail(nombreUsuario);
        }

        Usuario usuario = usuarioOpt.get();

        //Guardar usuario entero en sesión
        session.setAttribute("usuarioLogeado", usuario);
        session.setAttribute("username", usuario.getNombreUsuario()); // Para la navbar

        //MENSAJE DE BIENVENIDA para ambos tipos de usuarios
        redirectAttrs.addFlashAttribute("success",
                "Bienvenido, " + usuario.getNombreUsuario() + "! Has iniciado sesión correctamente.");

        //Redireccion segun el rol
        if (usuario.getRol().equalsIgnoreCase("TRABAJADOR")) {
            return "redirect:/productos";
        } else {
            return "redirect:/";  // Cliente va a la página principal
        }
    }

    //Pagina de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    //Procesar registro
    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario, Model model) {

        if (usuarioService.buscarPorNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            model.addAttribute("error", "Ese nombre de usuario ya está en uso");
            return "registro";
        }

        if (usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            model.addAttribute("error", "Ese correo ya está registrado");
            return "registro";
        }

        usuario.setRol("CLIENTE");
        usuarioService.registrarUsuario(usuario);

        model.addAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
        return "login";
    }

    //Logout
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate();
        redirectAttrs.addFlashAttribute("success", "Has cerrado sesión correctamente.");
        return "redirect:/login";
    }
}
