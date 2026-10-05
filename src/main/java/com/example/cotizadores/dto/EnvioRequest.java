package com.example.cotizadores.dto;

import com.example.cotizadores.model.TipoEnvio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record EnvioRequest(
        @NotBlank(message = "El código postal es obligatorio")
        @Pattern(regexp = "\\d{5}", message = "El código postal debe tener 5 dígitos")
        String codigoPostal,

        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser mayor a 0")
        BigDecimal pesoKg,

        @NotNull(message = "El largo es obligatorio")
        @Positive(message = "El largo debe ser mayor a 0")
        BigDecimal largoCm,

        @NotNull(message = "El ancho es obligatorio")
        @Positive(message = "El ancho debe ser mayor a 0")
        BigDecimal anchoCm,

        @NotNull(message = "El alto es obligatorio")
        @Positive(message = "El alto debe ser mayor a 0")
        BigDecimal altoCm,

        @NotNull(message = "El tipo de envío es obligatorio (ESTANDAR, EXPRESS, MISMO_DIA)")
        TipoEnvio tipoEnvio,

        @NotNull(message = "El valor declarado es obligatorio")
        @PositiveOrZero(message = "El valor declarado no puede ser negativo")
        BigDecimal valorDeclarado
) {
}
