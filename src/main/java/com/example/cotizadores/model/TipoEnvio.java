package com.example.cotizadores.model;

import java.math.BigDecimal;

public enum TipoEnvio {
    ESTANDAR("0"),
    EXPRESS("0.40"),
    MISMO_DIA("0.70");

    private final BigDecimal recargo;

    TipoEnvio(String recargo) {
        this.recargo = new BigDecimal(recargo);
    }

    public BigDecimal getRecargo() {
        return recargo;
    }
}
