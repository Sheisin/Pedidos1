package unip.api.pedidos1.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unip.api.pedidos1.dto.PedidoRequest;
import unip.api.pedidos1.dto.PedidoResponse;
import unip.api.pedidos1.exception.PedidoNotFoundException;
import unip.api.pedidos1.exception.StatusInvalidoException;
import unip.api.pedidos1.model.Pedido;
import unip.api.pedidos1.model.StatusPedido;
import unip.api.pedidos1.repository.PedidoRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Camada de lógica (Service): concentra a lógica da aplicação,
 * executa regras e coordena operações entre API e Repository.
 */
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        BigDecimal valorTotal = request.getValorUnitario()
                .multiply(BigDecimal.valueOf(request.getQuantidade()));

        Pedido pedido = Pedido.builder()
                .cliente(request.getCliente())
                .produto(request.getProduto())
                .quantidade(request.getQuantidade())
                .valorUnitario(request.getValorUnitario())
                .valorTotal(valorTotal)
                .status(StatusPedido.CRIADO)
                .build();

        Pedido salvo = pedidoRepository.save(pedido);
        return PedidoResponse.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public PedidoResponse consultarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return PedidoResponse.fromEntity(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponse::fromEntity)
                .toList();
    }

    @Transactional
    public PedidoResponse atualizarStatus(Long id, String statusTexto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        StatusPedido novoStatus = converterStatus(statusTexto);

        pedido.setStatus(novoStatus);
        Pedido atualizado = pedidoRepository.save(pedido);

        return PedidoResponse.fromEntity(atualizado);
    }

    private StatusPedido converterStatus(String statusPedido){
        String format = statusPedido.trim().toUpperCase();

        for (StatusPedido status : StatusPedido.values()){
            if(status.name().equals(format)){
                return status;
            }
        }
        throw new StatusInvalidoException(statusPedido);
    }
}
