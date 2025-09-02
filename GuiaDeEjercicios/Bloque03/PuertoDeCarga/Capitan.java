public class Capitan {

    private String nombre;
    private String apellido;
    private String identificador;
    private int antiguedad; 

    public Capitan(String nombre, String apellido, String identificador, int antiguedad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificador = identificador;
        this.antiguedad = antiguedad;
    }

    @Override
    public String toString(){
    return "Nombre: " + nombre 
         + " | Apellido: " + apellido 
         + " | Identificador: " + identificador 
         + " | Antiguedad: " + antiguedad;
    }

    public Boolean esMayor18(){
        return antiguedad > 18;
    }
}