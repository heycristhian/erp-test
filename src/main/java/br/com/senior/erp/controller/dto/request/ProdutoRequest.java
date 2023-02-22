package br.com.senior.erp.controller.dto.request;

import br.com.senior.erp.enums.SituacaoProduto;
import br.com.senior.erp.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoRequest {

    @NotBlank(message = "Nome {string.not.blank}")
    private String nome;

    @NotBlank(message = "Descricao {string.not.blank}")
    private String descricao;

    @Positive(message = "Preco {big.decimal.positive}")
    private BigDecimal preco;

    @NotNull(message = "TipoProduto {objeto.not.null}")
    private TipoProduto tipoProduto;

    private SituacaoProduto situacaoProduto;
}
