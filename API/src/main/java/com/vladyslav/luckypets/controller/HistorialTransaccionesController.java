package com.vladyslav.luckypets.controller;

import com.vladyslav.luckypets.model.HistorialTransacciones;
import com.vladyslav.luckypets.service.HistorialTransaccionesService;
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

	// Obtener todas las transacciones
	@GetMapping
	public List<HistorialTransacciones> getAllHistorialTransacciones() {
		return historialTransaccionesService.findAll();
	}

	// Obtener una transacción por ID
	@GetMapping("/{id}")
	public ResponseEntity<HistorialTransacciones> getHistorialTransaccionById(@PathVariable Long id) {
		Optional<HistorialTransacciones> transaccion = historialTransaccionesService.findById(id);
		if (transaccion.isPresent()) {
			return ResponseEntity.ok(transaccion.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Crear una nueva transacción
	@PostMapping
	public HistorialTransacciones createHistorialTransaccion(@RequestBody HistorialTransacciones transaccion) {
		return historialTransaccionesService.save(transaccion);
	}

	// Actualizar una transacción existente
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

	// Eliminar una transacción
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHistorialTransaccion(@PathVariable Long id) {
		historialTransaccionesService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}