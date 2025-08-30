public class Cafetera {

    private String marca; 
    private String modelo;
    private int capacidadMaxima;
    private int contenidoActual;
    private boolean encendida = false;
    private int cafesServidos = 0;
    private int temperatura; 

    public Cafetera(String marca, String modelo, int capacidadMaxima, int contenidoActual, int temperatura){
        this.marca = marca; 
        this.modelo = modelo;
        this.capacidadMaxima = capacidadMaxima;
        this.contenidoActual = contenidoActual;
        this.temperatura = temperatura; 
    }

    public void encender() {
        encendida = true;
        temperatura = 20;
        contenidoActual = 0;
    }

    public void apagar() {
        encendida = false;
        cafesServidos = 0;
    }

    public void cargarAgua(int ml) {
        if (encendida) {
            if (contenidoActual == capacidadMaxima) {
                System.out.println("La Cafetera ya está llena");
            } else if ( (contenidoActual + ml) >= capacidadMaxima) {
                contenidoActual = capacidadMaxima;
            } else {
                contenidoActual += ml;
            }
        }
    }

    public void calentar() {
        if (encendida) {
            if ((temperatura + 40) >= 100) {
                temperatura = 100;
            } else {
                temperatura += 40;
            }
        }
    }

    public boolean servirCafe() {
        if (encendida && (contenidoActual >= 100) && (temperatura >= 90)) {
            cafesServidos++;
            contenidoActual -= 100;
            return true;
        } else {
            return false;
        }
    }

    @Override 
    public String toString() {
            String estado = (encendida) ? "Encendida" : "Apagada";
            String linea = "Cafetera: " + marca  
                + " | Modelo: " + modelo 
                + " | Agua: " + contenidoActual + "ml"
                + " | Temperatura: " + temperatura + "°C"
                + " | Estado: " + estado  
                + " | Servidos: " + cafesServidos;
    return linea; }

    }