package com.example.cotizadores.service;

import com.example.cotizadores.dto.RentaRequest;
import com.example.cotizadores.dto.RentaResponse;
import com.example.cotizadores.exception.ReglaNegocioException;
import com.example.cotizadores.model.TipoVehiculo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RentaService {

    private static final int KM_INCLUIDOS_POR_DIA = 100;
    private static final BigDecimal COSTO_KM_ADICIONAL = new BigDecimal("4");
    private static final BigDecimal CARGO_EDAD = new BigDecimal("0.15");
    private static final BigDecimal SEGURO_POR_DIA = new BigDecimal("180");
    private static final BigDecimal DESCUENTO_LARGA_DURACION = new BigDecimal("0.10");

    private static final int EDAD_MINIMA = 18;
    private static final int EDAD_LIMITE_CARGO = 24;
    private static final int EDAD_MINIMA_CAMIONETA = 25;
    private static final int DIAS_MAXIMOS = 30;
    private static final int KM_MAXIMOS = 5000;
    private static final int DIAS_PARA_DESCUENTO = 7;

    public RentaResponse cotizar(RentaRequest request) {
        validar(request);

        int dias = request.diasRenta();
        BigDecimal diasBd = BigDecimal.valueOf(dias);

        BigDecimal costoRenta = request.tipoVehiculo().getCostoDiario().multiply(diasBd);

        int kmIncluidos = dias * KM_INCLUIDOS_POR_DIA;
        int kmAdicionales = Math.max(0, request.kilometrosEstimados() - kmIncluidos);
        BigDecimal cargoKm = COSTO_KM_ADICIONAL.multiply(BigDecimal.valueOf(kmAdicionales));

        // El cargo por edad se calcula sobre renta + km adicionales (antes del descuento)
        BigDecimal cargoEdad = request.edadConductor() <= EDAD_LIMITE_CARGO
                ? costoRenta.add(cargoKm).multiply(CARGO_EDAD)
                : BigDecimal.ZERO;

        BigDecimal seguro = Boolean.TRUE.equals(request.seguroCompleto())
                ? SEGURO_POR_DIA.multiply(diasBd)
                : BigDecimal.ZERO;

        // El descuento aplica únicamente al costo de la renta
        BigDecimal descuento = dias >= DIAS_PARA_DESCUENTO
                ? costoRenta.multiply(DESCUENTO_LARGA_DURACION)
                : BigDecimal.ZERO;

        BigDecimal total = costoRenta.add(cargoKm).add(cargoEdad).add(seguro).subtract(descuento);

        return new RentaResponse(
                request.nombreCliente(),
                request.tipoVehiculo(),
                dias,
                Dinero.redondear(costoRenta),
                kmIncluidos,
                kmAdicionales,
                Dinero.redondear(cargoKm),
                Dinero.redondear(cargoEdad),
                Dinero.redondear(seguro),
                Dinero.redondear(descuento),
                Dinero.redondear(total));
    }

    private void validar(RentaRequest r) {
        if (r.edadConductor() < EDAD_MINIMA) {
            throw new ReglaNegocioException("El conductor debe tener al menos 18 años");
        }
        if (r.diasRenta() > DIAS_MAXIMOS) {
            throw new ReglaNegocioException("La renta no puede superar 30 días");
        }
        if (r.kilometrosEstimados() > KM_MAXIMOS) {
            throw new ReglaNegocioException("Los kilómetros estimados no pueden superar 5,000");
        }
        if (r.tipoVehiculo() == TipoVehiculo.CAMIONETA && r.edadConductor() < EDAD_MINIMA_CAMIONETA) {
            throw new ReglaNegocioException("Para rentar una CAMIONETA el conductor debe tener al menos 25 años");
        }
    }
}
