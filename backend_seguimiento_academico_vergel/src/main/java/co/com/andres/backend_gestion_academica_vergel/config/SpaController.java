package co.com.andres.backend_gestion_academica_vergel.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Reenvía las rutas del frontend Angular (SPA) al {@code index.html}.
 *
 * <p>Angular maneja la navegación del lado del cliente. Cuando el usuario
 * recarga la página o entra directamente a una ruta como {@code /alertas},
 * el navegador la pide al servidor; sin este reenvío, Spring respondería 404.
 * Aquí esas rutas se reenvían al {@code index.html}, que arranca Angular y
 * muestra la vista correcta.</p>
 *
 * <p>Las rutas de la API ({@code /api/**}) y de Swagger no se ven afectadas,
 * porque no coinciden con los patrones declarados abajo.</p>
 *
 * @author Andres
 * @version 1.0
 * @since 2026
 */
@Controller
public class SpaController {

    /**
     * Reenvía las rutas conocidas del frontend al index de Angular.
     *
     * @return instrucción de reenvío interno a {@code /index.html}
     */
    @RequestMapping({
        "/login",
        "/alertas",
        "/profesores",
        "/estudiantes",
        "/acudientes",
        "/grados",
        "/materias",
        "/matriculas",
        "/notas",
        "/asistencias"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
