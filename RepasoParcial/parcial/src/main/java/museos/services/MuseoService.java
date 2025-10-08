package museos.services;

import museos.entities.Museo;
import museos.repositories.MuseoRepository;
import museos.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class MuseoService implements IService<Museo, Integer> {

    private final MuseoRepository repo;

    public MuseoService() {
        this.repo = new MuseoRepository();
    }

    public MuseoService(MuseoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Museo getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Museo getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Museo m = repo.getByName(normalized);
        if (m == null) {
            m = new Museo();
            m.setNombre(normalized);
            repo.create(m);
        }
        return m;
    }

    @Override
    public List<Museo> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Museo> getAllStream() {
        return repo.getAllStream();
    }
}
