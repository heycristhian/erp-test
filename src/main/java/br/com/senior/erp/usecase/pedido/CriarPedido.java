package br.com.senior.erp.usecase.pedido;

import br.com.senior.erp.controller.dto.request.PedidoRequest;
import br.com.senior.erp.domain.ItemPedido;
import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.mapper.ItemPedidoMapper;
import br.com.senior.erp.mapper.PedidoMapper;
import br.com.senior.erp.repository.PedidoRepository;
import br.com.senior.erp.usecase.produto.BuscarProduto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static br.com.senior.erp.util.LogMessage.MAP_PED_REQ_TO_PED;
import static java.util.Objects.isNull;

@Slf4j
@Component
public class CriarPedido {

    private final BuscarProduto buscarProduto;
    private final PedidoRepository pedidoRepository;

    public CriarPedido(BuscarProduto buscarProduto, PedidoRepository pedidoRepository) {
        this.buscarProduto = buscarProduto;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido execute(PedidoRequest pedidoRequest, boolean geraNumeroPedido) {
        log.info(MAP_PED_REQ_TO_PED);

        List<ItemPedido> itens = new ArrayList<>();

        pedidoRequest.getProdutos()
                .forEach(p -> {
                    var produto = buscarProduto.porIdAndSituacaoInativaNot(p.getId());
                    itens.add(
                            ItemPedidoMapper.INSTANCE.toItemPedido(produto, p.getQuantidade())
                    );
                });
        var numeroPedido = geraNumeroPedido ? criaNumeroPedido() : null;
        return PedidoMapper.INSTANCE.toPedido(pedidoRequest, itens, numeroPedido);
    }

    private Integer criaNumeroPedido() {
        var numeroPedido = pedidoRepository.maxNumeroPedido();
        return isNull(numeroPedido) ? 1 : numeroPedido + 1;
    }
}
