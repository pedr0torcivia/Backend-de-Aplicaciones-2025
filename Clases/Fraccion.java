public class Fraccion{

    // Atributos
    private int numerador;
    private int denominador;


    // Constructores
    public Fraccion(int num, int den) {
        numerador = num;
        setDenominador(den);
    }

    public Fraccion(int num) {
        this(num, 1);
    }

    public Fraccion(Fraccion aCopiar){
        this(aCopiar.numerador, aCopiar.denominador);
    }

    public int getNumerador() {
        return numerador;
    }

    public int getDenominador() {
        return denominador; 
    }

    public double valorReal() {
        double resp = numerador / (double) denominador;
        return resp;
    }

    public String toString() {
        return "("+ this.numerador+"/"+this.denominador+")";
    }

    public void setDenominador(int den) {
        if (den == 0) {
            throw new IllegalArgumentException("El denominador no puede ser cero.");
        }
        denominador = den; 
    }


    public static void main(String[] args) {
        Fraccion f1; 
        f1  = new Fraccion(2, 3);
        System.out.println(f1.valorReal());
        System.out.println(f1.toString());
    }
}
