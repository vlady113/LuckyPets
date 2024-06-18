package es.intermodular.equipo2.incidenciasies.recyclerComentarios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.intermodular.equipo2.incidenciasies.R
import es.intermodular.equipo2.incidenciasies.databinding.ItemComentarioBinding
import es.intermodular.equipo2.incidenciasies.modelo.ComentarioResponse

class ComentarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //Le pasamos la vista del comentario
    private val binding = ItemComentarioBinding.bind(view)

    fun bind(
        comentarioResponse: ComentarioResponse
    ) {
        //Obtenemos el usuario (puede ser el propio profesor u otro)
        val user = "${comentarioResponse.personal.nombre} ${comentarioResponse.personal.apellido1}"
        binding.txtUsuario.text = user

        binding.txtFechaComentario.text = comentarioResponse.fechahora.toString()

        binding.txtComentario.text = comentarioResponse.texto
    }
}