import java.util.ArrayList;
import java.util.List;


public class Puerto {
    
    private List<Barco> barcos;

    public void setBarcos(List<Barco> barcos) {
        this.barcos = barcos;
    }

    @Override
    public String toString() {
        String linea = "";
        for (int i = 0; i < barcos.size(); i++) {
            linea += barcos.get(i) + "\n";
        }
        return linea; 
    }

    public float calcRecaudacion(List<Barco> barcos) {
        float acuCosto = 0;
        for (int i = 0; i < barcos.size(); i++) {
            Barco barco = barcos.get(i);
            acuCosto += barco.getCostAlq() * 15;
        } return acuCosto;
    }

    public String barcos18(List<Barco> barcos) {
        String lista = "";
        for (int i = 0; i < barcos.size(); i++) {
            Barco barco = barcos.get(i);
            Capitan capitan = barco.getCapitan();

            if (capitan.esMayor18()) {
                lista += barco.toString() + "\n";
            }
        } return lista;
    }

    public float cargaPromedio(List<Barco> barcos) {
        int cont = 0;
        float acu = 0;

        for (int i = 0; i < barcos.size(); i++) {
            Barco barco = barcos.get(i);
            if (barco.getNumMuelle() % 2 == 0) {
                cont ++;
                acu += barco.getCapCarga(); 
            }
        }

        if (cont != 0) {
            return acu / cont; 
        } else {
            return 0;
        }
    }
}