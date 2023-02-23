package br.com.senior.erp.mapper;

import br.com.senior.erp.controller.dto.request.PedidoRequest;
import br.com.senior.erp.controller.dto.response.PedidoResponse;
import br.com.senior.erp.domain.ItemPedido;
import br.com.senior.erp.domain.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    PedidoResponse toPedidoResponse(Pedido pedido);

    @Mapping(target = "id", source = "pedidoRequest.id")
    @Mapping(target = "itens", source = "itens")
    @Mapping(target = "numeroPedido", source = "numeroPedido")
    @Mapping(target = "situacaoPedido", expression = "java(br.com.senior.erp.enums.SituacaoPedido.ABERTO)")
    @Mapping(target = "valorTotal", expression = "java(itens.stream().map(i -> i.getPrecoTotal()).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add))")
    Pedido toPedido(PedidoRequest pedidoRequest, List<ItemPedido> itens, Integer numeroPedido);
}
