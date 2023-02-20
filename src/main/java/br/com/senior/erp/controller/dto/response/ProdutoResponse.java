package br.com.senior.erp.controller.dto.response;

import br.com.senior.erp.enums.SituacaoProduto;
import br.com.senior.erp.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoResponse {

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private TipoProduto tipoProduto;
    private SituacaoProduto situacaoProduto;
}
