package ifmt.cba.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifmt.cba.restaurante.dto.EntregadorDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.EntregadorNegocio;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {
	
	 @Autowired
	    private EntregadorNegocio entregadorNegocio;

	    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public EntregadorDTO inserir(@RequestBody EntregadorDTO dto) throws NotValidDataException {
	        return entregadorNegocio.inserir(dto);
	    }

	    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public EntregadorDTO alterar(@RequestBody EntregadorDTO dto) throws NotValidDataException, NotFoundException {
	        return entregadorNegocio.alterar(dto);
	    }

	    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	    public void excluir(@RequestParam("codigo") int codigo) throws NotValidDataException, NotFoundException {
	        entregadorNegocio.excluir(codigo);
	    }

	    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<EntregadorDTO> listarTodos() throws NotFoundException {
	        return entregadorNegocio.pesquisaTodos();
	    }

	    @GetMapping(path = "/codigo", produces = MediaType.APPLICATION_JSON_VALUE)
	    public EntregadorDTO buscarPorCodigo(@RequestParam("codigo") int codigo) throws NotFoundException {
	        return entregadorNegocio.pesquisaCodigo(codigo);
	    }

	    @GetMapping(path = "/nome", produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<EntregadorDTO> buscarPorNome(@RequestParam("nome") String nome) throws NotFoundException {
	        return entregadorNegocio.pesquisaPorNome(nome);
	    }
}
