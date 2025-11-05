import org.springframework.stereotype.Component;
// Anotación que indica que esta clase será gestionada como un Bean por el
// contenedor de Spring
@Component
public class SaludoService {
 // Método público que retorna un saludo personalizado
 public String saludar(String nombre) {
 return "Hola, " + nombre + "! Bienvenido a Spring.";
 }
}