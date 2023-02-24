package br.com.senior.erp.usecase.produto;

import br.com.senior.erp.controller.dto.filter.ProdutoFilter;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.enums.SituacaoProduto;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.senior.erp.util.LogMessage.BUSCANDO_OBJETO_BD;
import static br.com.senior.erp.util.LogMessage.PRODUTO_ENTIDADE_NOME;

@Slf4j
@Component
public class BuscarProduto {

    private final ProdutoRepository produtoRepository;

    public BuscarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Page<Produto> todos(ProdutoFilter produtoFilter, Pageable pageable) {
        log.info(BUSCANDO_OBJETO_BD, PRODUTO_ENTIDADE_NOME);
        return produtoRepository.findAll(pageable);
    }

    public Produto porId(UUID id) {
        log.info(BUSCANDO_OBJETO_BD, PRODUTO_ENTIDADE_NOME);
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException(PRODUTO_ENTIDADE_NOME, id));
    }

    public Produto porIdAndSituacaoInativaNot(UUID id) {
        log.info(BUSCANDO_OBJETO_BD, PRODUTO_ENTIDADE_NOME);
        return produtoRepository.findByIdAndSituacaoProdutoNot(id, SituacaoProduto.INATIVO)
                .orElseThrow(() -> new EntidadeNotFoundException(PRODUTO_ENTIDADE_NOME, id));
    }
}
