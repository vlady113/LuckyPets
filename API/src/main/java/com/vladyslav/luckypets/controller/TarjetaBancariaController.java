package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.TarjetaBancaria;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.service.TarjetaBancariaService;
import com.vladyslav.luckypets.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarjetas")
public class TarjetaBancariaController {

    private static final Logger logger = LoggerFactory.getLogger(TarjetaBancariaController.class);

    @Autowired
    private TarjetaBancariaService tarjetaBancariaService;

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public ResponseEntity<List<TarjetaBancaria>> getAllTarjetas() {
        List<TarjetaBancaria> tarjetas = tarjetaBancariaService.findAll();
        return ResponseEntity.ok(tarjetas);
    }

    @GetMapping("/{numeroTarjeta}")
    public ResponseEntity<TarjetaBancaria> getTarjetaByNumero(@PathVariable Long numeroTarjeta) {
        Optional<TarjetaBancaria> tarjeta = tarjetaBancariaService.findByNumeroTarjeta(numeroTarjeta);
        return tarjeta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/usuario/{userID}")
    public ResponseEntity<List<TarjetaBancaria>> getTarjetasByUserId(@PathVariable Long userID) {
        List<TarjetaBancaria> tarjetas = tarjetaBancariaService.findByUserId(userID);
        return ResponseEntity.ok(tarjetas);
    }

    @PostMapping
    public ResponseEntity<?> createTarjeta(@RequestBody TarjetaBancaria tarjeta) {
        logger.debug("Datos de la tarjeta recibida: {}", tarjeta);

        if (tarjeta.getNumeroTarjeta() == null || tarjeta.getCvv() == null) {
            return ResponseEntity.badRequest().body("Número de tarjeta y CVV son obligatorios.");
        }

        Optional<TarjetaBancaria> existingTarjeta = tarjetaBancariaService
                .findByNumeroTarjeta(tarjeta.getNumeroTarjeta());
        if (existingTarjeta.isPresent()) {
            return ResponseEntity.badRequest().body("El número de tarjeta ya está registrado.");
        }

        Optional<Usuarios> usuarioOpt = usuariosService.findByEmail(tarjeta.getUsuario().getEmail());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }

        tarjeta.setUsuario(usuarioOpt.get());
        TarjetaBancaria savedTarjeta = tarjetaBancariaService.save(tarjeta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTarjeta);
    }
    
    @PutMapping("/updatePositions")
    public ResponseEntity<Void> updateTarjetas(@RequestBody List<TarjetaBancaria> tarjetas) {
        for (TarjetaBancaria tarjeta : tarjetas) {
            tarjetaBancariaService.save(tarjeta);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{numeroTarjeta}")
    public ResponseEntity<TarjetaBancaria> updateTarjeta(@PathVariable Long numeroTarjeta,
            @RequestBody TarjetaBancaria tarjetaDetails) {
        Optional<TarjetaBancaria> optionalTarjeta = tarjetaBancariaService.findByNumeroTarjeta(numeroTarjeta);
        if (optionalTarjeta.isPresent()) {
            TarjetaBancaria tarjeta = optionalTarjeta.get();
            tarjeta.setTitularTarjeta(tarjetaDetails.getTitularTarjeta());
            tarjeta.setFechaCaducidad(tarjetaDetails.getFechaCaducidad());
            tarjeta.setEmisorTarjeta(tarjetaDetails.getEmisorTarjeta());
            tarjeta.setCvv(tarjetaDetails.getCvv());
            tarjeta.setImgTarjeta(tarjetaDetails.getImgTarjeta());
            final TarjetaBancaria updatedTarjeta = tarjetaBancariaService.save(tarjeta);
            return ResponseEntity.ok(updatedTarjeta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        tarjetaBancariaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
