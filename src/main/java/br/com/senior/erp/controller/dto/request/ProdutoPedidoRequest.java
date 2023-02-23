package br.com.senior.erp.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoPedidoRequest {

    @NotNull(message = "ID do produto {objeto.not.null}")
    private UUID id;

    @NotNull(message = "Quantidade do produto {objeto.not.null}")
    @Positive(message = "Quantidade {numero.positive}")
    private Integer quantidade;
}
