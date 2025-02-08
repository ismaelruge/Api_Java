package com.ismaelruge;

import org.springframework.web.bind.annotation.*;

@RestController
public class MiControlador {
    @GetMapping("/publico")
    public String publico() {
        return "Este es un contenido público.";
    }

    @GetMapping("/privado")
    public String privado() {
        return "Este es un contenido privado, para cerrar sesión, utiliza /logout";
    }
}
