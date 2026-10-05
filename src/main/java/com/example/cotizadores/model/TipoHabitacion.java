package com.example.cotizadores.model;

import java.math.BigDecimal;

public enum TipoHabitacion {
    INDIVIDUAL("700", 1),
    DOBLE("1100", 2),
    SUITE("1800", 4);

    private final BigDecimal costoNoche;
    private final int capacidad;

    TipoHabitacion(String costoNoche, int capacidad) {
        this.costoNoche = new BigDecimal(costoNoche);
        this.capacidad = capacidad;
    }

    public BigDecimal getCostoNoche() {
        return costoNoche;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
