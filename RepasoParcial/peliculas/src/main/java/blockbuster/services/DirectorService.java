package blockbuster.services;

import blockbuster.entities.Director;
import blockbuster.repositories.DirectorRepository;
import blockbuster.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class DirectorService implements IService<Director, Integer> {

    private final DirectorRepository repo;

    public DirectorService() {
        this.repo = new DirectorRepository();
    }

    public DirectorService(DirectorRepository repo) {
        this.repo = repo;
    }

    @Override
    public Director getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Director getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Director d = repo.getByName(normalized);
        if (d == null) {
            d = new Director();
            d.setNombre(normalized);
            repo.create(d);
        }
        return d;
    }

    @Override
    public List<Director> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Director> getAllStream() {
        return repo.getAllStream();
    }
}
