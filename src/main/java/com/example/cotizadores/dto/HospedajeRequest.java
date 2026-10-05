package com.example.cotizadores.dto;

import com.example.cotizadores.model.Temporada;
import com.example.cotizadores.model.TipoHabitacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HospedajeRequest(
        @NotBlank(message = "El nombre del huésped es obligatorio")
        String nombreHuesped,

        @NotNull(message = "El tipo de habitación es obligatorio (INDIVIDUAL, DOBLE, SUITE)")
        TipoHabitacion tipoHabitacion,

        @NotNull(message = "El número de noches es obligatorio")
        @Positive(message = "El número de noches debe ser mayor a 0")
        Integer numeroNoches,

        @NotNull(message = "El número de huéspedes es obligatorio")
        @Positive(message = "El número de huéspedes debe ser mayor a 0")
        Integer numeroHuespedes,

        @NotNull(message = "La temporada es obligatoria (BAJA, REGULAR, ALTA)")
        Temporada temporada,

        @NotNull(message = "Debe indicar si incluye desayuno")
        Boolean incluyeDesayuno,

        @NotNull(message = "Debe indicar si incluye estacionamiento")
        Boolean incluyeEstacionamiento
) {
}
