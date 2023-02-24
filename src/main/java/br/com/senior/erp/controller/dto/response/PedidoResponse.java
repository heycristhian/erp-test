package br.com.senior.erp.controller.dto.response;

import br.com.senior.erp.enums.SituacaoPedido;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PedidoResponse {

    private UUID id;
    private Integer numeroPedido;
    private List<ItemPedidoResponse> itens;
    private BigDecimal valorTotal;
    private SituacaoPedido situacaoPedido;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal percentualDesconto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorDesconto;
}
