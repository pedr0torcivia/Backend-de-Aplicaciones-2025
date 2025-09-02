public class Autor {
    private String id;
    private String nombre;
    private String apellido;
    private int aniosCarrera; 

    public Autor(String id, String nombre, String apellido, int aniosCarrera){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.aniosCarrera = aniosCarrera; 
    }

    public boolean mas18(){
        if (aniosCarrera >= 18){
            return true;
        } else {
            return false;
        }
    }
}