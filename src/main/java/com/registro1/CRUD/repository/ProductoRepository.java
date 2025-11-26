/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.registro1.CRUD.repository;

import com.registro1.CRUD.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jull
 */
public interface ProductoRepository extends JpaRepository <Producto, Long>{
    
}
