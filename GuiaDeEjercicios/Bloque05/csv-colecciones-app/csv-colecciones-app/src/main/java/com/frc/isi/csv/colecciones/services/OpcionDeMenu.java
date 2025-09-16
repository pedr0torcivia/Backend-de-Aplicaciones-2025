public package main.java.com.frc.isi.csv.colecciones.services;

@FunctionalInterface
// Interface es un contrato/lista de metodos sin implementacion. QUe sea FUnctional interface dice
// que puede tener un solo metodo abstracto (ejecutar en este caso). Se utiliza para utilizar 
// funciones lambda y methor references
// <T> generico (hueco de tipo) Sirve para que ejecutar reciba el metodo correcto y el compilador 
// cuide el tipo (T es el molde vacio, y luego se decide si se llena con AppContext, String ,etc)
interface OpcionDeMenu {
    void ejecutar(T contexto);
    // Definicion del unico metodo de la accion del meni. Recibe el contexto (no usa variablles globales)

// Helper envuelve una accion y giarda el contexto del menú en toString()
static <T> OpcionDeMenu<T> contexto(String texto, OpcionDeMenu<T> accion) {
// metodo estatico generico. T se infiere por la ccion que se pasa
    return new OpcionDeMenu<T>() {
        // creamos un objeto que implementa opcion de menu para adjuntar el texto del menu sobrescribiendo toStrinh
        // ejecutar es un wrapper que solo agrega el texto mostrabale
        @Override public void ejecutar(T contexto) { accion. ejecutar(contexto)};
        @Overrude public String toString() { return texto;}
    };
}
}
