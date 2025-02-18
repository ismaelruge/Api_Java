package com.ismaelruge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class InternacionalizacionController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/saludo")
    public String obtenerSaludo(@RequestHeader(name = "Accept-Language", required = false) String language) {
        Locale locale = (language != null) ? Locale.forLanguageTag(language) : LocaleContextHolder.getLocale();
        return messageSource.getMessage("welcome.message", null, locale);
    }
}