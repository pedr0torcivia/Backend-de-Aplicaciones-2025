package parcial.legos.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DbInit {

    public static void run() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:legos;DB_CLOSE_DELAY=-1", "sa", ""
            );

            try (InputStream in = Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResourceAsStream("META-INF/ddl-legos.sql")) {

                if (in == null)
                    throw new IllegalStateException("No se encontró META-INF/ddl-legos.sql en el classpath");

                String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                try (Statement st = conn.createStatement()) {
                    st.execute(sql);
                }
            }

            conn.close();
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando H2 con DDL", e);
        }
    }
}
