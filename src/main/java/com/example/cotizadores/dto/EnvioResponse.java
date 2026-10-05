package com.example.cotizadores.dto;

import com.example.cotizadores.model.TipoEnvio;

import java.math.BigDecimal;

public record EnvioResponse(
        String codigoPostal,
        TipoEnvio tipoEnvio,
        BigDecimal volumenCm3,
        BigDecimal costoBase,
        BigDecimal costoPorPeso,
        BigDecimal cargoPorVolumen,
        BigDecimal recargoTipoEnvio,
        BigDecimal seguro,
        BigDecimal total
) {
}
