package ifmt.cba.restaurante.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.PreparoProdutoDTO;
import ifmt.cba.restaurante.entity.PreparoProduto;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.PreparoProdutoRepository;

@Service
public class PreparoProdutoNegocio {

    private ModelMapper modelMapper;

    @Autowired
    private PreparoProdutoRepository preparoProdutoRepository;

    public PreparoProdutoNegocio() {
        this.modelMapper = new ModelMapper();
    }

    public PreparoProdutoDTO inserir(PreparoProdutoDTO preparoProdutoDTO) throws NotValidDataException {

        PreparoProduto preparoProduto = this.toEntity(preparoProdutoDTO);
        String mensagemErros = preparoProduto.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }

        try {
            preparoProduto = preparoProdutoRepository.save(preparoProduto);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao incluir o preparo de produto - " + ex.getMessage());
        }
        return this.toDTO(preparoProduto);
    }

    public PreparoProdutoDTO alterar(PreparoProdutoDTO preparoProdutoDTO) throws NotValidDataException, NotFoundException {

        PreparoProduto preparoProduto = this.toEntity(preparoProdutoDTO);
        String mensagemErros = preparoProduto.validar();
        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }
        try {
            if (preparoProdutoRepository.findById(preparoProduto.getCodigo()).orElse(null) == null) {
                throw new NotFoundException("Nao existe esse preparo de produto");
            }
            preparoProduto = preparoProdutoRepository.save(preparoProduto);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao alterar o preparo de produto - " + ex.getMessage());
        }
        return this.toDTO(preparoProduto);
    }

    public void excluir(int codigo) throws NotValidDataException {

        try {
            PreparoProduto preparoProduto = preparoProdutoRepository.findById(codigo).orElse(null);
            if (preparoProduto == null) {
                throw new NotFoundException("Nao existe esse preparo de produto");
            }

            preparoProdutoRepository.delete(preparoProduto);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao excluir o preparo de produto - " + ex.getMessage());
        }
    }

    public List<PreparoProdutoDTO> pesquisaTodos() throws NotFoundException {
        try {
            return this.toDTOAll(preparoProdutoRepository.findAll());
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar preparos de produto - " + ex.getMessage());
        }
    }

    public PreparoProdutoDTO pesquisaNome(String parteNome) throws NotFoundException {
        try {
            return this.toDTO(preparoProdutoRepository.findByNomeIgnoreCaseStartingWith(parteNome));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar preparo de produto pelo nome - " + ex.getMessage());
        }
    }

    public PreparoProdutoDTO pesquisaCodigo(int codigo) throws NotFoundException {
        try {
            return this.toDTO(preparoProdutoRepository.findById(codigo).orElse(null));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar preparo de produto pelo codigo - " + ex.getMessage());
        }
    }

    public List<PreparoProdutoDTO> toDTOAll(List<PreparoProduto> listaPreparoProduto) {
        List<PreparoProdutoDTO> listaDTO = new ArrayList<PreparoProdutoDTO>();

        for (PreparoProduto preparoProduto : listaPreparoProduto) {
            listaDTO.add(this.toDTO(preparoProduto));
        }
        return listaDTO;
    }

    public PreparoProdutoDTO toDTO(PreparoProduto preparoProduto) {
        return this.modelMapper.map(preparoProduto, PreparoProdutoDTO.class);
    }

    public PreparoProduto toEntity(PreparoProdutoDTO preparoProdutoDTO) {
        return this.modelMapper.map(preparoProdutoDTO, PreparoProduto.class);
    }
}

