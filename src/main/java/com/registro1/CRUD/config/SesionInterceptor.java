/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro1.CRUD.config;

import com.registro1.CRUD.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author jull
 */
@Component
public class SesionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogeado");
            if (usuario != null && "TRABAJADOR".equalsIgnoreCase(usuario.getRol())) {
                return true; // El trabajador puede acceder
            }
        }

        //Redirige a login si no hay sesion o no es trabajador
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
