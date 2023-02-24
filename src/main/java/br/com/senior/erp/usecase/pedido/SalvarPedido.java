package br.com.senior.erp.usecase.pedido;

import br.com.senior.erp.controller.dto.request.PedidoRequest;
import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static br.com.senior.erp.util.MessageUtil.INSERINDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.PEDIDO_ENTIDADE_NOME;

@Slf4j
@Component
public class SalvarPedido {

    private final CriarPedido criarPedido;
    private final PedidoRepository pedidoRepository;

    public SalvarPedido(CriarPedido criarPedido, PedidoRepository pedidoRepository) {
        this.criarPedido = criarPedido;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(PedidoRequest pedidoRequest) {
        Pedido pedido = criarPedido.execute(pedidoRequest, true);

        log.info(INSERINDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        return pedidoRepository.save(pedido);
    }
}
