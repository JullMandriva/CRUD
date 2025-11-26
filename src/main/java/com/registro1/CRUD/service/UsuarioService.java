/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro1.CRUD.service;

import com.registro1.CRUD.model.Usuario;
import com.registro1.CRUD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 *
 * @author jull
 */

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar usuario (cliente o trabajador)
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar por nombre de usuario
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    // Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Validar login (temporal sin contraseña encriptada)
    public boolean validarLogin(String loginInput, String password) {

    // 1. Buscar por nombre de usuario
    Optional<Usuario> userOpt = usuarioRepository.findByNombreUsuario(loginInput);

    // 2. Si no existe, buscar por email
    if (userOpt.isEmpty()) {
        userOpt = usuarioRepository.findByEmail(loginInput);
    }

    // 3. Validar contraseña
    if (userOpt.isPresent()) {
        Usuario u = userOpt.get();
        return u.getPassword().equals(password); // Luego se reemplaza por BCrypt
    }

    return false;
    }
}
