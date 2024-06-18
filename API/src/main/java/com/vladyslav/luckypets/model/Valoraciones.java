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
	@JoinColumn(name = "ReservaID", nullable = false)
	private Anuncios reserva;

	@Column(name = "Valoracion", nullable = false)
	private Integer valoracion;

	@Column(name = "Comentario", length = 1024) // Limite caracteres
	private String comentario;

	// Constructor vacío
	public Valoraciones() {
	}

	// Constructor con parámetros
	public Valoraciones(Anuncios reserva, Integer valoracion, String comentario) {
		this.reserva = reserva;
		this.valoracion = valoracion;
		this.comentario = comentario;
	}

	// Getters y setters
	public Long getValoracionID() {
		return valoracionID;
	}

	public void setValoracionID(Long valoracionID) {
		this.valoracionID = valoracionID;
	}

	public Anuncios getReserva() {
		return reserva;
	}

	public void setReserva(Anuncios reserva) {
		this.reserva = reserva;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
