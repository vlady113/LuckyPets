package com.vgt.luckypets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vgt.luckypets.OnStartDragListener
import com.vgt.luckypets.R
import com.vgt.luckypets.model.TarjetaBancaria

class CardAdapter(
    private val tarjetas: MutableList<TarjetaBancaria>,
    private val cardLogos: Map<String, String>,
    private val onDeleteClick: (TarjetaBancaria) -> Unit,
    private val dragStartListener: OnStartDragListener // Añadir este parámetro
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPropietarioTarjeta: TextView = itemView.findViewById(R.id.txtPropietarioTarjeta)
        val txtNumCard: TextView = itemView.findViewById(R.id.txtNumCard)
        val imgEmisorTarjeta: ImageView = itemView.findViewById(R.id.imgEmisorTarjeta)
        val btnEliminarIncidencia: ImageView = itemView.findViewById(R.id.btnEliminarIncidencia)
        val btnMoverTarjeta: ImageView = itemView.findViewById(R.id.btnMoverTarjeta)

        init {
            btnMoverTarjeta.setOnTouchListener { _, _ ->
                dragStartListener.onStartDrag(this)
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val tarjeta = tarjetas[position]
        holder.txtPropietarioTarjeta.text = tarjeta.titularTarjeta
        holder.txtNumCard.text = "**** **** **** ${tarjeta.numeroTarjeta.toString().takeLast(4)}"

        val logoUrl = cardLogos[tarjeta.emisorTarjeta] ?: ""
        Glide.with(holder.itemView.context)
            .load(logoUrl)
            .into(holder.imgEmisorTarjeta)

        holder.btnEliminarIncidencia.setOnClickListener {
            onDeleteClick(tarjeta)
        }
    }

    override fun getItemCount(): Int {
        return tarjetas.size
    }

    fun removeItem(tarjeta: TarjetaBancaria) {
        val position = tarjetas.indexOf(tarjeta)
        if (position != -1) {
            tarjetas.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val tarjeta = tarjetas.removeAt(fromPosition)
        tarjetas.add(toPosition, tarjeta)
        notifyItemMoved(fromPosition, toPosition)
    }
}
