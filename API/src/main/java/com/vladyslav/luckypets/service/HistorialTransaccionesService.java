package com.vladyslav.luckypets.service;

import com.vladyslav.luckypets.model.Anuncios;
import com.vladyslav.luckypets.model.HistorialTransacciones;
import com.vladyslav.luckypets.model.Usuarios;
import com.vladyslav.luckypets.repository.AnunciosRepository;
import com.vladyslav.luckypets.repository.HistorialTransaccionesRepository;
import com.vladyslav.luckypets.repository.UsuariosRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialTransaccionesService {

    @Autowired
    private HistorialTransaccionesRepository historialTransaccionesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private AnunciosRepository anunciosRepository;

    public List<HistorialTransacciones> findAll() {
        return historialTransaccionesRepository.findAll();
    }

    public Optional<HistorialTransacciones> findById(Long id) {
        return historialTransaccionesRepository.findById(id);
    }

    public HistorialTransacciones save(HistorialTransacciones transaccion) {
        return historialTransaccionesRepository.save(transaccion);
    }

    public void deleteById(Long id) {
        historialTransaccionesRepository.deleteById(id);
    }

    @Transactional
    public void confirmarTransaccion(Anuncios anuncio, Usuarios usuarioCliente, double comision) {
        Usuarios usuarioPropietario = anuncio.getUsuario();
        double montoAnuncio = anuncio.getCostoCR();
        double montoCliente = montoAnuncio - comision;

        // Crear transacción de sustracción para el propietario
        HistorialTransacciones transaccionPropietario = new HistorialTransacciones(
            usuarioPropietario,
            LocalDate.now(),
            -montoAnuncio,
            HistorialTransacciones.TipoTransaccion.sustraccion,
            anuncio,
            usuarioCliente
        );
        historialTransaccionesRepository.save(transaccionPropietario);

        // Actualizar saldo del propietario
        usuarioPropietario.setSaldoCR(usuarioPropietario.getSaldoCR() - montoAnuncio);
        usuariosRepository.save(usuarioPropietario);

        // Crear transacción de adición para el cliente
        HistorialTransacciones transaccionCliente = new HistorialTransacciones(
            usuarioCliente,
            LocalDate.now(),
            montoCliente,
            HistorialTransacciones.TipoTransaccion.adicion,
            anuncio,
            usuarioPropietario
        );
        historialTransaccionesRepository.save(transaccionCliente);

        // Actualizar saldo del cliente
        usuarioCliente.setSaldoCR(usuarioCliente.getSaldoCR() + montoCliente);
        usuariosRepository.save(usuarioCliente);
    }
}