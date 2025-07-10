package ifmt.cba.restaurante.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.GrupoAlimentarDTO;
import ifmt.cba.restaurante.entity.GrupoAlimentar;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.GrupoAlimentarRepository;

@Service
public class GrupoAlimentarNegocio {

    private ModelMapper modelMapper;

    @Autowired
    private GrupoAlimentarRepository grupoAlimentarRepository;

    public GrupoAlimentarNegocio() {
        this.modelMapper = new ModelMapper();
    }

    public GrupoAlimentarDTO inserir(GrupoAlimentarDTO grupoAlimentarDTO) throws NotValidDataException {

        GrupoAlimentar grupoAlimentar = this.toEntity(grupoAlimentarDTO);
        String mensagemErros = grupoAlimentar.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }

        try {
            if (grupoAlimentarRepository.findByNomeIgnoreCaseStartingWith(grupoAlimentar.getNome()) != null) {
                throw new NotValidDataException("Ja existe esse grupo alimentar");
            }

            grupoAlimentar = grupoAlimentarRepository.save(grupoAlimentar);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao incluir o grupo alimentar - " + ex.getMessage());
        }
        return this.toDTO(grupoAlimentar);
    }

    public GrupoAlimentarDTO alterar(GrupoAlimentarDTO grupoAlimentarDTO) throws NotValidDataException, NotFoundException {

        GrupoAlimentar grupoAlimentar = this.toEntity(grupoAlimentarDTO);
        String mensagemErros = grupoAlimentar.validar();
        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }
        try {
            if (grupoAlimentarRepository.findById(grupoAlimentar.getCodigo()).orElse(null) == null) {
                throw new NotFoundException("Nao existe esse grupo alimentar");
            }
            grupoAlimentar = grupoAlimentarRepository.save(grupoAlimentar);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao alterar o grupo alimentar - " + ex.getMessage());
        }
        return this.toDTO(grupoAlimentar);
    }

    public void excluir(int codigo) throws NotValidDataException {

        try {
            GrupoAlimentar grupoAlimentar = grupoAlimentarRepository.findById(codigo).orElse(null);
            if (grupoAlimentar == null) {
                throw new NotFoundException("Nao existe esse grupo alimentar");
            }

            grupoAlimentarRepository.delete(grupoAlimentar);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao excluir o grupo alimentar - " + ex.getMessage());
        }
    }

    public List<GrupoAlimentarDTO> pesquisaTodos() throws NotFoundException {
        try {
            return this.toDTOAll(grupoAlimentarRepository.findAll());
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar grupos alimentares - " + ex.getMessage());
        }
    }

    public GrupoAlimentarDTO pesquisaNome(String parteNome) throws NotFoundException {
        try {
            return this.toDTO(grupoAlimentarRepository.findByNomeIgnoreCaseStartingWith(parteNome));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar grupo alimentar pelo nome - " + ex.getMessage());
        }
    }

    public GrupoAlimentarDTO pesquisaCodigo(int codigo) throws NotFoundException {
        try {
            return this.toDTO(grupoAlimentarRepository.findById(codigo).orElse(null));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar grupo alimentar pelo codigo - " + ex.getMessage());
        }
    }

    public List<GrupoAlimentarDTO> toDTOAll(List<GrupoAlimentar> listaGrupoAlimentar) {
        List<GrupoAlimentarDTO> listaDTO = new ArrayList<GrupoAlimentarDTO>();

        for (GrupoAlimentar grupoAlimentar : listaGrupoAlimentar) {
            listaDTO.add(this.toDTO(grupoAlimentar));
        }
        return listaDTO;
    }

    public GrupoAlimentarDTO toDTO(GrupoAlimentar grupoAlimentar) {
        return this.modelMapper.map(grupoAlimentar, GrupoAlimentarDTO.class);
    }

    public GrupoAlimentar toEntity(GrupoAlimentarDTO grupoAlimentarDTO) {
        return this.modelMapper.map(grupoAlimentarDTO, GrupoAlimentar.class);
    }
}

