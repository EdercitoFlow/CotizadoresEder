package com.example.cotizadores.dto;

import com.example.cotizadores.model.TipoVehiculo;

import java.math.BigDecimal;

public record RentaResponse(
        String nombreCliente,
        TipoVehiculo tipoVehiculo,
        Integer diasRenta,
        BigDecimal costoRenta,
        Integer kilometrosIncluidos,
        Integer kilometrosAdicionales,
        BigDecimal cargoKilometrosAdicionales,
        BigDecimal cargoPorEdad,
        BigDecimal seguroCompleto,
        BigDecimal descuentoLargaDuracion,
        BigDecimal total
) {
}
