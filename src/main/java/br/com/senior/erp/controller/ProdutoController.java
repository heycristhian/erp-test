package br.com.senior.erp.controller;

import br.com.senior.erp.controller.dto.filter.ProdutoFilter;
import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.controller.dto.response.ProdutoResponse;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.usecase.AtualizarProduto;
import br.com.senior.erp.usecase.BuscarProduto;
import br.com.senior.erp.usecase.SalvarProduto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

import static br.com.senior.erp.util.LogMessage.MAPEAMENTO;
import static br.com.senior.erp.util.LogMessage.RETORNO_HTTP;

@Slf4j
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final BuscarProduto buscarProduto;
    private final SalvarProduto salvarProduto;
    private final AtualizarProduto atualizarProduto;

    public ProdutoController(BuscarProduto buscarProduto, SalvarProduto salvarProduto, AtualizarProduto atualizarProduto) {
        this.buscarProduto = buscarProduto;
        this.salvarProduto = salvarProduto;
        this.atualizarProduto = atualizarProduto;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> buscaProdutos(ProdutoFilter produtoFilter, Pageable pageable) {
        log.info("Iniciando busca de todos produtos");
        Page<ProdutoResponse> response = buscarProduto.todos(produtoFilter, pageable)
                .map(ProdutoMapper.INSTANCE::toProdutoResponse);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscaProduto(@PathVariable UUID id) {
        log.info("Iniciando busca de um produto por ID");
        Produto produto = buscarProduto.porId(id);

        log.info(MAPEAMENTO, "Produto", "ProdutoResponse");
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(produtoResponse);
    }


    @PostMapping
    public ResponseEntity<ProdutoResponse> salvaProduto(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        log.info("Iniciando inclusao de um produto na base de dados");
        Produto produto = salvarProduto.execute(produtoRequest);

        log.info(MAPEAMENTO, "Produto", "ProdutoResponse");
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produtoResponse.getId()).toUri();

        log.info(RETORNO_HTTP);
        return ResponseEntity.created(uri).body(produtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizaProduto(@PathVariable UUID id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        log.info("Iniciando atualizacao de um produto na base de dados");
        Produto produto = atualizarProduto.execute(id, produtoRequest);

        log.info(MAPEAMENTO, "Produto", "ProdutoResponse");
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(produtoResponse);
    }
}
