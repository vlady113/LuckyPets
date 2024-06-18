package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.HistorialTransacciones;
import com.vladyslav.luckypets.repository.HistorialTransaccionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialTransaccionesService {

	@Autowired
	private HistorialTransaccionesRepository historialTransaccionesRepository;

	public List<HistorialTransacciones> findAll() {
		return historialTransaccionesRepository.findAll();
	}

	public Optional<HistorialTransacciones> findById(Long id) {
		return historialTransaccionesRepository.findById(id);
	}

	public HistorialTransacciones save(HistorialTransacciones transaccion) {
		return historialTransaccionesRepository.save(transaccion);
	}

	public void deleteById(Long id) {
		historialTransaccionesRepository.deleteById(id);
	}

}