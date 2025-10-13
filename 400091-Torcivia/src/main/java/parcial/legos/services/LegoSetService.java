package parcial.legos.services;

import parcial.legos.entities.AgeGroup;
import parcial.legos.entities.Country;
import parcial.legos.entities.LegoSet;
import parcial.legos.entities.Theme;
import parcial.legos.repositories.LegoSetRepository;
import parcial.legos.services.interfaces.IService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LegoSetService implements IService<LegoSet, Integer> {

    private final LegoSetRepository repo;
    private final AgeGroupService ageGroupService;
    private final ThemeService themeService;
    private final CountryService countryService;

    public LegoSetService() {
        this.repo = new LegoSetRepository();
        this.ageGroupService = new AgeGroupService();
        this.themeService = new ThemeService();
        this.countryService = new CountryService();
    }

    public LegoSetService(LegoSetRepository repo,
                          AgeGroupService ageGroupService,
                          ThemeService themeService,
                          CountryService countryService) {
        this.repo = repo;
        this.ageGroupService = ageGroupService;
        this.themeService = themeService;
        this.countryService = countryService;
    }

    @Override
    public LegoSet getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public List<LegoSet> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<LegoSet> getAllStream() {
        return repo.getAllStream();
    }

    public LegoSet findOrNewByProductId(Integer productId) {
        if (productId == null) return null;
        LegoSet s = repo.getByProductId(productId);
        if (s == null) {
            s = new LegoSet();
            s.setProductId(productId);
        }
        return s;
    }

     // 1ER PUNTO 
    public long obtenerCantidadSets() {
        return repo.countSets();
    }

    public long obtenerCantidadRangosEdad() {
        return repo.countAgeGroups();
    }

    public long obtenerCantidadTematicas() {
        return repo.countThemes();
    }

    // 2DO PUNTO
    public List<Object[]> obtenerRankingPaisesPorCostoValoracion() {
        return repo.top5CountriesByCostPerStar();
    }

    // 3ER PUNTO
    public List<LegoSet> buscarSetsPorEdadPrecioYRating(int edad, BigDecimal maxPrice) {
        if (edad < 0) 
            throw new IllegalArgumentException("La edad no puede ser negativa");
        if (maxPrice == null) 
            throw new IllegalArgumentException("Debe especificarse un precio máximo");

        // Paso 1: traer candidatos desde DB (solo precio y rating)
        List<LegoSet> candidatos = repo.buscarSetsPorPrecioYRating(maxPrice);

        // Paso 2: filtrar en memoria usando matchesAge
        return candidatos.stream()
                .filter(s -> s.getAgeGroup() != null && s.getAgeGroup().matchesAge(edad))
                .sorted((a, b) -> b.getListPrice().compareTo(a.getListPrice())) // mayor a menor
                .collect(Collectors.toList());
    }

// ======== IMPORTACIÓN MASIVA DESDE CSV (SIEMPRE INSERTA, AUN CON prod_id REPETIDO) ========

    public void bulkInsert(File fileToImport) throws IOException {
        try (var lines = java.nio.file.Files.lines(Paths.get(fileToImport.toURI()))) {
            var it = lines
                    .skip(1) // saltea encabezado
                    .map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .iterator();

            while (it.hasNext()) {
                String linea = it.next();
                procesarLinea(linea); // cada línea -> INSERT nuevo
            }
        }
    }

// ---------- procesamiento de una línea (INSERT SIEMPRE) ----------

    private void procesarLinea(String linea) {
        String[] t = linea.split(";", -1);
        if (t.length < 13) return;

        // 1) Ignorar si hay algún campo vacío (inclusive los no usados)
        for (String v : t) {
            if (v == null || v.isBlank()) return;
        }

        // 2) Parseos y validaciones
        String agesCode = t[0].trim();

        BigDecimal listPrice = parseBigDecimalSafe(t[1]);
        if (listPrice == null) return;

        Integer pieceCount = parseIntFromDecimalString(t[3]);
        if (pieceCount == null) return;

        String prodDesc = t[5].trim();

        Integer pid = parseProductId(t[6]);
        if (pid == null) return;

        String reviewDifficulty = t[7].trim();
        String setName = t[8].trim();

        BigDecimal starRating = parseBigDecimalSafe(t[9]);
        if (starRating == null) return;
        if (starRating.compareTo(BigDecimal.ZERO) < 0 || starRating.compareTo(new BigDecimal("5.0")) > 0) return;

        String themeName = t[10].trim();
        String countryCode = t[12].trim();

        // 3) País debe existir previamente
        Country country = countryService.getByCode(countryCode);
        if (country == null) return;

        // 4) Theme y AgeGroup se crean si no existen
        Theme theme = themeService.getOrCreateByName(themeName);
        AgeGroup ageGrp = ageGroupService.getOrCreateByCode(agesCode);

        // 5) SIEMPRE crear nuevo LegoSet (sin buscar por productId)
        LegoSet s = new LegoSet();
        s.setProductId(pid);                 // puede repetirse entre filas
        s.setSetName(setName);
        s.setProductDescription(prodDesc);
        s.setReviewDifficulty(reviewDifficulty);
        s.setPieceCount(pieceCount);
        s.setStarRating(starRating);
        s.setListPrice(listPrice);
        s.setTheme(theme);
        s.setAgeGroup(ageGrp);
        s.setCountry(country);

        repo.create(s); // persist SIEMPRE -> nuevo registro
    }

    // ------- helpers numéricos -------

    private BigDecimal parseBigDecimalSafe(String s) {
        if (s == null) return null;
        try { return new BigDecimal(s.trim()); }
        catch (Exception e) { return null; }
    }

    private Integer parseIntFromDecimalString(String s) {
        if (s == null) return null;
        String x = s.trim();
        if (x.matches("\\d+(\\.0+)?")) {
            return Integer.parseInt(x.split("\\.")[0]);
        }
        try { return new BigDecimal(x).intValueExact(); }
        catch (Exception ex) { return null; }
    }

    private Integer parseProductId(String s) {
        return parseIntFromDecimalString(s);
    }
}
