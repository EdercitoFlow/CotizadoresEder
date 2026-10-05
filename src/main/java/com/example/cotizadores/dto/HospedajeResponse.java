package com.example.cotizadores.dto;

import com.example.cotizadores.model.Temporada;
import com.example.cotizadores.model.TipoHabitacion;

import java.math.BigDecimal;

public record HospedajeResponse(
        String nombreHuesped,
        TipoHabitacion tipoHabitacion,
        Integer numeroNoches,
        Temporada temporada,
        BigDecimal costoHospedaje,
        BigDecimal ajusteTemporada,
        BigDecimal descuentoEstanciaLarga,
        BigDecimal costoDesayuno,
        BigDecimal costoEstacionamiento,
        BigDecimal subtotal,
        BigDecimal impuestoHospedaje,
        BigDecimal total
) {
}
