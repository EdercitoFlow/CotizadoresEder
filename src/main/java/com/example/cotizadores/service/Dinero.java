package com.example.cotizadores.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

final class Dinero {
    private Dinero() {
    }

    static BigDecimal redondear(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
