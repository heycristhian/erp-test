package br.com.senior.erp.controller;

import br.com.senior.erp.controller.dto.filter.ProdutoFilter;
import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.controller.dto.response.ProdutoResponse;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.usecase.BuscarProduto;
import br.com.senior.erp.usecase.SalvarProduto;
import br.com.senior.erp.util.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final BuscarProduto buscarProduto;
    private final SalvarProduto salvarProduto;

    public ProdutoController(BuscarProduto buscarProduto, SalvarProduto salvarProduto) {
        this.buscarProduto = buscarProduto;
        this.salvarProduto = salvarProduto;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> buscaProdutos(ProdutoFilter produtoFilter, Pageable pageable) {
        log.info("Iniciando busca de todos produtos");
        Page<ProdutoResponse> response = buscarProduto.execute(produtoFilter, pageable)
                .map(ProdutoMapper.INSTANCE::toProdutoResponse);

        log.info(LogMessage.RETORNO_HTTP);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvaProduto(@RequestBody ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        log.info("Iniciando inclusao de um produto na base de dados");
        Produto produto = salvarProduto.execute(produtoRequest);

        log.info("Mapeando Produto para ProdutoResponse");
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produtoResponse.getId()).toUri();

        log.info(LogMessage.RETORNO_HTTP);
        return ResponseEntity.created(uri).body(produtoResponse);
    }
}
