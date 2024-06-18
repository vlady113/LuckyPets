package es.intermodular.equipo2.incidenciasies.modelo

import java.time.LocalDateTime

class CrearIncidencia {

    constructor() {
        // Inicializamos los atributos aquí


    }

    var tipo: String = ""
        get() = field
        set(value) {
            field = value
        }

    var subtipo_id: Int = 0
        get() = field
        set(value) {
            field = value
        }

    var fechaCreacion: String=""

    var descripcion: String = ""
        get() = field
        set(value) {
            field = value
        }

    var equipoId: Int? = null
        get() = field
        set(value) {
            field = value
        }

    var estado: String = "abierta" //Por defecto


    var creador_id: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "CrearIncidencia(tipo='$tipo', subtipo_id=$subtipo_id, fechaCreacion=$fechaCreacion, descripcion='$descripcion', equipoId=$equipoId, estado='$estado', creador_id=$creador_id)"
    }


}

//ENUM('abierta', 'asignada', 'en_proceso', 'enviada_a_Infortec', 'resuelta', 'cerrada')