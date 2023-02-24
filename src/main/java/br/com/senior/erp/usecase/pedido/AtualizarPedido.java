package br.com.senior.erp.usecase.pedido;

import br.com.senior.erp.controller.dto.request.PedidoRequest;
import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.enums.SituacaoPedido;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.senior.erp.util.MessageUtil.INSERINDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.PEDIDO_ENTIDADE_NOME;

@Slf4j
@Component
public class AtualizarPedido {

    private final PedidoRepository pedidoRepository;
    private final CriarPedido criarPedido;

    public AtualizarPedido(PedidoRepository pedidoRepository, CriarPedido criarPedido) {
        this.pedidoRepository = pedidoRepository;
        this.criarPedido = criarPedido;
    }

    public Pedido execute(UUID id, PedidoRequest pedidoRequest) {
        log.info("Verificando se o pedido existe na base de dados");
        Pedido pedido = pedidoRepository.findByIdAndSituacaoPedido(id, SituacaoPedido.ABERTO)
                .map(p -> handlePedido(id, pedidoRequest, p.getNumeroPedido(), p.getCreateDateTime()))
                .orElseThrow(() -> new EntidadeNotFoundException("Nao é possível atualizar pedido que nao consta no sistema ou nao esta em situacao aberta (ID: " + id + ")"));

        log.info(INSERINDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        return pedidoRepository.save(pedido);
    }

    private Pedido handlePedido(UUID id, PedidoRequest pedidoRequest, Integer numeroPedido, LocalDateTime createDateTime) {
        Pedido pedido = criarPedido.execute(pedidoRequest, false);
        pedido.setNumeroPedido(numeroPedido);
        pedido.setCreateDateTime(createDateTime);
        pedido.setId(id);

        return pedido;
    }
}
