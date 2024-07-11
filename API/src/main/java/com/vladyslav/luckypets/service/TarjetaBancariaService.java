package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.TarjetaBancaria;
import com.vladyslav.luckypets.repository.TarjetaBancariaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TarjetaBancariaService {

    @Autowired
    private TarjetaBancariaRepository tarjetaBancariaRepository;

    @Transactional
    public void updateTarjetas(List<TarjetaBancaria> tarjetas) {
        tarjetaBancariaRepository.saveAll(tarjetas);
    }
    
    public List<TarjetaBancaria> findAll() {
        return tarjetaBancariaRepository.findAll();
    }

    public Optional<TarjetaBancaria> findByNumeroTarjeta(Long numeroTarjeta) {
        return tarjetaBancariaRepository.findByNumeroTarjeta(numeroTarjeta);
    }

    public List<TarjetaBancaria> findByUserId(Long userId) {
        return tarjetaBancariaRepository.findByUsuario_UserID(userId);
    }

    public TarjetaBancaria save(TarjetaBancaria tarjetaBancaria) {
        return tarjetaBancariaRepository.save(tarjetaBancaria);
    }

    public void deleteById(Long id) {
        tarjetaBancariaRepository.deleteById(id);
    }
   
}