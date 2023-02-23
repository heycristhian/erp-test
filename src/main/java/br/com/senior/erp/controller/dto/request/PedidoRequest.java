package br.com.senior.erp.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PedidoRequest {

    private UUID id;

    @NotNull(message = "Produtos {nao pode ser nulo")
    private List<ProdutoPedidoRequest> produtos;
}
