package com.vladyslav.luckypets.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "anuncios")
public class Anuncios implements Serializable {

    @Id
    @Column(name = "AnuncioID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anuncioID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID", nullable = false)
    private Usuarios usuario;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "Descripcion", length = 555)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "Estado", length = 255)
    private EstadoAnuncio estado;

    @Column(name = "costo_cr", nullable = false)
    private Double costoCR;

    @Lob
    @Column(name = "foto_anuncio")
    private byte[] fotoAnuncio;

    @Column(name = "email_cliente", nullable = true)
    private String emailCliente;

    public enum EstadoAnuncio {
        pendiente("pendiente"),
        en_curso("en_curso"),
        completado("completado");

        private final String value;

        EstadoAnuncio(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static EstadoAnuncio fromValue(String value) {
            for (EstadoAnuncio estado : EstadoAnuncio.values()) {
                if (estado.value.equalsIgnoreCase(value) || estado.name().replace("_", " ").equalsIgnoreCase(value)) {
                    return estado;
                }
            }
            throw new IllegalArgumentException("No enum constant " + EstadoAnuncio.class.getName() + "." + value);
        }
    }

    public Anuncios() {}

    public Anuncios(Long anuncioID, Usuarios usuario, LocalDateTime fechaInicio, LocalDateTime fechaFin, String descripcion, EstadoAnuncio estado, Double costoCR, byte[] fotoAnuncio, String emailCliente) {
        this.anuncioID = anuncioID;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
        this.estado = estado;
        this.costoCR = costoCR;
        this.fotoAnuncio = fotoAnuncio;
        this.emailCliente = emailCliente;
    }

    // Getters and Setters

    public Long getAnuncioID() {
        return anuncioID;
    }

    public void setAnuncioID(Long anuncioID) {
        this.anuncioID = anuncioID;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoAnuncio getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnuncio estado) {
        this.estado = estado;
    }

    public Double getCostoCR() {
        return costoCR;
    }

    public void setCostoCR(Double costoCR) {
        this.costoCR = costoCR;
    }

    public byte[] getFotoAnuncio() {
        return fotoAnuncio;
    }

    public void setFotoAnuncio(byte[] fotoAnuncio) {
        this.fotoAnuncio = fotoAnuncio;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
}