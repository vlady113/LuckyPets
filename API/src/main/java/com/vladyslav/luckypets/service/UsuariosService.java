package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuarios> findById(Long id) {
        return usuariosRepository.findById(id);
    }

    public Optional<Usuarios> findByEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    public Usuarios save(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    public void deleteById(Long id) {
        usuariosRepository.deleteById(id);
    }
}
