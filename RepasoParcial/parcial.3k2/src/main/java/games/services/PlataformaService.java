package games.services;

import games.entities.Plataforma;
import games.repositories.PlataformaRepository;
import games.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class PlataformaService implements IService<Plataforma, Integer> {

    private final PlataformaRepository repo;

    public PlataformaService() {
        this.repo = new PlataformaRepository();
    }

    public PlataformaService(PlataformaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Plataforma getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Plataforma getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Plataforma p = repo.getByName(normalized);
        if (p == null) {
            p = new Plataforma();
            p.setNombre(normalized);
            repo.create(p);
        }
        return p;
    }

    @Override
    public List<Plataforma> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Plataforma> getAllStream() {
        return repo.getAllStream();
    }
}
