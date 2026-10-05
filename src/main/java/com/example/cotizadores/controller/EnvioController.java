package com.example.cotizadores.controller;

import com.example.cotizadores.dto.EnvioRequest;
import com.example.cotizadores.dto.EnvioResponse;
import com.example.cotizadores.service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<EnvioResponse> cotizar(@Valid @RequestBody EnvioRequest request) {
        return ResponseEntity.ok(envioService.cotizar(request));
    }
}
