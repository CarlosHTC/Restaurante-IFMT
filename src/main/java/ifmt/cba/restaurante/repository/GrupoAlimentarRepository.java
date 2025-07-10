package ifmt.cba.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifmt.cba.restaurante.entity.GrupoAlimentar;

public interface GrupoAlimentarRepository extends JpaRepository<GrupoAlimentar, Integer> {

    GrupoAlimentar findByNomeIgnoreCaseStartingWith(String nome);

    List<GrupoAlimentar> findAll();

}

