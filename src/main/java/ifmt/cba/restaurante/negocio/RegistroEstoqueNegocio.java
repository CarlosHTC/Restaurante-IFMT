package ifmt.cba.restaurante.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.ProdutoDTO;
import ifmt.cba.restaurante.dto.RegistroEstoqueDTO;
import ifmt.cba.restaurante.entity.RegistroEstoque;
import ifmt.cba.restaurante.entity.Enum.MovimentoEstoqueEnum;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.RegistroEstoqueRepository;

@Service
public class RegistroEstoqueNegocio {

    private ModelMapper modelMapper;

    @Autowired
    private RegistroEstoqueRepository registroEstoqueRepository;
    
    @Autowired
    private ProdutoNegocio produtoNegocio;

    public RegistroEstoqueNegocio() {
        this.modelMapper = new ModelMapper();
    }

    public RegistroEstoqueDTO inserir(RegistroEstoqueDTO registroEstoqueDTO) throws NotValidDataException, NotFoundException {
    	try {
			ProdutoDTO produtoDTO = produtoNegocio.pesquisaCodigo(registroEstoqueDTO.getProduto().getCodigo());
			registroEstoqueDTO.setProduto(produtoDTO);
		} catch (NotFoundException e) {
			throw new NotFoundException("Produto não encontrado");
		}
        RegistroEstoque registroEstoque = this.toEntity(registroEstoqueDTO);
        registroEstoque.setData(LocalDate.now());
        
        String mensagemErros = registroEstoque.validar();

        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }

        try {
            registroEstoque = registroEstoqueRepository.save(registroEstoque);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao incluir o registro de estoque - " + ex.getMessage());
        }
        return this.toDTO(registroEstoque);
    }

    public List<RegistroEstoqueDTO> pesquisaTodos() throws NotFoundException {
        try {
            return this.toDTOAll(registroEstoqueRepository.findAll());
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar registros de estoque - " + ex.getMessage());
        }
    }

    public List<RegistroEstoqueDTO> pesquisaPorMovimento(MovimentoEstoqueEnum movimento) throws NotFoundException {
        try {
            return this.toDTOAll(registroEstoqueRepository.findByMovimento(movimento));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar registros por movimento - " + ex.getMessage());
        }
    }

    public List<RegistroEstoqueDTO> pesquisaPorPeriodo(LocalDate dataInicio, LocalDate dataFim) throws NotFoundException {
        try {
            return this.toDTOAll(registroEstoqueRepository.findByDataBetween(dataInicio, dataFim));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar registros por periodo - " + ex.getMessage());
        }
    }

    public RegistroEstoqueDTO pesquisaCodigo(int codigo) throws NotFoundException {
        try {
            return this.toDTO(registroEstoqueRepository.findById(codigo).orElse(null));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar registro de estoque pelo codigo - " + ex.getMessage());
        }
    }

    public List<RegistroEstoqueDTO> toDTOAll(List<RegistroEstoque> listaRegistroEstoque) {
        List<RegistroEstoqueDTO> listaDTO = new ArrayList<RegistroEstoqueDTO>();

        for (RegistroEstoque registroEstoque : listaRegistroEstoque) {
            listaDTO.add(this.toDTO(registroEstoque));
        }
        return listaDTO;
    }

    public RegistroEstoqueDTO toDTO(RegistroEstoque registroEstoque) {
        return this.modelMapper.map(registroEstoque, RegistroEstoqueDTO.class);
    }

    public RegistroEstoque toEntity(RegistroEstoqueDTO registroEstoqueDTO) {
        return this.modelMapper.map(registroEstoqueDTO, RegistroEstoque.class);
    }
}

