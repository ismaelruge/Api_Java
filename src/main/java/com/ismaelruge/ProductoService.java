package com.ismaelruge;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Flux;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto guardar(Producto producto) {
        return repository.guardar(producto);
    }

    public Flux<Producto> obtenerTodos() {
        Flux<Producto> obTod = repository.obtenerTodos();

        return obTod.hasElements()
                .flatMapMany(hasData -> hasData ? obTod : getDefaultProductos());
    }

    public Flux<Producto> obtenerNormal() {
        Flux<Producto> obTod = repository.obtenerTodos();
        return  obTod;
    }

    public Flux<Producto> obtenerFlix() {
        return  getDefaultProductos();
    }

    private Flux<Producto> getDefaultProductos() {
        return Flux.just(
                new Producto("1 Example", "Laptop Example", 1200.0),
                new Producto("2 Example", "Mouse Example", 25.0),
                new Producto("3 Example", "Teclado Example", 45.0)
        );
    }

    public Optional<Producto> obtenerPorId(String id) {
        return repository.obtenerPorId(id);
    }

    public Producto actualizar(String id, Producto productoActualizado) {
        return obtenerPorId(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            return repository.guardar(producto);
        }).orElse(null);
    }

    public boolean eliminar(String id) {
        return repository.eliminar(id);
    }
}
