package br.com.senior.erp.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExceptionResponse {

    private int code;
    private String status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldExceptionResponse> fields;
}
