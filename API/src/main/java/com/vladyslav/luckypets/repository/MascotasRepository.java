package com.vladyslav.luckypets.repository;

import com.vladyslav.luckypets.model.Mascotas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotasRepository extends JpaRepository<Mascotas, Long> {
	
}