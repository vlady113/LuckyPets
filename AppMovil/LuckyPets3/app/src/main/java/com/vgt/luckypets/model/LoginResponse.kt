package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("UserID")
    val userId: Long,
    @SerializedName("DNI")
    val dni: String,
    @SerializedName("Nombre")
    val nombre: String,
    @SerializedName("Apellidos")
    val apellido: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Direccion")
    val direccion: String,
    @SerializedName("Provincia")
    val provincia: String,
    @SerializedName("codigo_postal")
    val codigo_postal: String,
    @SerializedName("Telefono")
    val telefono: String,
    @SerializedName("fecha_registro")
    val fecha_registro: String,
    @SerializedName("SaldoCR")
    val saldoCr: Double,
    @SerializedName("codigo_restablecimiento")
    val codigo_restablecimiento: String?,
    @SerializedName("codigo_expiry")
    val codigo_expiry: String?,
    @SerializedName("es_administrador")
    val es_administrador: Boolean

)