package museos.services;

/** Importa CSV con columnas:
 *  0 NOMBRE_OBRA
 *  1 ANIO_CREACION
 *  2 AUTOR
 *  3 NOMBRE_MUSEO
 *  4 ESTILO_ARTISTICO
 *  5 MONTO_ASEGURADO
 *  6 SEGURO_TOTAL (1/0)
 */

import museos.entities.*;
import museos.repositories.ObraArtisticaRepository;
import museos.services.interfaces.IService;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ObraArtisticaService implements IService<ObraArtistica, Integer> {

    private final ObraArtisticaRepository repo;
    private final AutorService autorService;
    private final MuseoService museoService;
    private final EstiloArtisticoService estiloService;

    public ObraArtisticaService() {
        this.repo = new ObraArtisticaRepository();
        this.autorService = new AutorService();
        this.museoService = new MuseoService();
        this.estiloService = new EstiloArtisticoService();
    }

    public ObraArtisticaService(ObraArtisticaRepository repo,
                                AutorService autorService,
                                MuseoService museoService,
                                EstiloArtisticoService estiloService) {
        this.repo = repo;
        this.autorService = autorService;
        this.museoService = museoService;
        this.estiloService = estiloService;
    }

    @Override
    public ObraArtistica getById(Integer id) { return repo.getById(id); }

    @Override
    public ObraArtistica getOrCreateByName(String name) {
        if (name == null) return null;
        String n = name.trim().replaceAll("\\s+", " ");
        if (n.isEmpty()) return null;

        ObraArtistica o = repo.getByName(n);
        if (o == null) {
            o = new ObraArtistica();
            o.setNombre(n);
            repo.create(o);
        }
        return o;
    }

    @Override
    public List<ObraArtistica> getAll() { return repo.getAllList(); }

    @Override
    public Stream<ObraArtistica> getAllStream() { return repo.getAllStream(); }

    /** CSV: nombre, anio, autor, museo, estilo, montoAsegurado, seguroTotal(1/0) */
    public void bulkInsert(java.io.File csvFile) {
        if (this.existsAny()) {
            System.out.println("⚠ Ya hay obras cargadas en la BD. No se realizará la importación.");
            return;
        }

        try (Stream<String> lines = Files.lines(Paths.get(csvFile.toURI()), StandardCharsets.UTF_8)) {
            lines.skip(1)
                 .filter(l -> !l.trim().isEmpty())
                 .forEach(l -> {
                     ObraArtistica o = procesarLinea(l);
                     if (o != null) repo.create(o);
                 });
        } catch (Exception e) {
            throw new RuntimeException("Error importando obras desde CSV", e);
        }
    }

    public boolean existsAny() { return !repo.getAllList().isEmpty(); }

    public ObraArtistica procesarLinea(String linea) {
        String[] c = linea.split(",", -1);
        if (c.length < 7) {
            System.out.println("⚠ Línea inválida (7 columnas esperadas): " + linea);
            return null;
        }

        String nombre       = c[0].trim();
        String anio         = c[1].trim(); // si la entidad usa Integer, parsear aquí
        String autorNombre  = c[2].trim();
        String museoNombre  = c[3].trim();
        String estiloNombre = c[4].trim();
        String montoStr     = c[5].trim();
        String seguroStr    = c[6].trim();

        if (nombre.isEmpty()) {
            System.out.println("⚠ Línea inválida (nombre vacío): " + linea);
            return null;
        }

        Autor autor = autorNombre.isEmpty() ? null : autorService.getOrCreateByName(autorNombre);
        Museo museo = museoNombre.isEmpty() ? null : museoService.getOrCreateByName(museoNombre);
        EstiloArtistico estilo = estiloNombre.isEmpty() ? null : estiloService.getOrCreateByName(estiloNombre);

        Double montoAsegurado;
        try {
            montoAsegurado = montoStr.isEmpty() ? 0.0 : Double.parseDouble(montoStr);
        } catch (NumberFormatException e) {
            montoAsegurado = 0.0;
        }

        boolean seguroTotal = "1".equals(seguroStr);

        ObraArtistica o = new ObraArtistica();
        o.setNombre(nombre);
        o.setAnio(anio);
        o.setMontoAsegurado(montoAsegurado);
        o.setSeguroTotal(seguroTotal);
        o.setAutor(autor);
        o.setMuseo(museo);
        o.setEstilo(estilo);

        return o;
    }

    public Object[] calcularMontoTotalAsegurado(){
        return repo.findMontoTotal();
    }

    public void generarReporteObrasPorEstilo(String rutaArchivo) {
        List<Object[]> resultados = repo.findCantidadObrasPorEstilo();

        if (resultados.isEmpty()) {
            System.out.println("⚠ No hay obras registradas para generar el reporte.");
            return;
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write("Estilo,Cantidad\n");
            for (Object[] fila : resultados) {
                String estilo = (String) fila[0];
                Long cantidad = (Long) fila[1];
                writer.write(estilo + "," + cantidad + "\n");
            }
            System.out.println("✅ Reporte generado correctamente en: " + rutaArchivo);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el archivo de reporte", e);
        }
    }

    public List<ObraArtistica> obtenerObrasConSeguroParcialYPorEncimaDelPromedio(){
        return repo.findObrasConSeguroParcialYPorEncimaDelPromedio();
    }

    public List<ObraArtistica> getByMuseoNombre(String nombreMuseo) {
        if (nombreMuseo == null || nombreMuseo.trim().isEmpty()) return List.of();
        return repo.findByMuseoNombre(nombreMuseo.trim());
    }
}
