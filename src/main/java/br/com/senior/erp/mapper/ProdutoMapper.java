package br.com.senior.erp.mapper;

import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.controller.dto.response.ProdutoResponse;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.enums.SituacaoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoResponse toProdutoResponse(Produto produto);

    Produto toProduto(ProdutoRequest produtoRequest, SituacaoProduto situacaoProduto);
}
