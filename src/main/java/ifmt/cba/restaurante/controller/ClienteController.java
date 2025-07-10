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

import ifmt.cba.restaurante.dto.ClienteDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.ClienteNegocio;

@RestController()
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteNegocio clienteNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClienteDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return clienteNegocio.pesquisaTodos();
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return clienteNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        return clienteNegocio.pesquisaNome(nome);
    }

    @GetMapping(value = "/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO buscarPorCPF(@PathVariable("cpf") String cpf) throws NotFoundException, NotValidDataException {
        return clienteNegocio.pesquisaCPF(cpf);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO inserir(@RequestBody ClienteDTO clienteDTO) throws NotFoundException, NotValidDataException {
        return clienteNegocio.inserir(clienteDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClienteDTO alterar(@RequestBody ClienteDTO clienteDTO) throws NotFoundException, NotValidDataException {
        return clienteNegocio.alterar(clienteDTO);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        clienteNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }
}

