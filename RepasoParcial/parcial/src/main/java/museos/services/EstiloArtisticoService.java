package museos.services;

import museos.entities.EstiloArtistico;
import museos.repositories.EstiloArtisticoRepository;
import museos.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class EstiloArtisticoService implements IService<EstiloArtistico, Integer> {

    private final EstiloArtisticoRepository repo;

    public EstiloArtisticoService() {
        this.repo = new EstiloArtisticoRepository();
    }

    public EstiloArtisticoService(EstiloArtisticoRepository repo) {
        this.repo = repo;
    }

    @Override
    public EstiloArtistico getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public EstiloArtistico getOrCreateByName(String name) {
        if (name == null) return null;
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        EstiloArtistico e = repo.getByName(normalized);
        if (e == null) {
            e = new EstiloArtistico();
            e.setNombre(normalized);
            repo.create(e);
        }
        return e;
    }

    @Override
    public List<EstiloArtistico> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<EstiloArtistico> getAllStream() {
        return repo.getAllStream();
    }
}
