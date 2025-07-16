package ifmt.cba.restaurante.negocio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.ClienteDTO;
import ifmt.cba.restaurante.dto.EntregadorDTO;
import ifmt.cba.restaurante.dto.ItemPedidoDTO;
import ifmt.cba.restaurante.dto.PedidoDTO;
import ifmt.cba.restaurante.entity.Entregador;
import ifmt.cba.restaurante.entity.ItemPedido;
import ifmt.cba.restaurante.entity.Pedido;
import ifmt.cba.restaurante.entity.PreparoProduto;
import ifmt.cba.restaurante.entity.Enum.EstadoPedidoEnum;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.PedidoRepository;
import ifmt.cba.restaurante.repository.PreparoProdutoRepository;

@Service
public class PedidoNegocio {

    private ModelMapper modelMapper;

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteNegocio clienteNegocio;
    
    @Autowired
    private EntregadorNegocio entregadorNegocio;

    @Autowired
    private PreparoProdutoRepository preparoProdutoRepository;
    
    public PedidoNegocio() {
        this.modelMapper = new ModelMapper();
    }

    public PedidoDTO inserir(PedidoDTO pedidoDTO) throws NotValidDataException, NotFoundException {
        try {
            ClienteDTO clienteDTO = clienteNegocio.pesquisaCodigo(pedidoDTO.getCliente().getCodigo());
            pedidoDTO.setCliente(clienteDTO);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cliente não encontrado");
        }

        try {
            EntregadorDTO entregadorDTO = entregadorNegocio.pesquisaCodigo(pedidoDTO.getEntregador().getCodigo());
            pedidoDTO.setEntregador(entregadorDTO);
        } catch (Exception e) {
            throw new NotFoundException("Entregador não encontrado");
        }

        if (pedidoDTO.getListaItens() == null || pedidoDTO.getListaItens().isEmpty()) {
            throw new NotValidDataException("Pedido deve conter pelo menos um item");
        }

        Pedido pedido = this.toEntity(pedidoDTO);
        pedido.setDataPedido(LocalDate.now());
        pedido.setHoraPedido(LocalTime.now());
        pedido.setEstado(EstadoPedidoEnum.REGISTRADO);

        List<ItemPedido> listaItensConvertidos = new ArrayList<>();

        for (ItemPedidoDTO itemDTO : pedidoDTO.getListaItens()) {
            if (itemDTO.getQuantidadePorcao() <= 0) {
                throw new NotValidDataException("Quantidade de porções inválida.");
            }

            if (itemDTO.getPreparoProduto() == null || itemDTO.getPreparoProduto().getCodigo() <= 0) {
                throw new NotValidDataException("Preparo do produto é obrigatório.");
            }

            PreparoProduto preparo = preparoProdutoRepository.findById(itemDTO.getPreparoProduto().getCodigo())
                    .orElseThrow(() -> new NotValidDataException("Preparo de produto não encontrado."));

            ItemPedido item = new ItemPedido();
            item.setQuantidadePorcao(itemDTO.getQuantidadePorcao());
            item.setPreparoProduto(preparo);

            listaItensConvertidos.add(item);
        }

        pedido.setListaItens(listaItensConvertidos);

        String mensagemErros = pedido.validar();
        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }

        try {
            pedido = pedidoRepository.save(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao incluir o pedido - " + ex.getMessage());
        }

        return this.toDTO(pedido);
    }

    public PedidoDTO alterar(PedidoDTO pedidoDTO) throws NotValidDataException, NotFoundException {
        if (pedidoDTO.getListaItens() == null || pedidoDTO.getListaItens().isEmpty()) {
            throw new NotValidDataException("Pedido deve conter pelo menos um item");
        }

        Pedido pedidoExistente = pedidoRepository.findById(pedidoDTO.getCodigo())
            .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        // Atualiza cliente e entregador
        try {
            ClienteDTO clienteDTO = clienteNegocio.pesquisaCodigo(pedidoDTO.getCliente().getCodigo());
            pedidoDTO.setCliente(clienteDTO);
        } catch (NotFoundException e) {
            throw new NotFoundException("Cliente não encontrado");
        }

        try {
            EntregadorDTO entregadorDTO = entregadorNegocio.pesquisaCodigo(pedidoDTO.getEntregador().getCodigo());
            pedidoDTO.setEntregador(entregadorDTO);
        } catch (Exception e) {
            throw new NotFoundException("Entregador não encontrado");
        }

        Pedido pedido = this.toEntity(pedidoDTO);

        // Atualiza a lista de itens com os dados corretos
        List<ItemPedido> listaItensConvertidos = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : pedidoDTO.getListaItens()) {
            if (itemDTO.getQuantidadePorcao() <= 0) {
                throw new NotValidDataException("Quantidade de porções inválida.");
            }

            PreparoProduto preparo = preparoProdutoRepository.findById(itemDTO.getPreparoProduto().getCodigo())
                    .orElseThrow(() -> new NotValidDataException("Preparo de produto não encontrado."));

            ItemPedido item = new ItemPedido();
            item.setCodigo(itemDTO.getCodigo());
            item.setQuantidadePorcao(itemDTO.getQuantidadePorcao());
            item.setPreparoProduto(preparo);

            listaItensConvertidos.add(item);
        }

        pedido.setListaItens(listaItensConvertidos);

