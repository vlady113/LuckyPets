package com.vgt.luckypets.model

import java.io.Serializable
import java.time.LocalDate

data class Transaction(
    val transaccionID: Long,
    val usuario: String,
    val fecha: LocalDate,
    val montoCR: Double,
    val tipo: TipoTransaccion,
    val reserva: String?,
    val usuarioCliente: String?
) : Serializable {
    enum class TipoTransaccion {
        adicion,
        sustraccion
    }
}
