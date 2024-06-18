package com.vladyslav.luckypets.repository;

import com.vladyslav.luckypets.model.TarjetaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TarjetaBancariaRepository extends JpaRepository<TarjetaBancaria, Long> {

    Optional<TarjetaBancaria> findByNumeroTarjeta(Long numeroTarjeta);

    List<TarjetaBancaria> findByUsuario_UserID(Long userID);
    
}