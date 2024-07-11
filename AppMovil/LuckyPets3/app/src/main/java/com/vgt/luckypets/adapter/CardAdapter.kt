package com.vgt.luckypets.adapter

import android.content.Context
import android.content.SharedPreferences
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CardAdapter(
    private val context: Context,
    private val tarjetas: MutableList<TarjetaBancaria>,
    private val cardLogos: Map<String, String>,
    private val onDeleteClick: (TarjetaBancaria) -> Unit,
    private val dragStartListener: OnStartDragListener
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CardPositions", Context.MODE_PRIVATE)

    init {
        // Cargar posiciones guardadas al iniciar el adaptador
        loadPositions()
    }

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

    fun addItem(tarjeta: TarjetaBancaria) {
        tarjetas.add(tarjeta)
        notifyItemInserted(tarjetas.size - 1)
        savePositions()
    }

    fun removeItem(tarjeta: TarjetaBancaria) {
        val position = tarjetas.indexOf(tarjeta)
        if (position != -1) {
            tarjetas.removeAt(position)
            notifyItemRemoved(position)
            savePositions()
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val tarjeta = tarjetas.removeAt(fromPosition)
        tarjetas.add(toPosition, tarjeta)
        notifyItemMoved(fromPosition, toPosition)
        savePositions()
    }

    private fun savePositions() {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(tarjetas)
        editor.putString("positions", json)
        editor.apply()
    }

    private fun loadPositions() {
        val gson = Gson()
        val json = sharedPreferences.getString("positions", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<TarjetaBancaria>>() {}.type
            val savedTarjetas: MutableList<TarjetaBancaria> = gson.fromJson(json, type)
            tarjetas.clear()
            tarjetas.addAll(savedTarjetas)
            notifyDataSetChanged()
        }
    }
}
