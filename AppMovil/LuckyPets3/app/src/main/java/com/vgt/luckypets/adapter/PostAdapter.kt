package com.vgt.luckypets.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vgt.luckypets.R
import com.vgt.luckypets.activity.PrincipalActivity
import com.vgt.luckypets.model.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class PostAdapter(
    private val context: Context,
    private val postsList: List<Post>,
    private val clickListener: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList[position]

        Log.d("PostAdapter", "Binding post: $post")

        holder.txtProvincia.text = post.usuario.provincia
        holder.txtDuracion.text = calculateDuration(post.fechaInicio, post.fechaFin)
        holder.txtDescripcion.text = post.descripcion

        val fotoUrl = post.fotoAnuncio ?: ""
        Glide.with(context)
            .load(fotoUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imgPost)

        holder.itemView.setOnClickListener {
            clickListener(post)
        }
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPost: ImageView = itemView.findViewById(R.id.imgPost)
        val txtProvincia: TextView = itemView.findViewById(R.id.txtProvincia)
        val txtDuracion: TextView = itemView.findViewById(R.id.txtDuracion)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
    }

    private fun calculateDuration(startDate: String, endDate: String): String {
        return try {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val start = LocalDateTime.parse(startDate, formatter)
            val end = LocalDateTime.parse(endDate, formatter)
            val days = ChronoUnit.DAYS.between(start, end)
            if (days == 0L) {
                val hours = ChronoUnit.HOURS.between(start, end)
                if (hours == 1L) "$hours hora" else "$hours horas"
            } else {
                if (days == 1L) "$days día" else "$days días"
            }
        } catch (e: Exception) {
            Log.e("PostAdapter", "Error parsing dates: $startDate, $endDate", e)
            "Duración desconocida"
        }
    }

}
