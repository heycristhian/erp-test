package br.com.senior.erp.usecase.pedido;

import br.com.senior.erp.controller.dto.request.AplicaDescontoRequest;
import br.com.senior.erp.domain.ItemPedido;
import br.com.senior.erp.domain.Pedido;
import br.com.senior.erp.enums.SituacaoPedido;
import br.com.senior.erp.enums.TipoProduto;
import br.com.senior.erp.exception.EntidadeNotFoundException;
import br.com.senior.erp.exception.NegocioException;
import br.com.senior.erp.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.senior.erp.util.MessageUtil.BUSCANDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.INSERINDO_OBJETO_BD;
import static br.com.senior.erp.util.MessageUtil.PEDIDO_ENTIDADE_NOME;

@Slf4j
@Component
public class AplicarDesconto {

    private final PedidoRepository pedidoRepository;

    public AplicarDesconto(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void execute(AplicaDescontoRequest aplicaDescontoRequest) {
        log.info(BUSCANDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        Pedido pedido = pedidoRepository.findById(aplicaDescontoRequest.getIdPedido())
                .orElseThrow(() -> new EntidadeNotFoundException(PEDIDO_ENTIDADE_NOME, aplicaDescontoRequest.getIdPedido()));

        log.info("Verificando se o pedido está em situacao aberta");
        if (!SituacaoPedido.ABERTO.equals(pedido.getSituacaoPedido())) {
            throw new NegocioException("Nao é possível aplicar desconto para pedido diferente de 'ABERTO' - Situacao atual: " + pedido.getSituacaoPedido());
        }

        handlePedido(pedido, aplicaDescontoRequest.getPercentualDesconto());

        log.info(INSERINDO_OBJETO_BD, PEDIDO_ENTIDADE_NOME);
        pedidoRepository.save(pedido);
    }

    private void handlePedido(Pedido pedido, BigDecimal percentualDesconto) {
        BigDecimal novoValor = calculaNovoValor(pedido, percentualDesconto);
        BigDecimal valorDesconto = calculaValorDesconto(pedido, novoValor);

        log.info("Atualizando objeto para os novos valores calculados");
        pedido.setValorDesconto(valorDesconto);
        pedido.setPercentualDesconto(percentualDesconto);
        pedido.setValorTotal(novoValor);
    }

    private BigDecimal calculaNovoValor(Pedido pedido, BigDecimal percentualDesconto) {
        log.info("Calculando novo valor do pedido");
        BigDecimal percentual = (BigDecimal.valueOf(100).subtract(percentualDesconto))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);

        return pedido.getItens().stream()
                .map(i -> {
                    if (TipoProduto.FISICO.equals(i.getProduto().getTipoProduto())) {
                        return i.getPrecoTotal().multiply(percentual);
                    }
                    return i.getPrecoTotal();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculaValorDesconto(Pedido pedido, BigDecimal novoValor) {
        return pedido.getItens()
                .stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(novoValor);
    }
}
