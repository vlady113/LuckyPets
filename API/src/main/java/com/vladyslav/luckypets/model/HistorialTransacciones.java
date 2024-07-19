package com.vladyslav.luckypets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="historialtransacciones")
public class HistorialTransacciones implements Serializable{

	@Id
	@Column(name = "TransaccionID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaccionID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private Usuarios usuario;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "MontoCR", nullable = false)
    private Double montoCR;

    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo", nullable = false)
    private TipoTransaccion tipo;

    @ManyToOne
    @JoinColumn(name = "ReservaID")
    private Anuncios reserva;
    
    @ManyToOne
    @JoinColumn(name = "UserIDCliente")
    private Usuarios usuarioCliente;

    public enum TipoTransaccion {
        adicion,
        sustraccion
    }

    public HistorialTransacciones() {}

    public HistorialTransacciones(Usuarios usuario, LocalDate fecha, Double montoCR, TipoTransaccion tipo, Anuncios reserva, Usuarios usuarioCliente) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.montoCR = montoCR;
        this.tipo = tipo;
        this.reserva = reserva;
        this.usuarioCliente = usuarioCliente;
    }

    public Long getTransaccionID() {
        return transaccionID;
    }

    public void setTransaccionID(Long transaccionID) {
        this.transaccionID = transaccionID;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getMontoCR() {
        return montoCR;
    }

    public void setMontoCR(Double montoCR) {
        this.montoCR = montoCR;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public Anuncios getReserva() {
        return reserva;
    }

    public void setReserva(Anuncios reserva) {
        this.reserva = reserva;
    }

	public Usuarios getUsuarioCliente() {
		return usuarioCliente;
	}

	public void setUsuarioCliente(Usuarios usuarioCliente) {
		this.usuarioCliente = usuarioCliente;
	}
	
}
