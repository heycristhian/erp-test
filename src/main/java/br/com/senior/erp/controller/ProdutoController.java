package br.com.senior.erp.controller;

import br.com.senior.erp.controller.dto.filter.ProdutoFilter;
import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.controller.dto.response.ProdutoResponse;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.mapper.ProdutoMapper;
import br.com.senior.erp.usecase.produto.AtualizarProduto;
import br.com.senior.erp.usecase.produto.BuscarProduto;
import br.com.senior.erp.usecase.produto.RemoverProduto;
import br.com.senior.erp.usecase.produto.SalvarProduto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import static br.com.senior.erp.util.LogMessage.INICIANDO_ATUALIZACAO;
import static br.com.senior.erp.util.LogMessage.INICIANDO_BUSCA;
import static br.com.senior.erp.util.LogMessage.INICIANDO_BUSCA_POR_ID;
import static br.com.senior.erp.util.LogMessage.INICIANDO_DELECAO;
import static br.com.senior.erp.util.LogMessage.INICIANDO_INSERCAO;
import static br.com.senior.erp.util.LogMessage.MAP_PROD_TO_PROD_RESP;
import static br.com.senior.erp.util.LogMessage.PRODUTO_ENTIDADE_NOME;
import static br.com.senior.erp.util.LogMessage.RETORNO_HTTP;

@Slf4j
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final BuscarProduto buscarProduto;
    private final SalvarProduto salvarProduto;
    private final AtualizarProduto atualizarProduto;
    private final RemoverProduto removerProduto;

    public ProdutoController(BuscarProduto buscarProduto, SalvarProduto salvarProduto, AtualizarProduto atualizarProduto, RemoverProduto removerProduto) {
        this.buscarProduto = buscarProduto;
        this.salvarProduto = salvarProduto;
        this.atualizarProduto = atualizarProduto;
        this.removerProduto = removerProduto;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> buscaProdutos(ProdutoFilter produtoFilter, Pageable pageable) {
        log.info(INICIANDO_BUSCA, PRODUTO_ENTIDADE_NOME);
        Page<ProdutoResponse> response = buscarProduto.todos(produtoFilter, pageable)
                .map(ProdutoMapper.INSTANCE::toProdutoResponse);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscaProduto(@PathVariable UUID id) {
        log.info(INICIANDO_BUSCA_POR_ID, PRODUTO_ENTIDADE_NOME);
        Produto produto = buscarProduto.porId(id);

        log.info(MAP_PROD_TO_PROD_RESP);
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(produtoResponse);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvaProduto(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        log.info(INICIANDO_INSERCAO, PRODUTO_ENTIDADE_NOME);
        Produto produto = salvarProduto.execute(produtoRequest);

        log.info(MAP_PROD_TO_PROD_RESP);
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        URI uri = uriBuilder.path("/api/produtos/{id}").buildAndExpand(produtoResponse.getId()).toUri();

        log.info(RETORNO_HTTP);
        return ResponseEntity.created(uri).body(produtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizaProduto(@PathVariable UUID id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        log.info(INICIANDO_ATUALIZACAO, PRODUTO_ENTIDADE_NOME);
        Produto produto = atualizarProduto.execute(id, produtoRequest);

        log.info(MAP_PROD_TO_PROD_RESP);
        ProdutoResponse produtoResponse = ProdutoMapper.INSTANCE.toProdutoResponse(produto);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(produtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaProduto(@PathVariable UUID id) {
        log.info(INICIANDO_DELECAO, PRODUTO_ENTIDADE_NOME);
        removerProduto.execute(id);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok().build();
    }
}
