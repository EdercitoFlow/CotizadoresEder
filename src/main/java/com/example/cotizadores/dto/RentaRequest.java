package com.example.cotizadores.dto;

import com.example.cotizadores.model.TipoVehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RentaRequest(
        @NotBlank(message = "El nombre del cliente es obligatorio")
        String nombreCliente,

        @NotNull(message = "La edad del conductor es obligatoria")
        @Positive(message = "La edad debe ser mayor a 0")
        Integer edadConductor,

        @NotNull(message = "El tipo de vehículo es obligatorio (COMPACTO, SEDAN, SUV, CAMIONETA)")
        TipoVehiculo tipoVehiculo,

        @NotNull(message = "Los días de renta son obligatorios")
        @Positive(message = "Los días de renta deben ser mayores a 0")
        Integer diasRenta,

        @NotNull(message = "Los kilómetros estimados son obligatorios")
        @PositiveOrZero(message = "Los kilómetros estimados no pueden ser negativos")
        Integer kilometrosEstimados,

        @NotNull(message = "Debe indicar si contrata el seguro completo")
        Boolean seguroCompleto
) {
}
