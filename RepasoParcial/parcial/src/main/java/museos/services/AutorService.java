package museos.services;

import museos.entities.Autor;
import museos.repositories.AutorRepository;
import museos.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class AutorService implements IService<Autor, Integer> {

    private final AutorRepository repo;

    public AutorService() {
        this.repo = new AutorRepository();
    }

    public AutorService(AutorRepository repo) {
        this.repo = repo;
    }

    @Override
    public Autor getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Autor getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Autor a = repo.getByName(normalized);
        if (a == null) {
            a = new Autor();
            a.setNombre(normalized);
            repo.create(a);
        }
        return a;
    }

    @Override
    public List<Autor> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Autor> getAllStream() {
        return repo.getAllStream();
    }
}
