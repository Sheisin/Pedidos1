package unip.api.pedidos1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unip.api.pedidos1.model.Pedido;

/**
 * encapsula o acesso ao banco.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
