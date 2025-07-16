package ifmt.cba.restaurante.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ifmt.cba.restaurante.entity.RegistroEstoque;
import ifmt.cba.restaurante.entity.Enum.MovimentoEstoqueEnum;

public interface RegistroEstoqueRepository extends JpaRepository<RegistroEstoque, Integer> {

    List<RegistroEstoque> findByMovimento(MovimentoEstoqueEnum movimento);

    @Query("SELECT r FROM RegistroEstoque r WHERE r.data BETWEEN :dataInicio AND :dataFim")
    List<RegistroEstoque> findByDataBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    @Query("SELECT r FROM RegistroEstoque r WHERE r.movimento = :movimento AND r.data BETWEEN :dataInicio AND :dataFim")
    List<RegistroEstoque> findByMovimentoAndDataBetween(@Param("movimento") MovimentoEstoqueEnum movimento, 
                                                        @Param("dataInicio") LocalDate dataInicio, 
                                                        @Param("dataFim") LocalDate dataFim);

    List<RegistroEstoque> findAll();

}

