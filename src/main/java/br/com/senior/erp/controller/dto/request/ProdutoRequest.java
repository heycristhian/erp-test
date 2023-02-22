package br.com.senior.erp.controller.dto.request;

import br.com.senior.erp.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoRequest {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private TipoProduto tipoProduto;
}
