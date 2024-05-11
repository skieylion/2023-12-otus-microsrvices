package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос создания исходящего обмена")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExchangeCreateDto(
        @Schema(description = "ИНН получателя") String inn,
        @Schema(description = "КПП получателя") String kpp
) {
}