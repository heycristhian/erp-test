package br.com.senior.erp.controller;

import br.com.senior.erp.controller.dto.filter.ProdutoFilter;
import br.com.senior.erp.controller.dto.response.ProdutoResponse;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.usecase.BuscarProduto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final BuscarProduto buscarProduto;

    public ProdutoController(BuscarProduto buscarProduto) {
        this.buscarProduto = buscarProduto;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> buscarProdutos(ProdutoFilter produtoFilter, Pageable pageable) {
        //TODO Devolver somente produtos ativos
        log.info("Iniciando busca de todos produtos");
        Page<ProdutoResponse> response = buscarProduto.execute(produtoFilter, pageable)
                .map(ProdutoMapper.INSTANCE::toProdutoResponse);

        return ResponseEntity.ok(response);
    }
}
