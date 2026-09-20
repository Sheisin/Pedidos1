package unip.api.pedidos1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unip.api.pedidos1.model.StatusPedido;

/**
 * Corpo de entrada esperado por PATCH /pedidos/{id}/status.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateRequest {

    @NotNull(message = "status é obrigatório")
    private StatusPedido status;
}
