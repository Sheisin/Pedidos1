package unip.api.pedidos1.model;

/**
 * Estados iniciais do pedido, conforme especificação da disciplina.
 * Novos estados poderão surgir quando Estoque e Pagamento forem distribuídos.
 */
public enum StatusPedido {
    CRIADO,
    CONFIRMADO,
    CANCELADO
}
