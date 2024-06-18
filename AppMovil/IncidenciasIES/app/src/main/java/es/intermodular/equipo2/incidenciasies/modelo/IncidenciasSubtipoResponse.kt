package es.intermodular.equipo2.incidenciasies.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IncidenciasSubtipoResponse(

    @SerializedName("subSubtipo")
    val subSubtipo: String,

    @SerializedName("subtipoNombre")
    val subtipoNombre: String,

    @SerializedName("tipo")
    val tipo: String,

    @SerializedName("id")
    val id: Int

) : Serializable