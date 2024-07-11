package com.vladyslav.luckypets.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuarios implements Serializable {

    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(name = "DNI", nullable = true, unique = true, length = 9)
    private String dni;

    @Column(name = "Nombre", nullable = true, length = 155)
    private String nombre;

    @Column(name = "Apellidos", nullable = true, length = 255)
    private String apellidos;

    @Column(name = "Email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Direccion", nullable = true, length = 255)
    private String direccion;

    @Column(name = "Provincia", nullable = true, length = 255)
    private String provincia;

    @Column(name = "codigo_postal", nullable = true, length = 10)
    private String codigoPostal;

    @Column(name = "Telefono", nullable = true, length = 20)
    private String telefono;

    @Column(name = "fecha_registro", nullable = true)
    private LocalDate fechaRegistro;

    @Column(name = "SaldoCR", nullable = true)
    private Double saldoCR;

    @Column(name = "codigo_restablecimiento", nullable = true, length = 9)
    private String codigo_restablecimiento;

    @Column(name = "codigo_expiry", nullable = true)
    private LocalDateTime codigo_expiry;  // Campo para la fecha de expiración

    @Column(name = "es_administrador", nullable = false)
    private boolean esAdministrador;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TarjetaBancaria> tarjetasBancarias;

    public Usuarios() {
    }

    public Usuarios(String dni, String nombre, String apellidos, String email, String password,
                    String direccion, String provincia, String codigoPostal, String telefono, LocalDate fechaRegistro,
                    Double saldoCR, boolean esAdministrador) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.saldoCR = saldoCR;
        this.esAdministrador = esAdministrador;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Double getSaldoCR() {
        return saldoCR;
    }

    public void setSaldoCR(Double saldoCR) {
        this.saldoCR = saldoCR;
    }

    public String getCodigo_restablecimiento() {
        return codigo_restablecimiento;
    }

    public void setCodigo_restablecimiento(String codigo_restablecimiento) {
        this.codigo_restablecimiento = codigo_restablecimiento;
    }

    public LocalDateTime getCodigo_expiry() {
        return codigo_expiry;
    }

    public void setCodigo_expiry(LocalDateTime codigo_expiry) {
        this.codigo_expiry = codigo_expiry;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public List<TarjetaBancaria> getTarjetasBancarias() {
        return tarjetasBancarias;
    }

    public void setTarjetasBancarias(List<TarjetaBancaria> tarjetasBancarias) {
        this.tarjetasBancarias = tarjetasBancarias;
    }
}
