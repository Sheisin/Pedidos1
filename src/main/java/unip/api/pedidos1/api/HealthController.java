package unip.api.pedidos1.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint de saúde. Não representa uma funcionalidade de negócio;
 * pode ser utilizado posteriormente para disponibilidade, balanceamento
 * e monitoramento.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
