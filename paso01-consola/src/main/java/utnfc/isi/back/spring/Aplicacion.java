// Permite usar la anotación @Autowired para la inyección automática de
dependencias
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// Indica que esta clase será registrada como Bean en el contenedor de
Spring
@Component
public class Aplicacion {
 // Dependencia inyectada: se declara como final para reforzar la inmutabilidad
 private final SaludoService saludoService;
 // Constructor con anotación @Autowired para inyección de la dependencia
 @Autowired
 public Aplicacion(SaludoService saludoService) {
 this.saludoService = saludoService;
 }
 // Método que utiliza la dependencia inyectada
 public void ejecutar() {
 System.out.println(saludoService.saludar("Felipe"));
 }
}
// Clase Principal
public class Main {
 public static void main(String[] args) {
 // Inicialización del contenedor Spring escaneando el paquete
 ApplicationContext context = new
AnnotationConfigApplicationContext("com.example.demo");
 // Obtención del bean de tipo Aplicacion, con las dependencias ya inyectadas
    Aplicacion app = context.getBean(Aplicacion.class);
    // Ejecución de la lógica principal de la aplicación
    app.ejecutar();
 }
}
