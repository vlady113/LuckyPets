package com.vladyslav.luckypets.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "valoraciones")
public class Valoraciones implements Serializable {

    @Id
    @Column(name = "ValoracionID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valoracionID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private Usuarios usuario;

    @Column(name = "Valoracion", nullable = false)
    private Integer valoracion;

    public Valoraciones() {
    }

    public Valoraciones(Usuarios usuario, Integer valoracion) {
        this.usuario = usuario;
        this.valoracion = valoracion;
    }

    public Long getValoracionID() {
        return valoracionID;
    }

    public void setValoracionID(Long valoracionID) {
        this.valoracionID = valoracionID;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }
}