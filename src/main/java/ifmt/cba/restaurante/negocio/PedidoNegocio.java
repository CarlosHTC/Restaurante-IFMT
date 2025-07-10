package ifmt.cba.restaurante.negocio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifmt.cba.restaurante.dto.PedidoDTO;
import ifmt.cba.restaurante.entity.EstadoPedido;
import ifmt.cba.restaurante.entity.Pedido;
import ifmt.cba.restaurante.exception.NotFoundException;
import ifmt.cba.restaurante.exception.NotValidDataException;
import ifmt.cba.restaurante.repository.PedidoRepository;

@Service
public class PedidoNegocio {

    private ModelMapper modelMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoNegocio() {
        this.modelMapper = new ModelMapper();
    }

    public PedidoDTO inserir(PedidoDTO pedidoDTO) throws NotValidDataException {

        Pedido pedido = this.toEntity(pedidoDTO);
        pedido.setDataPedido(LocalDate.now());
        pedido.setHoraPedido(LocalTime.now());
        pedido.setEstado(EstadoPedido.REGISTRADO);
        
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

        Pedido pedido = this.toEntity(pedidoDTO);
        String mensagemErros = pedido.validar();
        if (!mensagemErros.isEmpty()) {
            throw new NotValidDataException(mensagemErros);
        }
        try {
            if (pedidoRepository.findById(pedido.getCodigo()).orElse(null) == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
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
            
            if (pedido.getEstado() != EstadoPedido.REGISTRADO) {
                throw new NotValidDataException("Pedido deve estar no estado REGISTRADO para iniciar producao");
            }
            
            pedido.setHoraProducao(LocalTime.now());
            pedido.setEstado(EstadoPedido.PRODUCAO);
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
            
            if (pedido.getEstado() != EstadoPedido.PRODUCAO) {
                throw new NotValidDataException("Pedido deve estar no estado PRODUCAO para finalizar");
            }
            
            pedido.setHoraPronto(LocalTime.now());
            pedido.setEstado(EstadoPedido.PRONTO);
            pedido = pedidoRepository.save(pedido);
            
            return this.toDTO(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao finalizar producao do pedido - " + ex.getMessage());
        }
    }

    public PedidoDTO iniciarEntrega(int codigo, int codigoEntregador) throws NotValidDataException, NotFoundException {
        try {
            Pedido pedido = pedidoRepository.findById(codigo).orElse(null);
            if (pedido == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
            
            if (pedido.getEstado() != EstadoPedido.PRONTO) {
                throw new NotValidDataException("Pedido deve estar no estado PRONTO para iniciar entrega");
            }
            
            pedido.setHoraEntrega(LocalTime.now());
            pedido.setEstado(EstadoPedido.ENTREGA);
            // Aqui deveria buscar o entregador pelo código e associar ao pedido
            pedido = pedidoRepository.save(pedido);
            
            return this.toDTO(pedido);
        } catch (Exception ex) {
            throw new NotValidDataException("Erro ao iniciar entrega do pedido - " + ex.getMessage());
        }
    }

    public PedidoDTO finalizarEntrega(int codigo) throws NotValidDataException, NotFoundException {
        try {
            Pedido pedido = pedidoRepository.findById(codigo).orElse(null);
            if (pedido == null) {
                throw new NotFoundException("Nao existe esse pedido");
            }
            
            if (pedido.getEstado() != EstadoPedido.ENTREGA) {
                throw new NotValidDataException("Pedido deve estar no estado ENTREGA para finalizar");
            }
            
            pedido.setHoraFinalizado(LocalTime.now());
            pedido.setEstado(EstadoPedido.CONCLUIDO);
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
            
            if (pedido.getEstado() != EstadoPedido.REGISTRADO) {
                throw new NotValidDataException("Apenas pedidos no estado REGISTRADO podem ser cancelados");
            }
            
            pedido.setEstado(EstadoPedido.CANCELADO);
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

    public List<PedidoDTO> pesquisaPorEstado(EstadoPedido estado) throws NotFoundException {
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

