package br.com.senior.erp.usecase.produto;

import br.com.senior.erp.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.senior.erp.util.MessageUtil.REMOVENDO_OBJETO_BD;

@Slf4j
@Component
public class RemoverProduto {

    private final ProdutoRepository produtoRepository;

    public RemoverProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void execute(UUID id) {
        log.info(REMOVENDO_OBJETO_BD, "produto");
        produtoRepository.deleteById(id);
    }
}
