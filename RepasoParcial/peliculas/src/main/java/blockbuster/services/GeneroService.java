package blockbuster.services;

import blockbuster.entities.Genero;
import blockbuster.repositories.GeneroRepository;
import blockbuster.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class GeneroService implements IService<Genero, Integer> {

    private final GeneroRepository repo;

    public GeneroService() {
        this.repo = new GeneroRepository();
    }

    public GeneroService(GeneroRepository repo) {
        this.repo = repo;
    }

    @Override
    public Genero getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Genero getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Genero g = repo.getByName(normalized);
        if (g == null) {
            g = new Genero();
            g.setNombre(normalized);
            repo.create(g);
        }
        return g;
    }

    @Override
    public List<Genero> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Genero> getAllStream() {
        return repo.getAllStream();
    }
}
