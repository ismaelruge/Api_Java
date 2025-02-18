# API Java - Spring Boot

## Lenguaje de Programación Avanzado 1 - 2501B04G1

**Autor:** Ismael Ruge Gonzalez  
**Parcial:** 1  

Este proyecto es una API desarrollada con **Spring Boot** en **Java**, con funcionalidades básicas de autenticación y seguridad mediante **Spring Security**.

## Tecnologías Utilizadas
- **Java**  
- **Spring Boot**  
- **Spring Security**  
- **Maven**  

## Estructura del Proyecto

### 1. Aplicación Principal
Archivo de inicio de la aplicación:
```java
package com.ismaelruge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IsmaelrugeApplication {
	public static void main(String[] args) {
		SpringApplication.run(IsmaelrugeApplication.class, args);
	}
}
```

### 2. Controlador
Controlador que define rutas públicas y privadas:
```java
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
```

### 3. Configuración de Seguridad
Configuración de **Spring Security** para definir accesos y autenticación:
```java
package com.ismaelruge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/publico/**").permitAll() // Permitir acceso público
                        .requestMatchers("/privado/**").authenticated() // Proteger rutas privadas
                        .anyRequest().permitAll() // Permitimos el acceso libre de las demás rutas
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .formLogin(form -> form
                        .defaultSuccessUrl("/privado", true) // Redirección tras login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Redirigir después del logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .httpBasic(basic -> {});

        return http.build();
    }
}
```

### 4. Configuración de Usuarios
Definición de usuarios en memoria con contraseñas encriptadas:
```java
package com.ismaelruge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ConfiguracionUsuarios {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails usuario = User.builder()
                .username("usuario")
                .password(passwordEncoder().encode("contraseña")) // Encriptar la contraseña
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(usuario);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uso de BCrypt para mayor seguridad
    }
}
```

### 5. Internacionalización
El sistema soporta múltiples idiomas mediante `MessageSource`.

#### Archivos de Mensajes
Archivo `messages.properties`:
```
welcome.message=Hello! Welcome to our internationalized application.
```
Archivo `messages_es.properties`:
```
welcome.message=¡Hola! Bienvenido a nuestra aplicación internacionalizada.
```

#### Configuración de Internacionalización
```java
package com.ismaelruge;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ConfiguracionInternacionalizacion {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
```

#### Controlador de Internacionalización
```java
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
```

## Instalación y Ejecución

1. Clonar el repositorio.
   ```bash
   git clone https://github.com/ismaelruge/Api_Java.git
   ```
2. Navegar al directorio del proyecto.
   ```bash
   cd Api_Java
   ```
3. Compilar y ejecutar con **Maven**.
   ```bash
   mvn spring-boot:run
   ```

## Endpoints Disponibles
| Método | Endpoint   | Acceso |
|---------|-----------|--------|
| GET     | /publico  | Libre  |
| GET     | /privado  | Requiere autenticación |
| GET     | /saludo   | Soporta internacionalización |

## Credenciales de Prueba
- **Usuario:** `usuario`
- **Contraseña:** `contraseña`

## Autor
Ismael Ruge Gonzalez