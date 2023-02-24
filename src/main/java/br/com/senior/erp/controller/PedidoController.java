package br.com.senior.erp.controller;

import br.com.senior.erp.controller.dto.filter.PedidoFilter;
import br.com.senior.erp.controller.dto.request.AplicaDescontoRequest;
import br.com.senior.erp.controller.dto.request.PedidoRequest;
import br.com.senior.erp.controller.dto.response.PedidoResponse;
import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.mapper.PedidoMapper;
import br.com.senior.erp.usecase.pedido.AplicarDesconto;
import br.com.senior.erp.usecase.pedido.AtualizarPedido;
import br.com.senior.erp.usecase.pedido.BuscarPedido;
import br.com.senior.erp.usecase.pedido.CancelarPedido;
import br.com.senior.erp.usecase.pedido.FinalizarPedido;
import br.com.senior.erp.usecase.pedido.SalvarPedido;
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

import static br.com.senior.erp.util.MessageUtil.INICIANDO_APLICACAO_DESCONTO;
import static br.com.senior.erp.util.MessageUtil.INICIANDO_ATUALIZACAO;
import static br.com.senior.erp.util.MessageUtil.INICIANDO_BUSCA;
import static br.com.senior.erp.util.MessageUtil.INICIANDO_BUSCA_POR_ID;
import static br.com.senior.erp.util.MessageUtil.INICIANDO_CANCELAMENTO_PEDIDO;
import static br.com.senior.erp.util.MessageUtil.INICIANDO_FINALIZACAO_PEDIDO;
import static br.com.senior.erp.util.MessageUtil.INICIANDO_INSERCAO;
import static br.com.senior.erp.util.MessageUtil.MAP_PED_TO_PED_RESP;
import static br.com.senior.erp.util.MessageUtil.PEDIDO_ENTIDADE_NOME;
import static br.com.senior.erp.util.MessageUtil.RETORNO_HTTP;

@Slf4j
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final BuscarPedido buscarPedido;
    private final SalvarPedido salvarPedido;
    private final AtualizarPedido atualizarPedido;
    private final AplicarDesconto aplicarDesconto;
    private final CancelarPedido cancelarPedido;
    private final FinalizarPedido finalizarPedido;

    public PedidoController(BuscarPedido buscarPedido, SalvarPedido salvarPedido, AtualizarPedido atualizarPedido, AplicarDesconto aplicarDesconto, CancelarPedido cancelarPedido, FinalizarPedido finalizarPedido) {
        this.buscarPedido = buscarPedido;
        this.salvarPedido = salvarPedido;
        this.atualizarPedido = atualizarPedido;
        this.aplicarDesconto = aplicarDesconto;
        this.cancelarPedido = cancelarPedido;
        this.finalizarPedido = finalizarPedido;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscaPedido(@PathVariable UUID id) {
        log.info(INICIANDO_BUSCA_POR_ID, PEDIDO_ENTIDADE_NOME);
        Pedido pedido = buscarPedido.porId(id);

        log.info(MAP_PED_TO_PED_RESP);
        PedidoResponse pedidoResponse = PedidoMapper.INSTANCE.toPedidoResponse(pedido);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(pedidoResponse);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoResponse>> buscaPedidos(PedidoFilter pedidoFilter, Pageable pageable) {
        log.info(INICIANDO_BUSCA, PEDIDO_ENTIDADE_NOME);
        Page<PedidoResponse> response = buscarPedido.todos(pageable)
                .map(PedidoMapper.INSTANCE::toPedidoResponse);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/criaPedido")
    public ResponseEntity<PedidoResponse> criaPedido(@RequestBody @Valid PedidoRequest pedidoRequest, UriComponentsBuilder uriBuilder) {
        log.info(INICIANDO_INSERCAO, PEDIDO_ENTIDADE_NOME);
        Pedido pedido = salvarPedido.execute(pedidoRequest);

        log.info(MAP_PED_TO_PED_RESP);
        PedidoResponse pedidoResponse = PedidoMapper.INSTANCE.toPedidoResponse(pedido);

        URI uri = uriBuilder.path("/api/pedidos/{id}").buildAndExpand(pedidoResponse.getId()).toUri();

        log.info(RETORNO_HTTP);
        return ResponseEntity.created(uri).body(pedidoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizaPedido(@RequestBody @Valid PedidoRequest pedidoRequest, @PathVariable UUID id) {
        log.info(INICIANDO_ATUALIZACAO, PEDIDO_ENTIDADE_NOME);
        Pedido pedido = atualizarPedido.execute(id, pedidoRequest);

        log.info(MAP_PED_TO_PED_RESP);
        PedidoResponse pedidoResponse = PedidoMapper.INSTANCE.toPedidoResponse(pedido);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok(pedidoResponse);
    }

    @PostMapping("/aplicaDesconto")
    public ResponseEntity<Void> aplicaDesconto(@RequestBody @Valid AplicaDescontoRequest aplicaDescontoRequest) {
        log.info(INICIANDO_APLICACAO_DESCONTO, PEDIDO_ENTIDADE_NOME);
        aplicarDesconto.execute(aplicaDescontoRequest);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/finalizaPedido/{id}")
    public ResponseEntity<Void> finalizaPedido(@PathVariable UUID id) {
        log.info(INICIANDO_FINALIZACAO_PEDIDO, PEDIDO_ENTIDADE_NOME);
        finalizarPedido.execute(id);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancelaPedido/{id}")
    public ResponseEntity<Void> cancelaPedido(@PathVariable UUID id) {
        log.info(INICIANDO_CANCELAMENTO_PEDIDO, PEDIDO_ENTIDADE_NOME);
        cancelarPedido.execute(id);

        log.info(RETORNO_HTTP);
        return ResponseEntity.ok().build();
    }
}
