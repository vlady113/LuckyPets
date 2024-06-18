package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Users(
    @SerializedName("UserID")
    val userID: Long,
    @SerializedName("dni")
    val dni: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("apellidos")
    val apellidos: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("direccion")
    val direccion: String,
    @SerializedName("provincia")
    val provincia: String,
    @SerializedName("codigoPostal")
    val codigoPostal: String,
    @SerializedName("telefono")
    val telefono: String,
    @SerializedName("fechaRegistro")
    val fechaRegistro: String,
    @SerializedName("saldoCR")
    var saldoCR: Double,
    @SerializedName("codigo_restablecimiento")
    val codigo_restablecimiento: String?,
    @SerializedName("codigo_expiry")
    val codigo_expiry: String?,
    @SerializedName("es_administrador")
    val es_administrador: Boolean
) : Serializable
