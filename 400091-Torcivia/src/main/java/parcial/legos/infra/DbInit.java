package parcial.legos.infra;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbInit {
  private static final String URL = "jdbc:h2:mem:legos;DB_CLOSE_DELAY=-1";
  private static final String USER = "sa";
  private static final String PASS = "";

  private DbInit() {}

  public static void run() throws SQLException, IOException {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
      exec(conn, "src/main/resources/META-INF/ddl-legos.sql");
    }
  }

  private static void exec(Connection conn, String file) throws IOException, SQLException {
    String sql = Files.readString(Path.of(file), StandardCharsets.UTF_8);
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }
}

