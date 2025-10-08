package enunciado.parcial.services;

import enunciado.parcial.services.interfaces.IService;
import enunciado.parcial.repositories.PuestoRepository;
import enunciado.parcial.entities.Puesto;

import java.util.List;
import java.util.stream.Stream;

public class PuestoService implements IService<Puesto, Integer> {

    private final PuestoRepository repo;

    public PuestoService() {
        this.repo = new PuestoRepository();
    }

    public PuestoService(PuestoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Puesto getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Puesto getOrCreateByName(String name) {
        String normalized = name == null ? null : name.trim();
        Puesto p = repo.getByName(normalized);
        if (p == null) {
            p = new Puesto();
            p.setNombre(normalized);
            repo.create(p);
        }
        return p;
    }

    @Override
    public List<Puesto> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Puesto> getAllStream() {
        return repo.getAllStream();
    }
}
