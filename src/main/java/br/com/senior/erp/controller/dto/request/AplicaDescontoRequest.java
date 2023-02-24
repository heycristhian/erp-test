package br.com.senior.erp.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AplicaDescontoRequest {

    @NotNull(message = "ID do Pedido {objeto.not.null}")
    private UUID idPedido;

    @NotNull(message = "ID do Pedido {objeto.not.null}")
    @Min(value = 0)
    @Max(value = 100)
    private BigDecimal percentualDesconto;
}
