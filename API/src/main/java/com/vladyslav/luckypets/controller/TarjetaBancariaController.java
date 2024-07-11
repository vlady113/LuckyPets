package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.TarjetaBancaria;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.service.TarjetaBancariaService;
import com.vladyslav.luckypets.service.UsuariosService;

import DTO.TarjetaBancariaDTO;

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
        try {
            List<TarjetaBancaria> tarjetas = tarjetaBancariaService.findAll();
            return ResponseEntity.ok(tarjetas);
        } catch (Exception e) {
            logger.error("Error retrieving all tarjetas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{numeroTarjeta}")
    public ResponseEntity<TarjetaBancaria> getTarjetaByNumero(@PathVariable Long numeroTarjeta) {
        try {
            Optional<TarjetaBancaria> tarjeta = tarjetaBancariaService.findByNumeroTarjeta(numeroTarjeta);
            return tarjeta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            logger.error("Error retrieving tarjeta with numeroTarjeta: " + numeroTarjeta, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<TarjetaBancaria>> getTarjetasByUserEmail(@PathVariable String email) {
        try {
            Optional<Usuarios> usuarioOpt = usuariosService.findByEmail(email);
            if (!usuarioOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            List<TarjetaBancaria> tarjetas = tarjetaBancariaService.findByUserId(usuarioOpt.get().getUserID());
            return ResponseEntity.ok(tarjetas);
        } catch (Exception e) {
            logger.error("Error retrieving tarjetas for email: " + email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTarjeta(@RequestBody TarjetaBancariaDTO tarjetaDTO) {
        logger.debug("Datos de la tarjeta recibida: {}", tarjetaDTO);
        try {
            if (tarjetaDTO.getNumeroTarjeta() == null || tarjetaDTO.getCvv() == null) {
                return ResponseEntity.badRequest().body("Número de tarjeta y CVV son obligatorios.");
            }

            Optional<TarjetaBancaria> existingTarjeta = tarjetaBancariaService.findByNumeroTarjeta(tarjetaDTO.getNumeroTarjeta());
            if (existingTarjeta.isPresent()) {
                return ResponseEntity.badRequest().body("El número de tarjeta ya está registrado.");
            }

            Optional<Usuarios> usuarioOpt = usuariosService.findByEmail(tarjetaDTO.getUsuarioEmail());
            if (!usuarioOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Usuario no encontrado.");
            }

            Usuarios usuario = usuarioOpt.get();

            TarjetaBancaria tarjeta = new TarjetaBancaria(
                tarjetaDTO.getNumeroTarjeta(),
                tarjetaDTO.getFechaCaducidad(),
                tarjetaDTO.getTitularTarjeta(),
                tarjetaDTO.getEmisorTarjeta(),
                tarjetaDTO.getCvv(),
                tarjetaDTO.getImgTarjeta(),
                usuario
            );

            TarjetaBancaria savedTarjeta = tarjetaBancariaService.save(tarjeta);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTarjeta);
        } catch (Exception e) {
            logger.error("Error creating tarjeta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating tarjeta.");
        }
    }

    @PutMapping("/updatePositions")
    public ResponseEntity<Void> updateTarjetas(@RequestBody List<TarjetaBancaria> tarjetas) {
        try {
            tarjetaBancariaService.updateTarjetas(tarjetas);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating tarjetas positions", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{numeroTarjeta}")
    public ResponseEntity<TarjetaBancaria> updateTarjeta(@PathVariable Long numeroTarjeta, @RequestBody TarjetaBancaria tarjetaDetails) {
        try {
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
        } catch (Exception e) {
            logger.error("Error updating tarjeta with numeroTarjeta: " + numeroTarjeta, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarjeta(@PathVariable Long id) {
        try {
            tarjetaBancariaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting tarjeta with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
