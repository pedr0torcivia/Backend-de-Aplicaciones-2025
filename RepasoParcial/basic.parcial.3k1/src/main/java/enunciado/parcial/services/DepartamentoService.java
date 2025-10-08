package enunciado.parcial.services;

import enunciado.parcial.services.interfaces.IService;
import enunciado.parcial.repositories.DepartamentoRepository;
import enunciado.parcial.entities.Departamento;

import java.util.List;
import java.util.stream.Stream;

public class DepartamentoService implements IService<Departamento, Integer> {

    private final DepartamentoRepository repo;

    public DepartamentoService() {
        this.repo = new DepartamentoRepository();
    }

    public DepartamentoService(DepartamentoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Departamento getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public Departamento getOrCreateByName(String name) {
        String normalized = name == null ? null : name.trim();
        Departamento d = repo.getByName(normalized);
        if (d == null) {
            d = new Departamento();
            d.setNombre(normalized);
            repo.create(d);
        }
        return d;
    }

    @Override
    public List<Departamento> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<Departamento> getAllStream() {
        return repo.getAllStream();
    }
}
