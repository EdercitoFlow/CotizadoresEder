package com.example.cotizadores;

import com.example.cotizadores.dto.EnvioRequest;
import com.example.cotizadores.dto.HospedajeRequest;
import com.example.cotizadores.dto.RentaRequest;
import com.example.cotizadores.exception.ReglaNegocioException;
import com.example.cotizadores.model.Temporada;
import com.example.cotizadores.model.TipoEnvio;
import com.example.cotizadores.model.TipoHabitacion;
import com.example.cotizadores.model.TipoVehiculo;
import com.example.cotizadores.service.EnvioService;
import com.example.cotizadores.service.HospedajeService;
import com.example.cotizadores.service.RentaService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CotizadoresServiceTest {

    private static void assertMonto(String esperado, BigDecimal real) {
        assertEquals(0, new BigDecimal(esperado).compareTo(real),
                "Esperado " + esperado + " pero fue " + real);
    }

    @Test
    void envioExpressConSeguro() {
        EnvioRequest req = new EnvioRequest("62577", new BigDecimal("8.5"),
                new BigDecimal("40"), new BigDecimal("30"), new BigDecimal("25"),
                TipoEnvio.EXPRESS, new BigDecimal("12000"));
        // (80 + 102) * 1.4 = 254.80 + seguro 240 = 494.80
        assertMonto("494.80", new EnvioService().cotizar(req).total());
    }

    @Test
    void envioPesadoSeRechaza() {
        EnvioRequest req = new EnvioRequest("62577", new BigDecimal("51"),
                BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN,
                TipoEnvio.ESTANDAR, BigDecimal.ZERO);
        assertThrows(ReglaNegocioException.class, () -> new EnvioService().cotizar(req));
    }

    @Test
    void rentaSuvConductorJoven() {
        RentaRequest req = new RentaRequest("Ana Martínez", 24, TipoVehiculo.SUV, 5, 650, true);
        // 4750 + 600 + 802.50 + 900 = 7052.50
        assertMonto("7052.50", new RentaService().cotizar(req).total());
    }

    @Test
    void camionetaMenorDe25SeRechaza() {
        RentaRequest req = new RentaRequest("Ana", 24, TipoVehiculo.CAMIONETA, 3, 100, false);
        assertThrows(ReglaNegocioException.class, () -> new RentaService().cotizar(req));
    }

    @Test
    void hospedajeDobleTemporadaAlta() {
        HospedajeRequest req = new HospedajeRequest("Carlos López", TipoHabitacion.DOBLE,
                4, 2, Temporada.ALTA, true, true);
        // 4400 + 1100 + 1200 + 400 = 7100; impuesto 284; total 7384
        assertMonto("7384.00", new HospedajeService().cotizar(req).total());
    }

    @Test
    void hospedajeExcedeCapacidad() {
        HospedajeRequest req = new HospedajeRequest("Carlos", TipoHabitacion.INDIVIDUAL,
                2, 2, Temporada.REGULAR, false, false);
        assertThrows(ReglaNegocioException.class, () -> new HospedajeService().cotizar(req));
    }
}
