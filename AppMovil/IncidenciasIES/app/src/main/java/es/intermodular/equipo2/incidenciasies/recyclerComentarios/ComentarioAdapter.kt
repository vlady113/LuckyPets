package es.intermodular.equipo2.incidenciasies.recyclerComentarios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.intermodular.equipo2.incidenciasies.R
import es.intermodular.equipo2.incidenciasies.modelo.ComentarioResponse

class ComentarioAdapter(
    var listaComentarios: List<ComentarioResponse> = emptyList()
) : RecyclerView.Adapter<ComentarioViewHolder>() {

    fun updateComentarios(list: List<ComentarioResponse>) {
        listaComentarios = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        return ComentarioViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        holder.bind(listaComentarios[position])
    }


    override fun getItemCount() = listaComentarios.size

}