/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro1.CRUD.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author jull
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SesionInterceptor sesionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Proteger todas las rutas de CRUD de productos
        registry.addInterceptor(sesionInterceptor)
            .addPathPatterns("/productos/**"); 
    }
}
