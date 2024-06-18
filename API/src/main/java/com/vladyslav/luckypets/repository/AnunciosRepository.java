package com.vladyslav.luckypets.repository;

import com.vladyslav.luckypets.model.Anuncios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnunciosRepository extends JpaRepository<Anuncios, Long> {
}
