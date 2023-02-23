package br.com.senior.erp.controller;

import br.com.senior.erp.controller.dto.filter.PedidoFilter;
import br.com.senior.erp.controller.dto.request.PedidoRequest;
import br.com.senior.erp.controller.dto.response.PedidoResponse;
import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.mapper.PedidoMapper;
import br.com.senior.erp.usecase.pedido.AtualizarPedido;
import br.com.senior.erp.usecase.pedido.BuscarPedido;
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

import static br.com.senior.erp.util.LogMessage.INICIANDO_ATUALIZACAO;
import static br.com.senior.erp.util.LogMessage.INICIANDO_BUSCA;
import static br.com.senior.erp.util.LogMessage.INICIANDO_BUSCA_POR_ID;
import static br.com.senior.erp.util.LogMessage.INICIANDO_INSERCAO;
import static br.com.senior.erp.util.LogMessage.MAP_PED_TO_PED_RESP;
import static br.com.senior.erp.util.LogMessage.PEDIDO_ENTIDADE_NOME;
import static br.com.senior.erp.util.LogMessage.RETORNO_HTTP;

@Slf4j
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final BuscarPedido buscarPedido;
    private final SalvarPedido salvarPedido;
    private final AtualizarPedido atualizarPedido;

    public PedidoController(BuscarPedido buscarPedido, SalvarPedido salvarPedido, AtualizarPedido atualizarPedido) {
        this.buscarPedido = buscarPedido;
        this.salvarPedido = salvarPedido;
        this.atualizarPedido = atualizarPedido;
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
}
