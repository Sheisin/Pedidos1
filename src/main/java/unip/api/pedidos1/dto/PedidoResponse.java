package unip.api.pedidos1.dto;


import lombok.*;
import unip.api.pedidos1.model.Pedido;
import unip.api.pedidos1.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representação de Pedido devolvida pela API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Long id;
    private String cliente;
    private String produto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private LocalDateTime dataCriacao;

    public static PedidoResponse fromEntity(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente())
                .produto(pedido.getProduto())
                .quantidade(pedido.getQuantidade())
                .valorUnitario(pedido.getValorUnitario())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus())
                .dataCriacao(pedido.getDataCriacao())
                .build();
    }
}
