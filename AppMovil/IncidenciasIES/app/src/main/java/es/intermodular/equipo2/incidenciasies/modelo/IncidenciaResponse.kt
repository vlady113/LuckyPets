package es.intermodular.equipo2.incidenciasies.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Time
import java.util.Date


data class IncidenciaResponse(

    @SerializedName("num")
    val idIncidencia: Int,
    @SerializedName("adjuntoUrl")
    val adjunto_url: String,
    @SerializedName("descripcion")
    var descripcion: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("fechaCierre")
    val fechaCierre: Date,
    @SerializedName("fechaCreacion")
    var fechaCreacion: Date,
    @SerializedName("tiempo_dec")
    val tiempoDesc: Time,
    @SerializedName("tipo")
    var tipo: String,
    @SerializedName("equipo")
    var equipo: EquipoResponse,
    @SerializedName("incidenciasSubtipo")
    val tipoIncidencia: IncidenciasSubtipoResponse,
    @SerializedName("creador")
    val creador: PersonalResponse,
    @SerializedName("responsable")
    val responsable: PersonalResponse


) : Serializable

