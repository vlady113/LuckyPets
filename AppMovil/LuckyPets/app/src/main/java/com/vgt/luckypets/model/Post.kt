package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post(
    @SerializedName("anuncioID")
    val anuncioID: Long,
    @SerializedName("usuario")
    val usuario: Users,
    @SerializedName("fechaInicio")
    val fechaInicio: String,
    @SerializedName("fechaFin")
    val fechaFin: String,
    @SerializedName("descripcion")
    val descripcion: String?,
    @SerializedName("estado")
    val estado: EstadoAnuncio,
    @SerializedName("costoCR")
    val costoCR: Double,
    @SerializedName("fotoAnuncio")
    val fotoAnuncio: String?
) : Serializable {
    enum class EstadoAnuncio(val value: String) {
        @SerializedName("pendiente")
        PENDIENTE("pendiente"),
        @SerializedName("en_curso")
        EN_CURSO("en_curso"),
        @SerializedName("completado")
        COMPLETADO("completado");

        companion object {
            fun fromValue(value: String): EstadoAnuncio {
                return values().first { it.value.equals(value, ignoreCase = true) || it.name.equals(value.replace(" ", "_"), ignoreCase = true) }
            }
        }
    }
}
