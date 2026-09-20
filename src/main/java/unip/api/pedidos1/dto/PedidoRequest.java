package unip.api.pedidos1.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Corpo de entrada esperado por POST /pedidos.
 * A aplicação calcula valor_total e define o status inicial (não são
 * informados pelo cliente).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    @NotBlank(message = "cliente é obrigatório")
    private String cliente;

    @NotBlank(message = "produto é obrigatório")
    private String produto;

    @NotNull(message = "quantidade é obrigatória")
    @Min(value = 1, message = "quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "valor_unitario é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "valor_unitario deve ser maior que zero")
    private BigDecimal valorUnitario;
}
