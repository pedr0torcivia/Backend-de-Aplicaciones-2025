public class Biblioteca {
    private Libro[] libros;

    public void setLibros(Libro[] libros){
        this.libros = libros;
    }

    public double recaudacionEstimada(){
        double acu = 0;
        for (int i = 0; i < libros.length; i++){
            acu += libros[i].getPrecioPorDia() * 15;
        }
        return acu;
    }

    public int cantidadAutores(){
        int cant = 0;
        for (int i = 0; i < libros.length; i++){
            if (libros[i].getAutor().mas18()){
                cant += 1; 
            }
        }
        return cant; 
    }

    public double promedioPags() {
        int sumaPaginas = 0;
        int contador = 0; 
        for (int i = 0; i < libros.length; i++){
            if (libros[i] != null && libros[i].esEstantePar()){
                sumaPaginas += libros[i].getPaginas();
                contador++; 
            }
        }
        if (contador == 0){
            return 0;
        } else {
            return (double) sumaPaginas / contador;
        }
    }
}