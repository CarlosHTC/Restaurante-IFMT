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

import ifmt.cba.restaurante.dto.GrupoAlimentarDTO;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.negocio.GrupoAlimentarNegocio;

@RestController()
@RequestMapping("/grupo-alimentar")
public class GrupoAlimentarController {

    @Autowired
    private GrupoAlimentarNegocio grupoAlimentarNegocio;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GrupoAlimentarDTO> buscarTodos() throws NotFoundException, NotValidDataException {
        return grupoAlimentarNegocio.pesquisaTodos();
    }

    @GetMapping(value = "/codigo/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoAlimentarDTO buscarPorID(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        return grupoAlimentarNegocio.pesquisaCodigo(codigo);
    }

    @GetMapping(value = "/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoAlimentarDTO buscarPorNome(@PathVariable("nome") String nome) throws NotFoundException, NotValidDataException {
        return grupoAlimentarNegocio.pesquisaNome(nome);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoAlimentarDTO inserir(@RequestBody GrupoAlimentarDTO grupoAlimentarDTO) throws NotFoundException, NotValidDataException {
        return grupoAlimentarNegocio.inserir(grupoAlimentarDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoAlimentarDTO alterar(@RequestBody GrupoAlimentarDTO grupoAlimentarDTO) throws NotFoundException, NotValidDataException {
        return grupoAlimentarNegocio.alterar(grupoAlimentarDTO);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") int codigo) throws NotFoundException, NotValidDataException {
        grupoAlimentarNegocio.excluir(codigo);
        return ResponseEntity.noContent().build();
    }
}

