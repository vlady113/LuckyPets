package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pets(
    @SerializedName("mascotaID")
    val mascotaID: Long,
    @SerializedName("userID")
    val userID: Long,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("tipo")
    val tipo: String,
    @SerializedName("raza")
    val raza: String?,
    @SerializedName("edad")
    val edad: Int?,
    @SerializedName("requisitosEspeciales")
    val requisitosEspeciales: String?
) : Serializable
