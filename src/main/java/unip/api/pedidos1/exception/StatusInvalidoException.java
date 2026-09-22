package unip.api.pedidos1.exception;

public class StatusInvalidoException extends RuntimeException{
    public StatusInvalidoException(String statusRecebido) {
        super("status inválido: '" + statusRecebido + "'. Valores aceitos: CRIADO, CONFIRMADO, CANCELADO");
    }
}
