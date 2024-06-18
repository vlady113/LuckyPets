package es.intermodular.equipo2.incidenciasies.modelo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PerfilReponse(

    @SerializedName("personal_id")
    val personal_id: Int,

    @SerializedName("dominio")
    val dominio: String,

    @SerializedName("educantabria")
    val educantabria: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("perfil")
    val perfil: String

) : Serializable