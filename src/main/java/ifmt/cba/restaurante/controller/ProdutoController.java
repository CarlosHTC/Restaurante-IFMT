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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import ifmt.cba.restaurante.dto.ProdutoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.ProdutoNegocio;

@RestController()
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoNegocio produtoNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return produtoNegocio.pesquisaTodos();
    }

    @GetMapping(path = "/codigo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoDTO buscarPorID(@RequestParam("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return produtoNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(path = "/nome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoDTO buscarPorNome(@RequestParam("nome") String nome) throws NotFoundException, NotValidDataException {
        return produtoNegocio.pesquisaNome(nome);
    }

    @GetMapping(path = "/estoqueMinimo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoDTO> buscarAbaixoEstoqueMinimo() throws NotFoundException, NotValidDataException {
        return produtoNegocio.pesquisaProdutosAbaixoEstoqueMinimo();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoDTO inserir(@RequestBody ProdutoDTO produtoDTO) throws NotFoundException, NotValidDataException {
        return produtoNegocio.inserir(produtoDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoDTO alterar(@RequestBody ProdutoDTO produtoDTO) throws NotFoundException, NotValidDataException {
        return produtoNegocio.alterar(produtoDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> excluir(@RequestParam("codigo") int codigo) throws NotFoundException, NotValidDataException {
        produtoNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }
}

