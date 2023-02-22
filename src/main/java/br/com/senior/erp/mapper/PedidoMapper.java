package br.com.senior.erp.mapper;

import br.com.senior.erp.controller.dto.response.PedidoResponse;
import br.com.senior.erp.domain.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    PedidoResponse toPedidoResponse(Pedido pedido);
}
