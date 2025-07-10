package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import ifmt.cba.restaurante.dto.PedidoDTO;
import ifmt.cba.restaurante.entity.EstadoPedido;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.PedidoNegocio;

@RestController()
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoNegocio pedidoNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PedidoDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return pedidoNegocio.pesquisaTodos();
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(value = "/estado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PedidoDTO> buscarPorEstado(@PathVariable("estado") String estado) throws NotFoundException, NotValidDataException {
        EstadoPedido estadoPedido = EstadoPedido.valueOf(estado.toUpperCase());
        return pedidoNegocio.pesquisaPorEstado(estadoPedido);
    }

    @GetMapping(value = "/data/{data}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PedidoDTO> buscarPorData(@PathVariable("data") String data) throws NotFoundException, NotValidDataException {
        LocalDate localDate = LocalDate.parse(data);
        return pedidoNegocio.pesquisaPorData(localDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO inserir(@RequestBody PedidoDTO pedidoDTO) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.inserir(pedidoDTO);
    }

    @PutMapping(value = "/{codigo}/iniciar-producao", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO iniciarProducao(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.iniciarProducao(codigo);
    }

    @PutMapping(value = "/{codigo}/finalizar-producao", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO finalizarProducao(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.finalizarProducao(codigo);
    }

    @PutMapping(value = "/{codigo}/iniciar-entrega/{entregador}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO iniciarEntrega(@PathVariable("codigo") int codigo, @PathVariable("entregador") int codigoEntregador) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.iniciarEntrega(codigo, codigoEntregador);
    }

    @PutMapping(value = "/{codigo}/finalizar-entrega", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO finalizarEntrega(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.finalizarEntrega(codigo);
    }

    @PutMapping(value = "/{codigo}/cancelar", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoDTO cancelarPedido(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return pedidoNegocio.cancelarPedido(codigo);
    }
}

