package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.Mascotas;
import com.vladyslav.luckypets.repository.MascotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotasService {

    @Autowired
    private MascotasRepository mascotasRepository;

    public List<Mascotas> findAll() {
        return mascotasRepository.findAll();
    }

    public Optional<Mascotas> findById(Long id) {
        return mascotasRepository.findById(id);
    }

    public Mascotas save(Mascotas mascota) {
        return mascotasRepository.save(mascota);
    }

    public void deleteById(Long id) {
        mascotasRepository.deleteById(id);
    }
    
}