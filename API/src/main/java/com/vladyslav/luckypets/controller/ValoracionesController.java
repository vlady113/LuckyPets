package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.Valoraciones;
import com.vladyslav.luckypets.service.ValoracionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionesController {

    @Autowired
    private ValoracionesService valoracionesService;

    @GetMapping
    public List<Valoraciones> getAllValoraciones() {
        return valoracionesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Valoraciones> getValoracionById(@PathVariable Long id) {
        Optional<Valoraciones> valoracion = valoracionesService.findById(id);
        if (valoracion.isPresent()) {
            return ResponseEntity.ok(valoracion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Valoraciones createValoracion(@RequestBody Valoraciones valoracion) {
        return valoracionesService.save(valoracion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Valoraciones> updateValoracion(@PathVariable Long id, @RequestBody Valoraciones valoracionDetails) {
        Optional<Valoraciones> optionalValoracion = valoracionesService.findById(id);
        if (optionalValoracion.isPresent()) {
            Valoraciones valoracion = optionalValoracion.get();
            valoracion.setUsuario(valoracionDetails.getUsuario());
            valoracion.setValoracion(valoracionDetails.getValoracion());
            final Valoraciones updatedValoracion = valoracionesService.save(valoracion);
            return ResponseEntity.ok(updatedValoracion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValoracion(@PathVariable Long id) {
        valoracionesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}