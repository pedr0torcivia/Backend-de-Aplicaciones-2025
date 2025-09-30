package com.frc.isi.museo.repositorios;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.frc.isi.museo.entidades.EstiloArtistico;

public class EstiloArtisticoRepository extends Repository<EstiloArtistico, Integer> {

    public EstiloArtisticoRepository() {
        super();
    }

    @Override
    public EstiloArtistico getById(Integer id) {
        return this.manager.find(EstiloArtistico.class, id);
    }

    @Override
    public Set<EstiloArtistico> getAll() {
        return this.manager.createQuery("SELECT e FROM EstiloArtistico e", EstiloArtistico.class)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Stream<EstiloArtistico> getAllStrem() {
        return this.manager.createQuery("SELECT o FROM EstiloArtistico o", EstiloArtistico.class).getResultStream();
    }

}
