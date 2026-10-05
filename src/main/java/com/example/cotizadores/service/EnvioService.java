package com.example.cotizadores.service;

import com.example.cotizadores.dto.EnvioRequest;
import com.example.cotizadores.dto.EnvioResponse;
import com.example.cotizadores.exception.ReglaNegocioException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EnvioService {

    private static final BigDecimal COSTO_BASE = new BigDecimal("80");
    private static final BigDecimal COSTO_POR_KG = new BigDecimal("12");
    private static final BigDecimal UMBRAL_VOLUMEN_EXTRA = new BigDecimal("50000");
    private static final BigDecimal CARGO_VOLUMEN = new BigDecimal("100");
    private static final BigDecimal UMBRAL_SEGURO = new BigDecimal("10000");
    private static final BigDecimal PORCENTAJE_SEGURO = new BigDecimal("0.02");

    private static final BigDecimal PESO_MAXIMO_KG = new BigDecimal("50");
    private static final BigDecimal DIMENSION_MAXIMA_CM = new BigDecimal("150");
    private static final BigDecimal VOLUMEN_MAXIMO_CM3 = new BigDecimal("1000000");

    public EnvioResponse cotizar(EnvioRequest request) {
        BigDecimal volumen = request.largoCm()
                .multiply(request.anchoCm())
                .multiply(request.altoCm());

        validarPaquete(request, volumen);

        BigDecimal costoPorPeso = COSTO_POR_KG.multiply(request.pesoKg());
        BigDecimal cargoVolumen = volumen.compareTo(UMBRAL_VOLUMEN_EXTRA) > 0
                ? CARGO_VOLUMEN : BigDecimal.ZERO;

        BigDecimal acumulado = COSTO_BASE.add(costoPorPeso).add(cargoVolumen);
        BigDecimal recargoTipoEnvio = acumulado.multiply(request.tipoEnvio().getRecargo());

        BigDecimal seguro = request.valorDeclarado().compareTo(UMBRAL_SEGURO) > 0
                ? request.valorDeclarado().multiply(PORCENTAJE_SEGURO)
                : BigDecimal.ZERO;

        BigDecimal total = acumulado.add(recargoTipoEnvio).add(seguro);

        return new EnvioResponse(
                request.codigoPostal(),
                request.tipoEnvio(),
                volumen,
                Dinero.redondear(COSTO_BASE),
                Dinero.redondear(costoPorPeso),
                Dinero.redondear(cargoVolumen),
                Dinero.redondear(recargoTipoEnvio),
                Dinero.redondear(seguro),
                Dinero.redondear(total));
    }

    private void validarPaquete(EnvioRequest r, BigDecimal volumen) {
        if (r.pesoKg().compareTo(PESO_MAXIMO_KG) > 0) {
            throw new ReglaNegocioException("No se aceptan paquetes de más de 50 kg");
        }
        if (r.largoCm().compareTo(DIMENSION_MAXIMA_CM) > 0
                || r.anchoCm().compareTo(DIMENSION_MAXIMA_CM) > 0
                || r.altoCm().compareTo(DIMENSION_MAXIMA_CM) > 0) {
            throw new ReglaNegocioException("No se aceptan paquetes con alguna dimensión superior a 150 cm");
        }
        if (volumen.compareTo(VOLUMEN_MAXIMO_CM3) > 0) {
            throw new ReglaNegocioException("No se aceptan paquetes con volumen superior a 1,000,000 cm3");
        }
    }
}
