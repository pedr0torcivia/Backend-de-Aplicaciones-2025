public class Barco {
    private String matricula;
    private int numMuelle;
    private float capCarga;
    private float costoAlq;
    private Capitan capitan; 

    public Barco(String matricula, int numMuelle, float capCarga, float costoAlq, Capitan capitan) {
        this.matricula = matricula;
        this.numMuelle = numMuelle;
        this.capCarga = capCarga;
        this.costoAlq = costoAlq;
        this.capitan = capitan; 
    }

    @Override
    public String toString() {
    String linea = "Matricula: " + matricula 
                 + "| numMuelle: " + numMuelle 
                 + "| capCarga: " + capCarga 
                 + "| costoAlq: " + costoAlq 
                 + " | Capitan: " + capitan.toString();
    return linea; }

    public float getCostAlq() {
        return costoAlq;
    }

    
    public float getCapCarga() {
        return capCarga;
    }


    public int getNumMuelle() {
        return numMuelle;
    }
    
    public Capitan getCapitan() {
        return capitan;
    }
}