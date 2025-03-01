package com.ismaelruge;

import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Producto> listarProductos() {
        return service.obtenerTodos();
    }

    @GetMapping("GetFlux")
    public  Flux<Producto> listarFlux() {
        return  service.obtenerFlix();
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return service.guardar(producto);
    }

    @GetMapping("/{id}")
    public Optional<Producto> obtenerProducto(@PathVariable String id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable String id, @RequestBody Producto producto) {
        return service.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable String id) {
        return service.eliminar(id) ? "Eliminado" : "No encontrado";
    }
}