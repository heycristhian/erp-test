package br.com.senior.erp.repository;

import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.enums.SituacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    Optional<Produto> findByIdAndSituacaoProdutoNot(UUID id, SituacaoProduto situacaoProduto);
}
