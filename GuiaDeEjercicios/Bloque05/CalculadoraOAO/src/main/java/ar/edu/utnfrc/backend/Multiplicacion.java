package ar.edu.utnfrc.backend;

public class Multiplicacion implements Operacion {
    @Override
    public float calcular(float a, float b) {
        return a * b;
    }
}
