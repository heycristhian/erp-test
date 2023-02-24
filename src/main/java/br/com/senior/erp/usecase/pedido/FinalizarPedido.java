package br.com.senior.erp.usecase.pedido;

import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.enums.SituacaoPedido;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.exception.NegocioException;
import br.com.senior.erp.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.senior.erp.util.MessageUtil.ATUALIZANDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.BUSCANDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.PEDIDO_ENTIDADE_NOME;

@Slf4j
@Component
public class FinalizarPedido {

    private final PedidoRepository pedidoRepository;

    public FinalizarPedido(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void execute(UUID id) {
        log.info(BUSCANDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException(PEDIDO_ENTIDADE_NOME, id));

        log.info("Verificando se o pedido está apto para finalizar");
        if (SituacaoPedido.FECHADO.equals(pedido.getSituacaoPedido()) || SituacaoPedido.CANCELADO.equals(pedido.getSituacaoPedido())) {
            throw new NegocioException("Nao é possível finalizar um pedido que esteja com a situacao 'FECHADO' ou 'CANCELADO' - Situacao atual: " + pedido.getSituacaoPedido());
        }

        pedido.setSituacaoPedido(SituacaoPedido.FECHADO);

        log.info(ATUALIZANDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        pedidoRepository.save(pedido);
    }
}
