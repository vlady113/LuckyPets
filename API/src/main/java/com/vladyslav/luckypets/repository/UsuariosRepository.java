package com.vladyslav.luckypets.repository;

import com.vladyslav.luckypets.model.Usuarios;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
	
    Optional<Usuarios> findByEmail(String email);
    
}
