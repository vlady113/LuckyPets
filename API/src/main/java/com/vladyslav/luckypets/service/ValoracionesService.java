package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.Valoraciones;
import com.vladyslav.luckypets.repository.ValoracionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValoracionesService {

    @Autowired
    private ValoracionesRepository valoracionesRepository;

    public List<Valoraciones> findAll() {
        return valoracionesRepository.findAll();
    }

    public Optional<Valoraciones> findById(Long id) {
        return valoracionesRepository.findById(id);
    }

    public Valoraciones save(Valoraciones valoracion) {
        return valoracionesRepository.save(valoracion);
    }

    public void deleteById(Long id) {
        valoracionesRepository.deleteById(id);
    }
    
}