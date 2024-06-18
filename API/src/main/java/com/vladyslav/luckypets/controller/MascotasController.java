package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.Mascotas;
import com.vladyslav.luckypets.service.MascotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mascotas")
public class MascotasController {

	@Autowired
	private MascotasService mascotasService;

	// Obtener todas las mascotas
	@GetMapping
	public List<Mascotas> getAllMascotas() {
		return mascotasService.findAll();
	}

	// Obtener una mascota por ID
	@GetMapping("/{id}")
	public ResponseEntity<Mascotas> getMascotaById(@PathVariable Long id) {
		Optional<Mascotas> mascota = mascotasService.findById(id);
		if (mascota.isPresent()) {
			return ResponseEntity.ok(mascota.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear una nueva mascota
	@PostMapping
	public Mascotas createMascota(@RequestBody Mascotas mascota) {
		return mascotasService.save(mascota);
	}

	// Actualizar una mascota existente
	@PutMapping("/{id}")
	public ResponseEntity<Mascotas> updateMascota(@PathVariable Long id, @RequestBody Mascotas mascotaDetails) {
		Optional<Mascotas> optionalMascota = mascotasService.findById(id);
		if (optionalMascota.isPresent()) {
			Mascotas mascota = optionalMascota.get();
			mascota.setUsuario(mascotaDetails.getUsuario());
			mascota.setNombre(mascotaDetails.getNombre());
			mascota.setTipo(mascotaDetails.getTipo());
			mascota.setRaza(mascotaDetails.getRaza());
			mascota.setEdad(mascotaDetails.getEdad());
			mascota.setRequisitosEspeciales(mascotaDetails.getRequisitosEspeciales());
			final Mascotas updatedMascota = mascotasService.save(mascota);
			return ResponseEntity.ok(updatedMascota);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Eliminar una mascota
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
		mascotasService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
