package ifmt.cba.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifmt.cba.restaurante.entity.Entregador;

public interface EntregadorRepository extends JpaRepository<Entregador, Integer>{

    List<Entregador> findByNomeIgnoreCaseStartingWith(String nome);

    Entregador findByCpf(String cpf);

}
