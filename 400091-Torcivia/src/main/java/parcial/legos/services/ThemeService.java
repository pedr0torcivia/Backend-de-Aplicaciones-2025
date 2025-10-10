package parcial.legos.services;

import parcial.legos.entities.Theme;
import parcial.legos.repositories.ThemeRepository;
import parcial.legos.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class ThemeService implements IService<Theme, Integer> {

    private final ThemeRepository repo;

    public ThemeService() {
        this.repo = new ThemeRepository();
    }

    public ThemeService(ThemeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Theme getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public List<Theme> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Theme> getAllStream() {
        return repo.getAllStream();
    }

    /** CSV → THEMES.NAME (único) */
    public Theme getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Theme t = repo.getByName(normalized);
        if (t == null) {
            t = new Theme();
            t.setName(normalized);
            repo.create(t);
        }
        return t;
    }
}
