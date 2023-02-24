package br.com.senior.erp.usecase.produto;

import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.senior.erp.util.LogMessage.ATUALIZANDO_OBJETO_BD;
import static br.com.senior.erp.util.LogMessage.MAP_PROD_REQ_TO_PROD;
import static br.com.senior.erp.util.LogMessage.PRODUTO_ENTIDADE_NOME;

@Slf4j
@Component
public class AtualizarProduto {

    private final ProdutoRepository produtoRepository;

    public AtualizarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(UUID id, ProdutoRequest produtoRequest) {
        if (!existeProdutoPorId(id)) {
            throw new EntidadeNotFoundException(PRODUTO_ENTIDADE_NOME, id);
        }

        log.info(MAP_PROD_REQ_TO_PROD);
        Produto produto = ProdutoMapper.INSTANCE.toProduto(produtoRequest, id);

        log.info(ATUALIZANDO_OBJETO_BD, "produto");
        return produtoRepository.save(produto);
    }

    private boolean existeProdutoPorId(UUID id) {
        log.info("Analizando se o produto por ID existe");
        return produtoRepository.existsById(id);
    }
}
