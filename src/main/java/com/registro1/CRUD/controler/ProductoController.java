package com.registro1.CRUD.controler;

import com.registro1.CRUD.model.Producto;
import com.registro1.CRUD.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // LISTAR PRODUCTOS
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodas());
        return "producto-list";
    }

    // FORMULARIO PARA AGREGAR PRODUCTO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    // FORMULARIO PARA EDITAR PRODUCTO
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {

        Producto producto = productoService.obtenerPorId(id);
        if (producto == null) {
            throw new IllegalArgumentException("ID de producto no válido: " + id);
        }

        model.addAttribute("producto", producto);
        return "producto-form";
    }

    // GUARDAR PRODUCTO (NUEVO O EDITADO)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/productos";
    }

    // ELIMINAR PRODUCTO
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {

        productoService.eliminar(id);
        return "redirect:/productos";
    }
}
