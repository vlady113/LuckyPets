package DTO;

import java.time.LocalDate;

public class TarjetaBancariaDTO {

    private Long numeroTarjeta;
    private LocalDate fechaCaducidad;
    private String titularTarjeta;
    private String emisorTarjeta;
    private Integer cvv;
    private String imgTarjeta;
    private String usuarioEmail;

    public TarjetaBancariaDTO() {
    }

    public TarjetaBancariaDTO(Long numeroTarjeta, LocalDate fechaCaducidad, String titularTarjeta, String emisorTarjeta,
                              Integer cvv, String imgTarjeta, String usuarioEmail) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaCaducidad = fechaCaducidad;
        this.titularTarjeta = titularTarjeta;
        this.emisorTarjeta = emisorTarjeta;
        this.cvv = cvv;
        this.imgTarjeta = imgTarjeta;
        this.usuarioEmail = usuarioEmail;
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

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
}
