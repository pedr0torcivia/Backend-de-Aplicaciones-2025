package parcial.legos.services;

import parcial.legos.entities.Country;
import parcial.legos.repositories.CountryRepository;
import parcial.legos.services.interfaces.IService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class CountryService implements IService<Country, Integer> {

    private final CountryRepository repo;

    public CountryService() {
        this.repo = new CountryRepository();
    }

    public CountryService(CountryRepository repo) {
        this.repo = repo;
    }

    @Override
    public Country getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public List<Country> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Country> getAllStream() {
        return repo.getAllStream();
    }

    /** CSV → COUNTRIES.CODE (ej. US, GB, DE). Si no existe en seed, lo crea con name=code. */
    public Country getOrCreateByCode(String code) {
        if (code == null) return null;
        String normalized = code.trim().toUpperCase(Locale.ROOT);
        if (normalized.isEmpty()) return null;

        Country c = repo.getByCode(normalized);
        if (c == null) {
            c = new Country();
            c.setCode(normalized);
            c.setName(normalized); // fallback simple si no está en seed
            repo.create(c);
        }
        return c;
    }

    public Country getByCode(String code) {
        if (code == null || code.isBlank()) return null;
        return repo.getByCode(code.trim().toUpperCase());
    }
}