        String mensagemErros = pedido.validar();
        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }

        try {
            pedido = pedidoRepository.save(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao alterar o pedido - " + ex.getMessage());
        }

        return this.toDTO(pedido);
    }


    public PedidoDTO iniciarProducao(int codigo) throws NotValidDataException, NotFoundException {
        try {
            Pedido pedido = pedidoRepository.findById(codigo).orElse(null);
            if (pedido == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
            
            if (pedido.getEstado() != EstadoPedidoEnum.REGISTRADO) {
                throw new NotValidDataException("Pedido deve estar no estado REGISTRADO para iniciar producao");
            }
            
            pedido.setHoraProducao(LocalTime.now());
            pedido.setEstado(EstadoPedidoEnum.PRODUCAO);
            pedido = pedidoRepository.save(pedido);
            
            return this.toDTO(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao iniciar producao do pedido - " + ex.getMessage());
        }
    }

    public PedidoDTO finalizarProducao(int codigo) throws NotValidDataException, NotFoundException {
        try {
            Pedido pedido = pedidoRepository.findById(codigo).orElse(null);
            if (pedido == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
            
            if (pedido.getEstado() != EstadoPedidoEnum.PRODUCAO) {
                throw new NotValidDataException("Pedido deve estar no estado PRODUCAO para finalizar");
            }
            
            pedido.setHoraPronto(LocalTime.now());
            pedido.setEstado(EstadoPedidoEnum.PRONTO);
            pedido = pedidoRepository.save(pedido);
            
            return this.toDTO(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao finalizar producao do pedido - " + ex.getMessage());
        }
    }

    public PedidoDTO iniciarEntrega(int codigo, int codigoEntregador) throws NotValidDataException, NotFoundException {
        Pedido pedido = pedidoRepository.findById(codigo)
            .orElseThrow(() -> new NotFoundException("Nao existe esse pedido"));

        if (pedido.getEstado() != EstadoPedidoEnum.PRONTO) {
            throw new NotValidDataException("Pedido deve estar no estado PRONTO para iniciar entrega");
        }

        EntregadorDTO entregadorDTO = entregadorNegocio.pesquisaCodigo(codigoEntregador);
        Entregador entregador = modelMapper.map(entregadorDTO, Entregador.class);

        pedido.setHoraEntrega(LocalTime.now());
        pedido.setEstado(EstadoPedidoEnum.ENTREGA);
        pedido.setEntregador(entregador);

        try {
            pedido = pedidoRepository.save(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao iniciar entrega do pedido - " + ex.getMessage());
        }

        return this.toDTO(pedido);
    }

    public PedidoDTO finalizarEntrega(int codigo) throws NotValidDataException, NotFoundException {
        try {
            Pedido pedido = pedidoRepository.findById(codigo).orElse(null);
            if (pedido == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
            
            if (pedido.getEstado() != EstadoPedidoEnum.ENTREGA) {
                throw new NotValidDataException("Pedido deve estar no estado ENTREGA para finalizar");
            }
            
            pedido.setHoraFinalizado(LocalTime.now());
            pedido.setEstado(EstadoPedidoEnum.CONCLUIDO);
            pedido = pedidoRepository.save(pedido);
            
            return this.toDTO(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao finalizar entrega do pedido - " + ex.getMessage());
        }
    }

    public PedidoDTO cancelarPedido(int codigo) throws NotValidDataException, NotFoundException {
        try {
            Pedido pedido = pedidoRepository.findById(codigo).orElse(null);
            if (pedido == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
            
            if (pedido.getEstado() != EstadoPedidoEnum.REGISTRADO) {
                throw new NotValidDataException("Apenas pedidos no estado REGISTRADO podem ser cancelados");
            }
            
            pedido.setEstado(EstadoPedidoEnum.CANCELADO);
            pedido = pedidoRepository.save(pedido);
            
            return this.toDTO(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao cancelar pedido - " + ex.getMessage());
        }
    }

    public List<PedidoDTO> pesquisaTodos() throws NotFoundException {
        try {
            return this.toDTOAll(pedidoRepository.findAll());
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar pedidos - " + ex.getMessage());
        }
    }

    public List<PedidoDTO> pesquisaPorEstado(EstadoPedidoEnum estado) throws NotFoundException {
        try {
            return this.toDTOAll(pedidoRepository.findByEstadoOrderByDataPedidoAscHoraPedidoAsc(estado));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar pedidos por estado - " + ex.getMessage());
        }
    }

    public List<PedidoDTO> pesquisaPorData(LocalDate data) throws NotFoundException {
        try {
            return this.toDTOAll(pedidoRepository.findByDataPedido(data));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar pedidos por data - " + ex.getMessage());
        }
    }

    public PedidoDTO pesquisaCodigo(int codigo) throws NotFoundException {
        try {
            return this.toDTO(pedidoRepository.findById(codigo).orElse(null));
        } catch (Exception ex) {
            throw new NotFoundException("Erro ao pesquisar pedido pelo codigo - " + ex.getMessage());
        }
    }

    public List<PedidoDTO> toDTOAll(List<Pedido> listaPedido) {
        List<PedidoDTO> listaDTO = new ArrayList<PedidoDTO>();

        for (Pedido pedido : listaPedido) {
            listaDTO.add(this.toDTO(pedido));
        }
        return listaDTO;
    }

    public PedidoDTO toDTO(Pedido pedido) {
        return this.modelMapper.map(pedido, PedidoDTO.class);
    }

    public Pedido toEntity(PedidoDTO pedidoDTO) {
        return this.modelMapper.map(pedidoDTO, Pedido.class);
    }
}

