package es.intermodular.equipo2.incidenciasies.modelo

import androidx.resourceinspection.annotation.Attribute.IntMap
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DepartamentoResponse(
    @SerializedName("int_")
    val id: Int,
    @SerializedName("activo")
    val activo: Boolean,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("nombre")
    val nombe: String,
    @SerializedName("personal")
    val personal: Int
) : Serializable