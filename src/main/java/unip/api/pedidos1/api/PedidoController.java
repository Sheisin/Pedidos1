package unip.api.pedidos1.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unip.api.pedidos1.dto.PedidoRequest;
import unip.api.pedidos1.dto.PedidoResponse;
import unip.api.pedidos1.dto.StatusUpdateRequest;
import unip.api.pedidos1.service.PedidoService;

import java.util.List;

/**
 * Camada de API (API/Controller): responsável pela interface HTTP,
 * recebe requisições e produz respostas.
 */
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // POST /pedidos -> 201 Created + pedido criado
    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse pedidoCriado = pedidoService.criarPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    // GET /pedidos/{id} -> 200 OK ou 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> consultarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.consultarPedido(id));
    }

    // GET /pedidos -> lista de pedidos, sem paginação nesta versão
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    // PATCH /pedidos/{id}/status -> atualiza apenas o estado do pedido
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, request.getStatus()));
    }
}
