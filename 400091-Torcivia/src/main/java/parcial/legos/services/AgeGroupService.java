package parcial.legos.services;

import parcial.legos.entities.AgeGroup;
import parcial.legos.repositories.AgeGroupRepository;
import parcial.legos.services.interfaces.IService;

import java.util.List;
import java.util.stream.Stream;

public class AgeGroupService implements IService<AgeGroup, Integer> {

    private final AgeGroupRepository repo;

    public AgeGroupService() {
        this.repo = new AgeGroupRepository();
    }

    public AgeGroupService(AgeGroupRepository repo) {
        this.repo = repo;
    }

    @Override
    public AgeGroup getById(Integer id) {
        return repo.getById(id);
    }

    @Override
    public List<AgeGroup> getAll() {
        return repo.getAllList();
    }

    @Override
    public Stream<AgeGroup> getAllStream() {
        return repo.getAllStream();
    }

    /** CSV → AGE_GROUPS.CODE (único) */
    public AgeGroup getOrCreateByCode(String code) {
        if (code == null) return null;
        String normalized = code.trim();
        if (normalized.isEmpty()) return null;

        AgeGroup ag = repo.getByCode(normalized);
        if (ag == null) {
            ag = new AgeGroup();
            ag.setCode(normalized);
            repo.create(ag);
        }
        return ag;
    }
}
