package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.Anuncios;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.repository.AnunciosRepository;
import com.vladyslav.luckypets.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnunciosService {

    @Autowired
    private AnunciosRepository anunciosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Anuncios> findAll() {
        List<Anuncios> anuncios = anunciosRepository.findAll();
        anuncios.forEach(anuncio -> {
            Usuarios usuario = anuncio.getUsuario();
            if (usuario != null && usuario.getUserID() != null) {
                Usuarios completeUser = usuariosRepository.findById(usuario.getUserID()).orElse(null);
                if (completeUser != null) {
                    anuncio.setUsuario(completeUser);
                }
            }
        });
        return anuncios;
    }

    public Optional<Anuncios> findById(Long id) {
        Optional<Anuncios> anuncio = anunciosRepository.findById(id);
        anuncio.ifPresent(a -> {
            Usuarios usuario = a.getUsuario();
            if (usuario != null && usuario.getUserID() != null) {
                Usuarios completeUser = usuariosRepository.findById(usuario.getUserID()).orElse(null);
                if (completeUser != null) {
                    a.setUsuario(completeUser);
                }
            }
        });
        return anuncio;
    }

    public Anuncios save(Anuncios anuncio) {
        if (anuncio.getUsuario() != null && anuncio.getUsuario().getUserID() != null) {
            Usuarios usuario = usuariosRepository.findById(anuncio.getUsuario().getUserID()).orElse(null);
            if (usuario != null) {
                anuncio.setUsuario(usuario);
            }
        }
        return anunciosRepository.save(anuncio);
    }

    public void deleteById(Long id) {
        anunciosRepository.deleteById(id);
    }
}
