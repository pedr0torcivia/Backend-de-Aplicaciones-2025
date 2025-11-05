// Importa el contenedor principal de Spring que maneja los beans
import org.springframework.context.ApplicationContext;
// Proporciona un contexto basado en configuración anotada y escaneo de
componentes
import
org.springframework.context.annotation.AnnotationConfigApplicationContext;
// Importa la clase del servicio a utilizar
import com.example.demo.SaludoService;
public class Main {
 public static void main(String[] args) {
 // Crea un contexto de aplicación escaneando el paquete indicado
 ApplicationContext context = new
AnnotationConfigApplicationContext("com.example.demo");
 // Solicita al contenedor una instancia del bean SaludoService
 SaludoService saludo = context.getBean(SaludoService.class);
 // Usa el servicio para mostrar un mensaje en consola
 System.out.println(saludo.saludar("Mundo"));
 }
}