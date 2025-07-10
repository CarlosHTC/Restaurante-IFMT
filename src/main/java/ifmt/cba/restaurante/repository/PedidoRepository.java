package ifmt.cba.restaurante.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ifmt.cba.restaurante.entity.EstadoPedido;
import ifmt.cba.restaurante.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByEstadoOrderByDataPedidoAscHoraPedidoAsc(EstadoPedido estado);

    List<Pedido> findByDataPedido(LocalDate data);

    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim")
    List<Pedido> findByDataPedidoBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    List<Pedido> findAll();

}

