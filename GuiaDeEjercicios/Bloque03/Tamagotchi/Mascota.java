public class Mascota {

    private int energia;
    private int humor;
    private String nombre;

    private int cantIngestas;
    private int cantActividades;  
    private boolean vivo;
    private boolean durmiendo;

    // Constructor
    public Mascota(String nombre, int energia, int humor) {
        this.nombre = nombre;
        this.energia = Math.min(Math.max(energia, 0), 100);
        this.humor = Math.min(Math.max(humor, 1), 5);
        this.vivo = true;
        this.durmiendo = false;
        this.cantIngestas = 0;
        this.cantActividades = 0;
    }

    private void verificarEstado() {
        if (energia <= 0) {
            energia = 0;
            vivo = false;
            durmiendo = false;
            System.out.println(nombre + " se murío (F).");
        }
        if (humor <= 0 && vivo) {
            humor = 1;
            durmiendo = true;
            System.out.println(nombre + " se durmió por molesto.");
        }
    }

    private void ajustarEnergia(int cambio) {
        energia += cambio;
        if (energia > 100) energia = 100;
        if (energia < 0) energia = 0;
        verificarEstado();
    }

    private void ajustarHumor(int cambio) {
        humor += cambio;
        if (humor > 5) humor = 5;
        if (humor < 1) humor = 1;
        verificarEstado();
    }


    public boolean comer() {
        if (!vivo || durmiendo) return false;

        this.cantIngestas += 1;
        this.cantActividades = 0; 
        
        if (cantIngestas == 5) {      
            vivo = false;  
            System.out.println("El bicho se puso gordo y murío de obesidad...");
            return false;
        } else {
            int incEnergia = (int)(energia*0.1);
            ajustarEnergia(incEnergia);

            int incHumor = 1;
            ajustarHumor(incHumor);
        }
        System.out.println("Se ha finalizado la actividad: Comer");
        return true; 
    }

    public boolean beber() {
        if (!vivo || durmiendo) return false;

        this.cantIngestas += 1;
        this.cantActividades = 0; 

        if (cantIngestas == 5) {     
            vivo = false;    
            System.out.println("El bicho se puso gordo y murió de obesidad...");
            return false; 
        } else {
            int incEnergia = (int)(energia*0.05);
            ajustarEnergia(incEnergia);

            int incHumor = 1;
            ajustarHumor(incHumor);
        }
        System.out.println("Se ha finalizado la actividad: Beber");
        return true;   
        }
 
    
    public boolean saltar() {
        if (!vivo || durmiendo) return false;

        this.cantIngestas = 0;
        this.cantActividades += 1;

        if (cantActividades == 3) {
            durmiendo = true;        
            System.out.println("El bicho se puso a entrenar y se durmió de un espasmo...");
            return false;
        } else {
            int disEnergia = (int)(energia * 0.15);
            ajustarEnergia(-disEnergia);
            ajustarHumor(-2);           
            }
        System.out.println("Se ha finalizado la actividad: Saltar");
        return true;
    }

    public boolean correr() {
        if (!vivo || durmiendo) return false;

        this.cantIngestas = 0;
        this.cantActividades += 1;

        if (cantActividades == 3) {       
            durmiendo = true; 
            System.out.println("El bicho se puso a entrenar y se durmió de un espasmo...");
            return false; 
        } else {
            int disEnergia = (int)(energia * 0.35);
            ajustarEnergia(-disEnergia);
            ajustarHumor(-2);   
            }
        System.out.println("Se ha finalizado la actividad: Correr");
        return true; 
    }
    
    public boolean dormir(){
        if (!vivo || durmiendo) return false;
        durmiendo = true;
        ajustarEnergia(25);
        ajustarHumor(2);
        System.out.println("Durmiendo, no jodas...");
        return true;
    }

    public boolean despertar(){
        if (!vivo || !durmiendo) return false;
        durmiendo = false;
        ajustarHumor(-1);
        System.out.println("Despertandome...");
        return true; 
       }


    @Override
    public String toString() {
        String estado = durmiendo ? "Durmiendo" : "Despierto";
        String humorStr = switch(humor) {
            case 1 -> "Muy enojado";
            case 2 -> "Enojado";
            case 3 -> "Neutral";
            case 4 -> "Contento";
            case 5 -> "Chocho";
            default -> "Desconocido";
        };
        String vida = vivo ? "Vivo" : "Muerto";

        return "Mascota: " + nombre +
                " | Energía: " + energia +
                " | Humor: " + humorStr +
                " | Estado: " + estado +
                " | Vida: " + vida;
    }
}
