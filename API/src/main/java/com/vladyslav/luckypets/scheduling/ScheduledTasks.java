package com.vladyslav.luckypets.scheduling;

import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private UsuariosService usuariosService;

    @Scheduled(fixedRate = 60000)  // Ejecuta cada minuto
    public void clearExpiredResetCodes() {
        List<Usuarios> usuarios = usuariosService.findAll();
        LocalDateTime now = LocalDateTime.now();
        usuarios.forEach(usuario -> {
            if (usuario.getCodigo_expiry() != null && usuario.getCodigo_expiry().isBefore(now)) {
                usuario.setCodigo_restablecimiento(null);
                usuario.setCodigo_expiry(null);
                usuariosService.save(usuario);
            }
        });
    }
}
