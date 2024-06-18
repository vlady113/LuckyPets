package es.intermodular.equipo2.incidenciasies.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Constructor
import java.sql.Date

data class EquipoResponse(


    @SerializedName("id")
    val id: Int,
    @SerializedName("baja")
    val baja: Int,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("etiqueta")
    val etiqueta: String,
    @SerializedName("fechaAdquisicion")
    val fechaAdquisicion: String,
    @SerializedName("marca")
    val marca: String,
    @SerializedName("modelo")
    val modelo: String,
    @SerializedName("puesto")
    val puesto: Int,
    @SerializedName("tipoEquipo")
    val tipoEquipo: String,
    @SerializedName("aula")
    val aula: AulaResponse
) : Serializable