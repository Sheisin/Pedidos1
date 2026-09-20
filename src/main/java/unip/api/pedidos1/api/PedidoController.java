package unip.api.pedidos1.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido. A aplicação calcula o valor_total e define o status inicial como CRIADO.")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse pedidoCriado = pedidoService.criarPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
    }

    // GET /pedidos/{id} -> 200 OK ou 404 Not Found
    @Operation(summary = "Consultar pedido", description = "Retorna um pedido pelo id.")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> consultarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.consultarPedido(id));
    }

    // GET /pedidos -> lista de pedidos
    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos existentes, sem paginação.")
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    // PATCH /pedidos/{id}/status -> atualiza apenas o estado do pedido
    @Operation(summary = "Alterar status do pedido", description = "Atualiza apenas o campo status do pedido.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, request.getStatus()));
    }
}
