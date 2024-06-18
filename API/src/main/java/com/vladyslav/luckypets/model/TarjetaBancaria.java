package com.vladyslav.luckypets.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tarjetabancaria")
public class TarjetaBancaria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_tarjeta", nullable = false, unique = true)
    private Long numeroTarjeta;

    @Column(name = "fecha_caducidad", nullable = false)
    private LocalDate fechaCaducidad;

    @Column(name = "titular_tarjeta", nullable = false, length = 255)
    private String titularTarjeta;

    @Column(name = "emisor_tarjeta", nullable = false, length = 100)
    private String emisorTarjeta;

    @Column(name = "cvv", nullable = false)
    private Integer cvv;

    @Column(name = "img_tarjeta", length = 225)
    private String imgTarjeta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "UserID")
    private Usuarios usuario;

    public TarjetaBancaria() {
    }

    public TarjetaBancaria(Long numeroTarjeta, LocalDate fechaCaducidad, String titularTarjeta, String emisorTarjeta,
                           Integer cvv, String imgTarjeta, Usuarios usuario) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.titularTarjeta = titularTarjeta;
        this.emisorTarjeta = emisorTarjeta;
        this.cvv = cvv;
        this.imgTarjeta = imgTarjeta;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(Long numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getEmisorTarjeta() {
        return emisorTarjeta;
    }

    public void setEmisorTarjeta(String emisorTarjeta) {
        this.emisorTarjeta = emisorTarjeta;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getImgTarjeta() {
        return imgTarjeta;
    }

    public void setImgTarjeta(String imgTarjeta) {
        this.imgTarjeta = imgTarjeta;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
