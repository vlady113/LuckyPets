package com.vladyslav.luckypets.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="mascotas")
public class Mascotas implements Serializable {

    @Id
    @Column(name = "MascotaID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mascotaID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private Usuarios usuario;

    @Column(name = "Nombre", nullable = false, length = 35)
    private String nombre;

    @Column(name = "Tipo", nullable = false, length = 35)
    private String tipo;

    @Column(name = "Raza", length = 35)
    private String raza;

    @Column(name = "Edad")
    private Integer edad;

    @Column(name = "requisitos_especiales")
    private String requisitosEspeciales;

    public Mascotas() {}

    public Mascotas(Usuarios usuario, String nombre, String tipo, String raza, Integer edad, String requisitosEspeciales) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.edad = edad;
        this.requisitosEspeciales = requisitosEspeciales;
    }

    // Getters y setters
    public Long getMascotaID() {
        return mascotaID;
    }

    public void setMascotaID(Long mascotaID) {
        this.mascotaID = mascotaID;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getRequisitosEspeciales() {
        return requisitosEspeciales;
    }

    public void setRequisitosEspeciales(String requisitosEspeciales) {
        this.requisitosEspeciales = requisitosEspeciales;
    }
}
