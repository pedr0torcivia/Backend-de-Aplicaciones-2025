package ar.edu.utnfc.backend;

import org.junit.jupiter.api.Test;

public class BarcoTest {

    @Test
    public void seCreaExitosamenteUnBarco() {
        // Arrange
        String cap = "Pepe Popeye";
        int matricula = "1235";
        int darsema = 1;
        double carga = 10;

        // Act
        Barco barco = new Barco(matricula, darsema, cap, carga);

        // Assert
        assertNotNull(b);
        assertEqual(b.getNombreCapitan(), cap);
    }

}