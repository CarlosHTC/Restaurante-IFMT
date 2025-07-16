package ifmt.cba.restaurante.negocio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.ItemPedidoDTO;
import ifmt.cba.restaurante.entity.ItemPedido;
import ifmt.cba.restaurante.entity.PreparoProduto;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.ItemPedidoRepository;
import ifmt.cba.restaurante.repository.PreparoProdutoRepository;

@Service
public class ItemPedidoNegocio {
	
	private ModelMapper modelMapper;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PreparoProdutoRepository preparoProdutoRepository;

    public ItemPedidoNegocio() {
        this.modelMapper = new ModelMapper();
    }

    public ItemPedidoDTO inserir(ItemPedidoDTO dto) throws NotValidDataException {
        ItemPedido item = this.toEntity(dto);
        
        if (item.getPreparoProduto() == null || item.getPreparoProduto().getCodigo() <= 0) {
            throw new NotValidDataException("Preparo do produto é obrigatório.");
        }

        PreparoProduto preparo = preparoProdutoRepository.findById(item.getPreparoProduto().getCodigo())
                .orElseThrow(() -> new NotValidDataException("Preparo de produto não encontrado."));

        item.setPreparoProduto(preparo);

        if (item.getQuantidadePorcao() <= 0) {
            throw new NotValidDataException("Quantidade de porções deve ser maior que zero.");
        }

        try {
            item = itemPedidoRepository.save(item);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao incluir item do pedido - " + ex.getMessage());
        }

        return this.toDTO(item);
    }

    public ItemPedidoDTO alterar(ItemPedidoDTO dto) throws NotValidDataException, NotFoundException {
        ItemPedido item = this.toEntity(dto);

        if (itemPedidoRepository.findById(item.getCodigo()).isEmpty()) {
            throw new NotFoundException("Item do pedido não encontrado.");
        }

        if (item.getPreparoProduto() == null || item.getPreparoProduto().getCodigo() <= 0) {
            throw new NotValidDataException("Preparo do produto é obrigatório.");
        }

        PreparoProduto preparo = preparoProdutoRepository.findById(item.getPreparoProduto().getCodigo())
                .orElseThrow(() -> new NotValidDataException("Preparo de produto não encontrado."));

        item.setPreparoProduto(preparo);

        if (item.getQuantidadePorcao() <= 0) {
            throw new NotValidDataException("Quantidade de porções deve ser maior que zero.");
        }

        try {
            item = itemPedidoRepository.save(item);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao alterar item do pedido - " + ex.getMessage());
        }

        return this.toDTO(item);
    }

    public ItemPedidoDTO pesquisarPorCodigo(int codigo) throws NotFoundException {
        ItemPedido item = itemPedidoRepository.findById(codigo)
                .orElseThrow(() -> new NotFoundException("Item do pedido não encontrado."));
        return this.toDTO(item);
    }

    public List<ItemPedidoDTO> listarTodos() throws NotFoundException {
        List<ItemPedido> itens = itemPedidoRepository.findAll();
        if (itens.isEmpty()) {
            throw new NotFoundException("Nenhum item de pedido encontrado.");
        }
        return toDTOList(itens);
    }

    // Conversão
    private ItemPedidoDTO toDTO(ItemPedido item) {
        return modelMapper.map(item, ItemPedidoDTO.class);
    }

    private ItemPedido toEntity(ItemPedidoDTO dto) {
        return modelMapper.map(dto, ItemPedido.class);
    }

    private List<ItemPedidoDTO> toDTOList(List<ItemPedido> lista) {
        List<ItemPedidoDTO> dtos = new ArrayList();
        for (ItemPedido item : lista) {
            dtos.add(toDTO(item));
        }
        return dtos;
    }

}
