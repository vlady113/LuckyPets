package com.vgt.luckypets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vgt.luckypets.R
import com.vgt.luckypets.model.TarjetaBancaria

class CardAdapter(
    private var tarjetas: MutableList<TarjetaBancaria>,
    private val cardLogos: Map<String, String>,
    private val deleteAction: (TarjetaBancaria) -> Unit,
    private val startDragListener: (RecyclerView.ViewHolder) -> Unit
) : RecyclerView.Adapter<CardAdapter.TarjetaViewHolder>(), ItemTouchHelperAdapter {

    class TarjetaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgEmisorTarjeta: ImageView = view.findViewById(R.id.imgEmisorTarjeta)
        val txtPropietarioTarjeta: TextView = view.findViewById(R.id.txtPropietarioTarjeta)
        val txtNumCard: TextView = view.findViewById(R.id.txtNumCard)
        val btnEliminarIncidencia: ImageView = view.findViewById(R.id.btnEliminarIncidencia)
        val btnMoverTarjeta: ImageView = view.findViewById(R.id.btnMoverTarjeta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarjetaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return TarjetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarjetaViewHolder, position: Int) {
        val tarjeta = tarjetas[position]
        holder.txtPropietarioTarjeta.text = tarjeta.titularTarjeta
        holder.txtNumCard.text = "**** **** **** ${tarjeta.numeroTarjeta.toString().takeLast(4)}"

        val logoUrl = cardLogos[tarjeta.emisorTarjeta] ?: cardLogos["Others"]
        Glide.with(holder.imgEmisorTarjeta.context)
            .load(logoUrl)
            .into(holder.imgEmisorTarjeta)

        holder.btnEliminarIncidencia.setOnClickListener {
            deleteAction(tarjeta)
        }

        holder.btnMoverTarjeta.setOnTouchListener { _, _ ->
            startDragListener(holder)
            false
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

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        val fromTarjeta = tarjetas.removeAt(fromPosition)
        tarjetas.add(toPosition, fromTarjeta)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        tarjetas.removeAt(position)
        notifyItemRemoved(position)
    }
}
