package ifmt.cba.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifmt.cba.restaurante.entity.PreparoProduto;

public interface PreparoProdutoRepository extends JpaRepository<PreparoProduto, Integer> {

    PreparoProduto findByNomeIgnoreCaseStartingWith(String nome);

    List<PreparoProduto> findAll();

}

