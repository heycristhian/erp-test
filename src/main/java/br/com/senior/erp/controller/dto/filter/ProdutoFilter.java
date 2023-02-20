package br.com.senior.erp.controller.dto.filter;

import br.com.senior.erp.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoFilter {

    private String nome;
    private String descricao;
    private TipoProduto tipoProduto;
}
