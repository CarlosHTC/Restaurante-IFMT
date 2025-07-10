package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import ifmt.cba.restaurante.dto.RegistroEstoqueDTO;
import ifmt.cba.restaurante.entity.MovimentoEstoque;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.RegistroEstoqueNegocio;

@RestController()
@RequestMapping("/registro-estoque")
public class RegistroEstoqueController {

    @Autowired
    private RegistroEstoqueNegocio registroEstoqueNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistroEstoqueDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return registroEstoqueNegocio.pesquisaTodos();
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RegistroEstoqueDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return registroEstoqueNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(value = "/movimento/{movimento}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistroEstoqueDTO> buscarPorMovimento(@PathVariable("movimento") String movimento) throws NotFoundException, NotValidDataException {
        MovimentoEstoque movimentoEstoque = MovimentoEstoque.valueOf(movimento.toUpperCase());
        return registroEstoqueNegocio.pesquisaPorMovimento(movimentoEstoque);
    }

    @GetMapping(value = "/periodo/{dataInicio}/{dataFim}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistroEstoqueDTO> buscarPorPeriodo(@PathVariable("dataInicio") String dataInicio, 
                                                     @PathVariable("dataFim") String dataFim) throws NotFoundException, NotValidDataException {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return registroEstoqueNegocio.pesquisaPorPeriodo(inicio, fim);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegistroEstoqueDTO inserir(@RequestBody RegistroEstoqueDTO registroEstoqueDTO) throws NotFoundException, NotValidDataException {
        return registroEstoqueNegocio.inserir(registroEstoqueDTO);
    }
}

