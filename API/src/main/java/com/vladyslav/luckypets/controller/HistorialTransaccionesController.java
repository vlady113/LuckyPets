package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.Anuncios;
import com.vladyslav.luckypets.model.HistorialTransacciones;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.service.AnunciosService;
import com.vladyslav.luckypets.service.HistorialTransaccionesService;
import com.vladyslav.luckypets.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historialtransacciones")
public class HistorialTransaccionesController {

    @Autowired
    private HistorialTransaccionesService historialTransaccionesService;

    @Autowired
    private AnunciosService anunciosService;

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public List<HistorialTransacciones> getAllHistorialTransacciones() {
        return historialTransaccionesService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialTransacciones> getHistorialTransaccionById(@PathVariable Long id) {
        Optional<HistorialTransacciones> transaccion = historialTransaccionesService.findById(id);
        if (transaccion.isPresent()) {
            return ResponseEntity.ok(transaccion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public HistorialTransacciones createHistorialTransaccion(@RequestBody HistorialTransacciones transaccion) {
        return historialTransaccionesService.save(transaccion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialTransacciones> updateHistorialTransaccion(@PathVariable Long id,
            @RequestBody HistorialTransacciones transaccionDetails) {
        Optional<HistorialTransacciones> optionalTransaccion = historialTransaccionesService.findById(id);
        if (optionalTransaccion.isPresent()) {
            HistorialTransacciones transaccion = optionalTransaccion.get();
            transaccion.setUsuario(transaccionDetails.getUsuario());
            transaccion.setFecha(transaccionDetails.getFecha());
            transaccion.setMontoCR(transaccionDetails.getMontoCR());
            transaccion.setTipo(transaccionDetails.getTipo());
            transaccion.setReserva(transaccionDetails.getReserva());
            final HistorialTransacciones updatedTransaccion = historialTransaccionesService.save(transaccion);
            return ResponseEntity.ok(updatedTransaccion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialTransaccion(@PathVariable Long id) {
        historialTransaccionesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirmar")
    public ResponseEntity<Void> confirmarTransaccion(@RequestParam Long anuncioId, @RequestParam String emailCliente) {
        Optional<Anuncios> anuncioOpt = anunciosService.findById(anuncioId);
        Optional<Usuarios> usuarioClienteOpt = usuariosService.findByEmail(emailCliente);

        if (anuncioOpt.isPresent() && usuarioClienteOpt.isPresent()) {
            Anuncios anuncio = anuncioOpt.get();
            Usuarios usuarioCliente = usuarioClienteOpt.get();

            historialTransaccionesService.confirmarTransaccion(anuncio, usuarioCliente, 15.0); // Asumiendo que la comisión es de 15 CR

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
