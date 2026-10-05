package com.example.cotizadores.controller;

import com.example.cotizadores.dto.RentaRequest;
import com.example.cotizadores.dto.RentaResponse;
import com.example.cotizadores.service.RentaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentas")
public class RentaController {

    private final RentaService rentaService;

    public RentaController(RentaService rentaService) {
        this.rentaService = rentaService;
    }

    @PostMapping("/cotizar")
    public ResponseEntity<RentaResponse> cotizar(@Valid @RequestBody RentaRequest request) {
        return ResponseEntity.ok(rentaService.cotizar(request));
    }
}
