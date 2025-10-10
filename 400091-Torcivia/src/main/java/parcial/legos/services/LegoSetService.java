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
import java.nio.file.Files;
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
     * CSV esperado:
     * PROD_ID;SET_NAME;PROD_DESC;REVIEW_DIFFICULTY;PIECE_COUNT;STAR_RATING;LIST_PRICE;THEME_NAME;AGE_GROUP_CODE;COUNTRY_CODE
     */
    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
            .skip(1)
            .filter(line -> !line.trim().isEmpty())
            .forEach(line -> repo.create(procesarLinea(line)));
    }

    /** Convierte una línea del CSV a LegoSet (resuelve FKs por código/nombre correctos). */
    private LegoSet procesarLinea(String linea) {
        String[] t = linea.split(";", -1);

        LegoSet s = new LegoSet();
        s.setProductId(parseIntSafe(t[0]));
        s.setSetName(t[1].trim());
        s.setProductDescription(emptyToNull(t[2]));
        s.setReviewDifficulty(emptyToNull(t[3]));
        s.setPieceCount(parseIntSafe(t[4]));
        s.setStarRating(parseBigDecimalSafe(t[5]));
        s.setListPrice(parseBigDecimalSafe(t[6]));

        // THEMES.NAME (único por DDL)
        Theme theme = themeService.getOrCreateByName(t[7].trim());
        // AGE_GROUPS.CODE (único)
        AgeGroup ageGroup = ageGroupService.getOrCreateByCode(t[8].trim());
        // COUNTRIES.CODE (único)
        Country country = countryService.getOrCreateByCode(t[9].trim());

        s.setTheme(theme);
        s.setAgeGroup(ageGroup);
        s.setCountry(country);
        return s;
    }

    // --------- helpers de parseo ---------

    private static Integer parseIntSafe(String raw) {
        try { return raw == null || raw.isBlank() ? null : Integer.parseInt(raw.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    private static BigDecimal parseBigDecimalSafe(String raw) {
        try { return raw == null || raw.isBlank() ? null : new BigDecimal(raw.trim().replace(',', '.')); }
        catch (NumberFormatException e) { return null; }
    }

    private static String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
}
