package br.com.senior.erp.usecase;

import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.repository.ProdutoRepository;
import br.com.senior.erp.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AtualizarProduto {

    private final ProdutoRepository produtoRepository;

    public AtualizarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(UUID id, ProdutoRequest produtoRequest) {
        if (!existeProdutoPorId(id)) {
            throw new EntidadeNotFoundException("Produto nao encontrado para atualizar com o ID: " + id);
        }

        log.info(LogMessage.MAPEAMENTO, "ProdutoRequest", "Produto");
        Produto produto = ProdutoMapper.INSTANCE.toProduto(produtoRequest, id);

        log.info("Atualizando produto no banco de dados");
        return produtoRepository.save(produto);
    }

    private boolean existeProdutoPorId(UUID id) {
        log.info("Analizando se o produto por ID existe");
        return produtoRepository.existsById(id);
    }
}
