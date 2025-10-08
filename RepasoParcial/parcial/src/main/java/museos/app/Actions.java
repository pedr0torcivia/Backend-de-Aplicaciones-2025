package museos.app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import museos.entities.Museo;
import museos.entities.ObraArtistica;
import museos.services.MuseoService;
import museos.services.ObraArtisticaService;

public class Actions {

    public void importarObras(AppContext context) {
        URL csvUrl = (URL) context.get("csvUrl");
        if (csvUrl == null) {
            throw new IllegalStateException("No se encontró la URL del CSV en el contexto.");
        }

        var obraSrv = context.getService(ObraArtisticaService.class);

        try {
            // Si corre desde clases (file:) -> usar File directo
            if (csvUrl.getProtocol().startsWith("file")) {
                File f = new File(csvUrl.toURI());
                obraSrv.bulkInsert(f);
                System.out.println("✔ Importación OK: " + f.getName());
                return;
            }

            // Si alguna vez corre empacado en JAR (jar:) -> copiar a temp y leer
            try (var in = Actions.class.getResourceAsStream("/files/games_data.csv")) {
                if (in == null) throw new IllegalArgumentException("No se pudo abrir /files/games_data.csv");
                var tmp = java.nio.file.Files.createTempFile("games_data", ".csv").toFile();
                java.nio.file.Files.copy(in, tmp.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                obraSrv.bulkInsert(tmp);
                System.out.println("✔ Importación OK (modo JAR).");
            }
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void listarObras(AppContext context) {
        var service = context.getService(ObraArtisticaService.class);
        var obras = service.getAll();

        if (obras.isEmpty()) {
            System.out.println("⚠ No hay obras registradas en la base de datos.");
        } else {
            System.out.println("📜 Lista de obras artísticas:");
            obras.forEach(obra -> {
                String autor  = obra.getAutor()  != null ? obra.getAutor().getNombre()  : "Desconocido";
                String estilo = obra.getEstilo() != null ? obra.getEstilo().getNombre() : "Desconocido";
                String museo  = obra.getMuseo()  != null ? obra.getMuseo().getNombre()  : "Desconocido";
                
                System.out.printf(
                    "ID: %d | Nombre: %s | Año: %s | Autor: %s | Estilo: %s | Museo: %s | Monto asegurado: %.2f | Seguro total: %s%n",
                    obra.getId(),
                    obra.getNombre(),
                    obra.getAnio(),
                    autor,
                    estilo,
                    museo,
                    obra.getMontoAsegurado(),
                    obra.getSeguroTotal() ? "Sí" : "No"
                );
            });
        }
    }

    public void determinarMontoTotal(AppContext context) {
        var service = context.getService(ObraArtisticaService.class);
        var montoTotal = service.calcularMontoTotalAsegurado();

        System.out.printf("El monto total asegurado por todas las obras es: %.2f%n", montoTotal[0]);
    }

    public void generarTxtPorEstilo(AppContext context) {
        var service = context.getService(ObraArtisticaService.class);
        service.generarReporteObrasPorEstilo("reporte_estilos.txt");
    }

    public void mostrarObrasConSeguroParcial(AppContext context) {
        var service = context.getService(ObraArtisticaService.class);
        var obras = service.obtenerObrasConSeguroParcialYPorEncimaDelPromedio(); 

        if (obras.isEmpty()) {
            System.out.println("⚠ No hay obras con seguro por daño parcial por encima del promedio.");
            return;
        }

        System.out.println("Obras con seguro por daño parcial y monto > promedio (ordenadas por año desc):");
        obras.forEach(o -> {
            String autor  = o.getAutor()  != null ? o.getAutor().getNombre()  : "Desconocido";
            String estilo = o.getEstilo() != null ? o.getEstilo().getNombre() : "Desconocido";
            String museo  = o.getMuseo()  != null ? o.getMuseo().getNombre()  : "Desconocido";
            System.out.printf("ID: %d | %s (%s) | Autor: %s | Estilo: %s | Museo: %s | Monto: %.2f%n",
                    o.getId(), o.getNombre(), o.getAnio(), autor, estilo, museo, o.getMontoAsegurado());
        });
    }

    public void mostrarObrasPorMuseo(AppContext context) {
    var museoSrv = context.getService(MuseoService.class);
    var obraSrv  = context.getService(ObraArtisticaService.class);

    // 1) Traer museos y validar
    List<Museo> museos = museoSrv.getAll();
    if (museos.isEmpty()) {
        System.out.println("⚠ No hay museos cargados.");
        return;
    }

    // 2) Mostrar opciones numeradas
    System.out.println("Seleccione un museo:");
    for (int i = 0; i < museos.size(); i++) {
        System.out.printf("%2d) %s%n", i + 1, museos.get(i).getNombre());
    }

    // 3) Leer opción por teclado con validación básica
    Scanner sc = new Scanner(System.in);
    int opcion = -1;
    while (true) {
        System.out.print("Ingrese el número del museo: ");
        String in = sc.nextLine();
        try {
            opcion = Integer.parseInt(in);
            if (opcion >= 1 && opcion <= museos.size()) break;
        } catch (NumberFormatException ignored) {}
        System.out.println("Opción inválida. Intente nuevamente.");
    }

    // 4) Obtener el nombre del museo elegido
    String nombreMuseo = museos.get(opcion - 1).getNombre();

    // 5) Consultar obras por service
    List<ObraArtistica> obras = obraSrv.getByMuseoNombre(nombreMuseo);

    // 6) Mostrar resultado
    if (obras.isEmpty()) {
        System.out.println("⚠ No se encontraron obras para el museo: " + nombreMuseo);
        return;
    }

    System.out.println("Obras del museo: " + nombreMuseo);
    obras.forEach(o -> {
        String autor  = o.getAutor()  != null ? o.getAutor().getNombre()  : "Desconocido";
        String estilo = o.getEstilo() != null ? o.getEstilo().getNombre() : "Desconocido";
        System.out.printf("- %s | Autor: %s | Estilo: %s%n",
                o.getNombre(), autor, estilo);
    });
}
}