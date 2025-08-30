public class Libro {
    private String isbn;
    private String titulo; 
    private int nroEstante; 
    private int paginas;
    private double precioPorDia;
    private Autor autor; 

    public Libro(String isbn, String titulo, int nroEstante, int paginas, double precioPorDia, Autor autor){
        this.isbn = isbn;
        this.titulo = titulo;
        this.nroEstante = nroEstante;
        this.paginas = paginas;
        this.precioPorDia = precioPorDia;
        this.autor = autor; 
    }

    public double getPrecioPorDia(){
        return precioPorDia;
    }

    public int getPaginas(){
        return paginas;
    }

    public Autor getAutor(){
        return autor;
    }

    public boolean esEstantePar(){
        if (nroEstante % 2 == 0) {
            return true;
        } else {
            return false; 
        }
    }
}