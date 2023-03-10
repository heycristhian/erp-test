package br.com.senior.erp.usecase.pedido;

import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.com.senior.erp.util.MessageUtil.BUSCANDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.PEDIDO_ENTIDADE_NOME;

@Slf4j
@Component
public class BuscarPedido {

    private final PedidoRepository pedidoRepository;

    public BuscarPedido(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido porId(UUID id) {
        log.info(BUSCANDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException(PEDIDO_ENTIDADE_NOME, id));
    }

    public Page<Pedido> todos(Pageable pageable) {
        log.info(BUSCANDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        return pedidoRepository.findAll(pageable);
    }
}
