package ar.edu.utnfrc.backend;

public class Division implements Operacion{
    @Override
    public float calcular(float a, float b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir por cero...");
        }
        return a / b; 
        }
    }
