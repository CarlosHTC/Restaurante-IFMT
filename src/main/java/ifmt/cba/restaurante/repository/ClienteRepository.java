package ifmt.cba.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifmt.cba.restaurante.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByCPF(String cpf);

    Cliente findByNomeIgnoreCaseStartingWith(String nome);

    List<Cliente> findAll();

}

