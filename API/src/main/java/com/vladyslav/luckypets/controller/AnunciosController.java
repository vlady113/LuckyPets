package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.Anuncios;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.service.AnunciosService;
import com.vladyslav.luckypets.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/anuncios")
public class AnunciosController {

    @Autowired
    private AnunciosService anunciosService;

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public List<Anuncios> getAllAnuncios() {
        return anunciosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anuncios> getAnuncioById(@PathVariable Long id) {
        Optional<Anuncios> anuncio = anunciosService.findById(id);
        return anuncio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Anuncios> createAnuncio(@RequestBody Anuncios anuncio) {
        Usuarios usuario = anuncio.getUsuario();
        if (usuario != null) {
            Optional<Usuarios> existingUsuario = usuariosService.findByEmail(usuario.getEmail());
            if (existingUsuario.isPresent()) {
                anuncio.setUsuario(existingUsuario.get());
            } else {
                Usuarios savedUsuario = usuariosService.save(usuario);
                anuncio.setUsuario(savedUsuario);
            }
        }
        Anuncios savedAnuncio = anunciosService.save(anuncio);
        return ResponseEntity.ok(savedAnuncio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anuncios> updateAnuncio(@PathVariable Long id, @RequestBody Anuncios anuncioDetails) {
        Optional<Anuncios> optionalAnuncio = anunciosService.findById(id);
        if (optionalAnuncio.isPresent()) {
            Anuncios anuncio = optionalAnuncio.get();
            anuncio.setUsuario(anuncioDetails.getUsuario());
            anuncio.setFechaInicio(anuncioDetails.getFechaInicio());
            anuncio.setFechaFin(anuncioDetails.getFechaFin());
            anuncio.setDescripcion(anuncioDetails.getDescripcion());
            anuncio.setEstado(anuncioDetails.getEstado());
            anuncio.setCostoCR(anuncioDetails.getCostoCR());
            anuncio.setFotoAnuncio(anuncioDetails.getFotoAnuncio());
            final Anuncios updatedAnuncio = anunciosService.save(anuncio);
            return ResponseEntity.ok(updatedAnuncio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnuncio(@PathVariable Long id) {
        anunciosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
