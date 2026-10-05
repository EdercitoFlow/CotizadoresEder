package com.example.cotizadores.model;

import java.math.BigDecimal;

public enum TipoVehiculo {
    COMPACTO("550"),
    SEDAN("700"),
    SUV("950"),
    CAMIONETA("1200");

    private final BigDecimal costoDiario;

    TipoVehiculo(String costoDiario) {
        this.costoDiario = new BigDecimal(costoDiario);
    }

    public BigDecimal getCostoDiario() {
        return costoDiario;
    }
}
