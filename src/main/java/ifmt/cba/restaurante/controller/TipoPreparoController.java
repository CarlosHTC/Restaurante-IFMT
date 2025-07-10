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

import ifmt.cba.restaurante.dto.TipoPreparoDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.TipoPreparoNegocio;

@RestController()
@RequestMapping("/tipo-preparo")
public class TipoPreparoController {

    @Autowired
    private TipoPreparoNegocio tipoPreparoNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TipoPreparoDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return tipoPreparoNegocio.pesquisaTodos();
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TipoPreparoDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return tipoPreparoNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(value = "/descricao/{descricao}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TipoPreparoDTO buscarPorDescricao(@PathVariable("descricao") String descricao) throws NotFoundException, NotValidDataException {
        return tipoPreparoNegocio.pesquisaDescricao(descricao);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TipoPreparoDTO inserir(@RequestBody TipoPreparoDTO tipoPreparoDTO) throws NotFoundException, NotValidDataException {
        return tipoPreparoNegocio.inserir(tipoPreparoDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TipoPreparoDTO alterar(@RequestBody TipoPreparoDTO tipoPreparoDTO) throws NotFoundException, NotValidDataException {
        return tipoPreparoNegocio.alterar(tipoPreparoDTO);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        tipoPreparoNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }
}

