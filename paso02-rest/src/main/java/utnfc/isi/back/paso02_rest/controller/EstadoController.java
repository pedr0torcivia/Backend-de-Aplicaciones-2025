package utnfc.isi.back.paso02_rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EstadoController {

    @Value("${server.port}")
    private String puerto;

    @GetMapping("/estado")
    public String estado() {
        return "Servidor funcionando correctamente en el puerto " + puerto + ".";
    }

    @GetMapping("/saludo")
    public String saludo(@RequestParam(defaultValue = "Anónimo") String nombre) {
        return "Hola " + nombre + ", que tengas un día excelente!";
    }
}
