package br.com.senior.erp.usecase;

import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.enums.SituacaoProduto;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.repository.ProdutoRepository;
import br.com.senior.erp.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SalvarProduto {

    private final ProdutoRepository produtoRepository;

    public SalvarProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto execute(ProdutoRequest produtoRequest) {
        log.info(LogMessage.MAPEAMENTO, "ProdutoRequest", "Produto");
        var produto = ProdutoMapper.INSTANCE.toProduto(produtoRequest, SituacaoProduto.ATIVO);

        log.info("Inserindo no banco de dados");
        return produtoRepository.save(produto);
    }
}
