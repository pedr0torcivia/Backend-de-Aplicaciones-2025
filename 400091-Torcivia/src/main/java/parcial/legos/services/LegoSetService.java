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
    /**
     * CSV:
     * PROD_ID;SET_NAME;PROD_DESC;REVIEW_DIFFICULTY;PIECE_COUNT;STAR_RATING;LIST_PRICE;THEME_NAME;AGE_GROUP_CODE;COUNTRY_CODE
     */
    public LegoSet findOrNewByProductId(Integer productId) {
        if (productId == null) return null;
        LegoSet s = repo.getByProductId(productId);
        if (s == null) {
            s = new LegoSet();          // ← solo en memoria
            s.setProductId(productId);  // aún NO persistimos
        }
        return s;
    }

    /**
     * CSV:
     * PROD_ID;SET_NAME;PROD_DESC;REVIEW_DIFFICULTY;PIECE_COUNT;STAR_RATING;LIST_PRICE;THEME_NAME;AGE_GROUP_CODE;COUNTRY_CODE
     */
    public void bulkInsert(File fileToImport) throws IOException {
        try (var lines = java.nio.file.Files.lines(Paths.get(fileToImport.toURI()))) {
            lines.skip(1)
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .forEach(this::procesarLinea); // acá NO repo.create/update
        }
    }

    private void procesarLinea(String linea) {
        String[] t = linea.split(";", -1);
        if (t.length < 10) return;

        Integer pid = parseIntSafe(t[0]);
        if (pid == null) return;

        // Catalogos: estos sí pueden hacer get-or-create con persistencia adentro
        Theme theme     = themeService.getOrCreateByName(emptyToNull(t[7]));
        AgeGroup ageGrp = ageGroupService.getOrCreateByCode(emptyToNull(t[8]));
        Country country = countryService.getOrCreateByCode(emptyToNull(t[9]));

        // Buscar existente o crear uno NUEVO en memoria (sin persistir)
        LegoSet s = findOrNewByProductId(pid);
        boolean isNew = (s.getId() == null); // si no tiene PK, aún no existe en DB

        // Completar TODOS los campos antes de tocar la DB
        s.setSetName(emptyToNull(t[1]));
        s.setProductDescription(emptyToNull(t[2]));
        s.setReviewDifficulty(emptyToNull(t[3]));
        s.setPieceCount(parseIntSafe(t[4]));
        s.setStarRating(parseBigDecimalSafe(t[5]));
        s.setListPrice(parseBigDecimalSafe(t[6]));
        s.setTheme(theme);
        s.setAgeGroup(ageGrp);   // ← NOT NULL en DDL: debe estar seteado
        s.setCountry(country);   // ← NOT NULL en DDL

        // recién ahora persistimos
        if (isNew) {
            repo.create(s);
        } else {
            repo.update(s);
        }
    }

    // ------- helpers -------
    private static Integer parseIntSafe(String raw) {
        try { return (raw == null || raw.isBlank()) ? null : Integer.parseInt(raw.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    private static BigDecimal parseBigDecimalSafe(String raw) {
        try { return (raw == null || raw.isBlank()) ? null : new BigDecimal(raw.trim().replace(',', '.')); }
        catch (NumberFormatException e) { return null; }
    }

    private static String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}

