package com.example.cotizadores.service;

import com.example.cotizadores.dto.HospedajeRequest;
import com.example.cotizadores.dto.HospedajeResponse;
import com.example.cotizadores.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class HospedajeService {

    private static final BigDecimal DESCUENTO_TEMPORADA_BAJA = new BigDecimal("0.10");
    private static final BigDecimal CARGO_TEMPORADA_ALTA = new BigDecimal("0.25");
    private static final BigDecimal DESAYUNO_POR_PERSONA_NOCHE = new BigDecimal("150");
    private static final BigDecimal ESTACIONAMIENTO_POR_NOCHE = new BigDecimal("100");
    private static final BigDecimal DESCUENTO_ESTANCIA_LARGA = new BigDecimal("0.08");
    private static final BigDecimal IMPUESTO = new BigDecimal("0.04");

    private static final int NOCHES_MAXIMAS = 30;
    private static final int NOCHES_PARA_DESCUENTO = 7;

    public HospedajeResponse cotizar(HospedajeRequest request) {
        validar(request);

        int noches = request.numeroNoches();
        BigDecimal nochesBd = BigDecimal.valueOf(noches);
        BigDecimal huespedesBd = BigDecimal.valueOf(request.numeroHuespedes());

        BigDecimal costoHospedaje = request.tipoHabitacion().getCostoNoche().multiply(nochesBd);

        // Positivo = cargo, negativo = descuento
        BigDecimal ajusteTemporada = switch (request.temporada()) {
            case BAJA -> costoHospedaje.multiply(DESCUENTO_TEMPORADA_BAJA).negate();
            case REGULAR -> BigDecimal.ZERO;
            case ALTA -> costoHospedaje.multiply(CARGO_TEMPORADA_ALTA);
        };

        // Descuento adicional sobre el costo del hospedaje (no aplica a desayuno ni estacionamiento)
        BigDecimal descuentoEstancia = noches >= NOCHES_PARA_DESCUENTO
                ? costoHospedaje.multiply(DESCUENTO_ESTANCIA_LARGA)
                : BigDecimal.ZERO;

        BigDecimal desayuno = Boolean.TRUE.equals(request.incluyeDesayuno())
                ? huespedesBd.multiply(nochesBd).multiply(DESAYUNO_POR_PERSONA_NOCHE)
                : BigDecimal.ZERO;

        BigDecimal estacionamiento = Boolean.TRUE.equals(request.incluyeEstacionamiento())
                ? nochesBd.multiply(ESTACIONAMIENTO_POR_NOCHE)
                : BigDecimal.ZERO;

        BigDecimal subtotal = costoHospedaje
                .add(ajusteTemporada)
                .subtract(descuentoEstancia)
                .add(desayuno)
                .add(estacionamiento);

        BigDecimal impuesto = subtotal.multiply(IMPUESTO);
        BigDecimal total = subtotal.add(impuesto);

        return new HospedajeResponse(
                request.nombreHuesped(),
                request.tipoHabitacion(),
                noches,
                request.temporada(),
                Dinero.redondear(costoHospedaje),
                Dinero.redondear(ajusteTemporada),
                Dinero.redondear(descuentoEstancia),
                Dinero.redondear(desayuno),
                Dinero.redondear(estacionamiento),
                Dinero.redondear(subtotal),
                Dinero.redondear(impuesto),
                Dinero.redondear(total));
    }

    private void validar(HospedajeRequest r) {
        if (r.numeroNoches() > NOCHES_MAXIMAS) {
            throw new ReglaNegocioException("La reservación no puede superar 30 noches");
        }
        int capacidad = r.tipoHabitacion().getCapacidad();
        if (r.numeroHuespedes() > capacidad) {
            throw new ReglaNegocioException("La habitación " + r.tipoHabitacion()
                    + " admite máximo " + capacidad + " persona(s)");
        }
    }
}
