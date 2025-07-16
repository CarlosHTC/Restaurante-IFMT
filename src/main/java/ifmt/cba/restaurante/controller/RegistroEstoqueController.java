package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import ifmt.cba.restaurante.dto.RegistroEstoqueDTO;
import ifmt.cba.restaurante.entity.Enum.MovimentoEstoqueEnum;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.RegistroEstoqueNegocio;

@RestController()
@RequestMapping("/registroEstoque")
public class RegistroEstoqueController {

    @Autowired
    private RegistroEstoqueNegocio registroEstoqueNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistroEstoqueDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return registroEstoqueNegocio.pesquisaTodos();
    }

    @GetMapping(path = "/codigo", produces = MediaType.APPLICATION_JSON_VALUE)
    public RegistroEstoqueDTO buscarPorID(@RequestParam("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return registroEstoqueNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(path = "/movimento", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistroEstoqueDTO> buscarPorMovimento(@RequestParam("movimento") String movimento) throws NotFoundException, NotValidDataException {
        MovimentoEstoqueEnum movimentoEstoque = MovimentoEstoqueEnum.valueOf(movimento.toUpperCase());
        return registroEstoqueNegocio.pesquisaPorMovimento(movimentoEstoque);
    }

    @GetMapping(path = "/periodo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RegistroEstoqueDTO> buscarPorPeriodo(@RequestParam("dataInicio") String dataInicio, 
    		@RequestParam("dataFim") String dataFim) throws NotFoundException, NotValidDataException {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inicio;
        LocalDate fim;

        try {
            inicio = LocalDate.parse(dataInicio, formatter);
            fim = LocalDate.parse(dataFim, formatter);
        } catch (DateTimeParseException e) {
            throw new NotValidDataException("Data inválida. Use o formato dd/MM/yyyy");
        }
        return registroEstoqueNegocio.pesquisaPorPeriodo(inicio, fim);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegistroEstoqueDTO inserir(@RequestBody RegistroEstoqueDTO registroEstoqueDTO) throws NotFoundException, NotValidDataException {
        return registroEstoqueNegocio.inserir(registroEstoqueDTO);
    }
}

