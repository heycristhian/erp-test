package br.com.senior.erp.repository;

import br.com.senior.erp.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Query(value = "SELECT max(p.numeroPedido) FROM Pedido p")
    Integer maxNumeroPedido();
}
