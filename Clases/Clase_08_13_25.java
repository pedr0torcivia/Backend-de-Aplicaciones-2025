public class Clase_08_13_25 {
    public static void main(String[] args) {
        System.out.println("Hola buenas tardes"); 
        // Asignación Normal
        // TipoDato NombreVariable = ValorVariable
        String Nombre = "Pedro";
        String Apellido = "Torcivia";
        int Edad = 20;

        // Tambíen se puede realizar la asignación de valores de la siguiente manera:
        int a, b;
        a = 10;
        b = a;

        // Asignación de variables de distinto tipo
        // Casting: reducir el tamaño del tipo de dato

        // 1er Escenario: Ambos tipos de datos son iguales y no hay perdida de presición
        int c = 20, d = 5;
        c = d;

        // 2do Escenario: Variable izquierda mas grande. El valor cabe de sobra en el nuevo tipo, por lo que
        // se realiza una pormocion implicita o promoción de tipo. 
        short e = 12; 
        int f; 
        f = e;

        // 3er Escenario: Variable izquierda mas pequeña. Java obliga a usar casting explicito.
        // v1 = (TipoDato) v2
        int h = 1221344;
        short i;
        i = (short) h;

        // VAR --> El compilador determina automaticamente el tipo de variable
        var aderezo = "Mayonesa";
        var numero = 12312;
        System.out.println("Aderezo: " + aderezo + ", Numero: " + numero);

    }
}