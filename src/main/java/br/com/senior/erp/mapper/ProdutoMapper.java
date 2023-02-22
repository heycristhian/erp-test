package br.com.senior.erp.mapper;

import br.com.senior.erp.controller.dto.request.ProdutoRequest;
import br.com.senior.erp.controller.dto.response.ProdutoResponse;
import br.com.senior.erp.domain.Produto;
import br.com.senior.erp.enums.SituacaoProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoResponse toProdutoResponse(Produto produto);

    @Mapping(target = "situacaoProduto", source = "situacaoProduto")
    Produto toProduto(ProdutoRequest produtoRequest, SituacaoProduto situacaoProduto);

    Produto toProduto(ProdutoRequest produtoRequest, UUID id);
}
