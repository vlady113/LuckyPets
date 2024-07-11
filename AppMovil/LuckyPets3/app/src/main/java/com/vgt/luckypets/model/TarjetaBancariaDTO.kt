package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDate

data class TarjetaBancariaDTO(
    @SerializedName("numeroTarjeta")
    val numeroTarjeta: Long,
    @SerializedName("fechaCaducidad")
    val fechaCaducidad: String, // Usar String para simplificar la serialización
    @SerializedName("titularTarjeta")
    val titularTarjeta: String,
    @SerializedName("emisorTarjeta")
    val emisorTarjeta: String,
    @SerializedName("cvv")
    val cvv: Int,
    @SerializedName("imgTarjeta")
    val imgTarjeta: String,
    @SerializedName("usuarioEmail")
    val usuarioEmail: String
) : Serializable
