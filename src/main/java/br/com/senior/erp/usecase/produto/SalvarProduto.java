package br.com.senior.erp.usecase.produto;

import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.enums.SituacaoProduto;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static br.com.senior.erp.util.MessageUtil.INSERINDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.MAP_PROD_REQ_TO_PROD;
import static br.com.senior.erp.util.MessageUtil.PRODUTO_ENTIDADE_NOME;

@Slf4j
@Component
public class SalvarProduto {

    private final ProdutoRepository produtoRepository;

    public SalvarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(ProdutoRequest produtoRequest) {
        log.info(MAP_PROD_REQ_TO_PROD);
        var produto = ProdutoMapper.INSTANCE.toProduto(produtoRequest, SituacaoProduto.ATIVO);

        log.info(INSERINDO_OBJETO_BD, PRODUTO_ENTIDADE_NOME);
        return produtoRepository.save(produto);
    }
}
