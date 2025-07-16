package ifmt.cba.restaurante.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.GrupoAlimentarDTO;
import ifmt.cba.restaurante.dto.ProdutoDTO;
import ifmt.cba.restaurante.entity.Produto;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.ProdutoRepository;

@Service
public class ProdutoNegocio {

	private ModelMapper modelMapper;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private GrupoAlimentarNegocio grupoAlimentarNegocio;

	public ProdutoNegocio() {
		this.modelMapper = new ModelMapper();
	}

	public ProdutoDTO inserir(ProdutoDTO produtoDTO) throws NotValidDataException, NotFoundException {
		try {
			GrupoAlimentarDTO grupo = grupoAlimentarNegocio.pesquisaCodigo(produtoDTO.getGrupoAlimentar().getCodigo());
			produtoDTO.setGrupoAlimentar(grupo);
		} catch (NotFoundException e) {
			throw new NotFoundException("Grupo alimentar não encontrado");
		}
		
		Produto produto = this.toEntity(produtoDTO);
		String mensagemErros = produto.validar();

		if (!mensagemErros.isEmpty()) {
			throw new NotValidDataException(mensagemErros);
		}

		try {
			if (produtoRepository.findByNomeIgnoreCaseStartingWith(produto.getNome()) != null) {
				throw new NotValidDataException("Ja existe esse produto");
			}

			produto = produtoRepository.save(produto);
		} catch (Exception ex) {
			throw new NotValidDataException("Erro ao incluir o produto - " + ex.getMessage());
		}
		return this.toDTO(produto);
	}

	public ProdutoDTO alterar(ProdutoDTO produtoDTO) throws NotValidDataException, NotFoundException {
		try {
			GrupoAlimentarDTO grupo = grupoAlimentarNegocio.pesquisaCodigo(produtoDTO.getGrupoAlimentar().getCodigo());
			produtoDTO.setGrupoAlimentar(grupo);
		} catch (NotFoundException e) {
			throw new NotFoundException("Grupo alimentar não encontrado");
		}
		
		Produto produto = this.toEntity(produtoDTO);
		String mensagemErros = produto.validar();
		if (!mensagemErros.isEmpty()) {
			throw new NotValidDataException(mensagemErros);
		}
		try {
			if (produtoRepository.findById(produto.getCodigo()).orElse(null) == null) {
				throw new NotFoundException("Nao existe esse produto");
			}
			produto = produtoRepository.save(produto);
		} catch (Exception ex) {
			throw new NotValidDataException("Erro ao alterar o produto - " + ex.getMessage());
		}
		return this.toDTO(produto);
	}

	public void excluir(int codigo) throws NotValidDataException {

		try {
			Produto produto = produtoRepository.findById(codigo).orElse(null);
			if (produto == null) {
				throw new NotFoundException("Nao existe esse produto");
			}

			produtoRepository.delete(produto);
		} catch (Exception ex) {
			throw new NotValidDataException("Erro ao excluir o produto - " + ex.getMessage());
		}
	}

	public List<ProdutoDTO> pesquisaTodos() throws NotFoundException {
		try {
			return this.toDTOAll(produtoRepository.findAll());
		} catch (Exception ex) {
			throw new NotFoundException("Erro ao pesquisar produtos - " + ex.getMessage());
		}
	}

	public List<ProdutoDTO> pesquisaProdutosAbaixoEstoqueMinimo() throws NotFoundException {
		try {
			return this.toDTOAll(produtoRepository.findProdutosAbaixoEstoqueMinimo());
		} catch (Exception ex) {
			throw new NotFoundException("Erro ao pesquisar produtos abaixo do estoque minimo - " + ex.getMessage());
		}
	}

	public ProdutoDTO pesquisaNome(String parteNome) throws NotFoundException {
		try {
			return this.toDTO(produtoRepository.findByNomeIgnoreCaseStartingWith(parteNome));
		} catch (Exception ex) {
			throw new NotFoundException("Erro ao pesquisar produto pelo nome - " + ex.getMessage());
		}
	}

	public ProdutoDTO pesquisaCodigo(int codigo) throws NotFoundException {
		try {
			return this.toDTO(produtoRepository.findById(codigo).orElse(null));
		} catch (Exception ex) {
			throw new NotFoundException("Erro ao pesquisar produto pelo codigo - " + ex.getMessage());
		}
	}

	public List<ProdutoDTO> toDTOAll(List<Produto> listaProduto) {
		List<ProdutoDTO> listaDTO = new ArrayList<ProdutoDTO>();

		for (Produto produto : listaProduto) {
			listaDTO.add(this.toDTO(produto));
		}
		return listaDTO;
	}

	public ProdutoDTO toDTO(Produto produto) {
		return this.modelMapper.map(produto, ProdutoDTO.class);
	}

	public Produto toEntity(ProdutoDTO produtoDTO) {
		return this.modelMapper.map(produtoDTO, Produto.class);
	}
}
