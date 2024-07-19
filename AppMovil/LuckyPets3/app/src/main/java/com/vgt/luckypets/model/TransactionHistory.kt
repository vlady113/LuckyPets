package com.vgt.luckypets.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.time.LocalDateTime

data class TransactionHistory(
    @SerializedName("TransaccionID")
    val transaccionID: Long,
    @SerializedName("UserID")
    val userID: Long,
    @SerializedName("Fecha")
    val fecha: LocalDateTime,
    @SerializedName("MontoCR")
    val montoCR: Double,
    @SerializedName("Tipo")
    val tipo: TipoTransaccion,
    @SerializedName("ReservaID")
    val reservaID: Long?,
    @SerializedName("UserIDCliente")
    val userIDCliente: Long?
) : Serializable {

    enum class TipoTransaccion(val value: String) {
        @SerializedName("adicion")
        ADICION("adicion"),
        @SerializedName("sustraccion")
        SUSTRACCION("sustraccion");

        companion object {
            fun fromValue(value: String): TipoTransaccion {
                return values().first { it.value == value }
            }
        }
    }
}