package com.ismaelruge;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api")
public class SaludoController {
    private final MessageSource messageSource;

    public SaludoController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/saludo")
    public String obtenerSaludo(@RequestParam(name = "lang", required = false) String lang) {
        // Si no se especifica "lang", retornar el mensaje por defecto
        if (lang == null || lang.isBlank()) {
            return "¡Hola, API RESTful!";
        }

        Locale locale = new Locale(lang);
        return messageSource.getMessage("saludo.message", null, locale);
    }
}