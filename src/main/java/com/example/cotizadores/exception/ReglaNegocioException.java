package com.example.cotizadores.exception;

/**
 * Se lanza cuando una petición es válida en formato pero incumple
 * una regla de negocio (por ejemplo, paquete demasiado pesado).
 */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String message) {
        super(message);
    }
}
