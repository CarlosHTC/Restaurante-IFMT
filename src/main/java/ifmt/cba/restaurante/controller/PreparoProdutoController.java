package ifmt.cba.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ifmt.cba.restaurante.dto.PreparoProdutoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.PreparoProdutoNegocio;

@RestController()
@RequestMapping("/preparo-produto")
public class PreparoProdutoController {

    @Autowired
    private PreparoProdutoNegocio preparoProdutoNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PreparoProdutoDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return preparoProdutoNegocio.pesquisaTodos();
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PreparoProdutoDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return preparoProdutoNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PreparoProdutoDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        return preparoProdutoNegocio.pesquisaNome(nome);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PreparoProdutoDTO inserir(@RequestBody PreparoProdutoDTO preparoProdutoDTO) throws NotFoundException, NotValidDataException {
        return preparoProdutoNegocio.inserir(preparoProdutoDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PreparoProdutoDTO alterar(@RequestBody PreparoProdutoDTO preparoProdutoDTO) throws NotFoundException, NotValidDataException {
        return preparoProdutoNegocio.alterar(preparoProdutoDTO);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        preparoProdutoNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }
}

