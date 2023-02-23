package br.com.senior.erp.mapper;

import br.com.senior.erp.domain.ItemPedido;
import br.com.senior.erp.domain.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemPedidoMapper {

    ItemPedidoMapper INSTANCE = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", source = "produto")
    @Mapping(target = "quantidade", source = "quantidade")
    @Mapping(target = "precoUnitario", source = "produto.preco")
    @Mapping(target = "precoTotal", expression = "java(produto.getPreco().multiply(java.math.BigDecimal.valueOf(quantidade)))")
    ItemPedido toItemPedido(Produto produto, Integer quantidade);
}
