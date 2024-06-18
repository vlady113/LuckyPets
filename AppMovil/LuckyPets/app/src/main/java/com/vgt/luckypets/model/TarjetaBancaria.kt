package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TarjetaBancaria(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("numeroTarjeta")
    val numeroTarjeta: Long,
    @SerializedName("fechaCaducidad")
    val fechaCaducidad: String,
    @SerializedName("titularTarjeta")
    val titularTarjeta: String,
    @SerializedName("emisorTarjeta")
    val emisorTarjeta: String,
    @SerializedName("cvv")
    val cvv: Int,
    @SerializedName("imgTarjeta")
    val imgTarjeta: String,
    @SerializedName("usuario")
    val usuario: Users
) : Serializable
