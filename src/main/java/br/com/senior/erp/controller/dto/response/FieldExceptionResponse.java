package br.com.senior.erp.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class FieldExceptionResponse {

    private final String field;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object parameter;
}
