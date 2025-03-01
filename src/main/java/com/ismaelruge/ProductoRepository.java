package com.ismaelruge;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductoRepository {
    private final Map<String, Producto> productos = new ConcurrentHashMap<>();

    public Flux<Producto> obtenerTodos() {
        return Flux.fromIterable(productos.values());
    }

    public Optional<Producto> obtenerPorId(String id) {
        return Optional.ofNullable(productos.get(id));
    }

    public Producto guardar(Producto producto) {
        productos.put(producto.getId(), producto);
        return producto;
    }

    public boolean eliminar(String id) {
        return productos.remove(id) != null;
    }
}
