package games.services;

import games.entities.Desarrollador;
import games.repositories.DesarrolladorRepository;
import games.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class DesarrolladorService implements IService<Desarrollador, Integer> {

    private final DesarrolladorRepository repo;

    public DesarrolladorService() {
        this.repo = new DesarrolladorRepository();
    }

    public DesarrolladorService(DesarrolladorRepository repo) {
        this.repo = repo;
    }

    @Override
    public Desarrollador getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Desarrollador getOrCreateByName(String name) {
        // 1) validar
        if (name == null) return null;
        // 2) normalizar (trim + colapsar espacios internos)
        String normalized = name.trim().replaceAll("\\s+", " ");
        if (normalized.isEmpty()) return null;

        Desarrollador d = repo.getByName(normalized);
        if (d == null) {
            d = new Desarrollador();
            d.setNombre(normalized);
            repo.create(d); // el repo maneja la transacción
        }
        return d;
    }

    @Override
    public List<Desarrollador> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Desarrollador> getAllStream() {
        return repo.getAllStream();
    }
}
