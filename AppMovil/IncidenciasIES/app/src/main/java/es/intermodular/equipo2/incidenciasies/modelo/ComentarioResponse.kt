package es.intermodular.equipo2.incidenciasies.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime
import java.util.Date

data class ComentarioResponse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("adjuntoUrl")
    val adjuntoUrl: String?,

    @SerializedName("texto")
    val texto: String,

    @SerializedName("fechahora")
    val fechahora: String,

    @SerializedName("incidencia")
    val incidencia: IncidenciaResponse,

    @SerializedName("personal")
    val personal: PersonalResponse

) : Serializable {


    override fun toString(): String {
        return "ComentarioResponse(id=$id, adjuntoUrl=$adjuntoUrl, texto='$texto', fechahora='$fechahora', incidencia=$incidencia, personal=$personal)"
    }

}