package es.intermodular.equipo2.incidenciasies.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AulaResponse(

    @SerializedName("num")
    val num: Int,

    @SerializedName("codigo")
    val codigo: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("planta")
    val planta: Int

) : Serializable