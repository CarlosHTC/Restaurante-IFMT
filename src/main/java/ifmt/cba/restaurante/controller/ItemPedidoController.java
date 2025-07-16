package ifmt.cba.restaurante.controller;

import org.springframework.http.MediaType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifmt.cba.restaurante.dto.ItemPedidoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.ItemPedidoNegocio;

@RestController
@RequestMapping("/itemPedido")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoNegocio itemPedidoNegocio;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemPedidoDTO inserir(@RequestBody ItemPedidoDTO dto) throws NotValidDataException {
		return itemPedidoNegocio.inserir(dto);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemPedidoDTO alterar(@RequestBody ItemPedidoDTO dto) throws NotValidDataException, NotFoundException {
		return itemPedidoNegocio.alterar(dto);
	}

	@GetMapping(path = "/codigo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ItemPedidoDTO buscarPorCodigo(@RequestParam("codigo") int codigo) throws NotFoundException {
		return itemPedidoNegocio.pesquisarPorCodigo(codigo);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ItemPedidoDTO> listarTodos() throws NotFoundException {
		return itemPedidoNegocio.listarTodos();
	}

}
