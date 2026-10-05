package com.example.cotizadores.controller;

import com.example.cotizadores.dto.HospedajeRequest;
import com.example.cotizadores.dto.HospedajeResponse;
import com.example.cotizadores.service.HospedajeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospedajes")
public class HospedajeController {

    private final HospedajeService hospedajeService;

    public HospedajeController(HospedajeService hospedajeService) {
        this.hospedajeService = hospedajeService;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<HospedajeResponse> cotizar(@Valid @RequestBody HospedajeRequest request) {
        return ResponseEntity.ok(hospedajeService.cotizar(request));
    }
}
