package com.vgt.luckypets.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.R
import com.vgt.luckypets.activity.PostActivity
import com.vgt.luckypets.model.Post
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class PostAdapter(
    private val context: Context,
    private var postsList: List<Post>,
    private val clickListener: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var originalPostsList: List<Post> = postsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList[position]

        Log.d("PostAdapter", "Binding post: ${post.anuncioID}")

        holder.txtProvincia.text = post.usuario.provincia
        holder.txtDuracion.text = calculateDuration(post.fechaInicio, post.fechaFin)
        holder.txtCoste.text = "${post.costoCR} CR"

        val fotoBase64 = post.fotoAnuncio
        if (!fotoBase64.isNullOrEmpty()) {
            val imageBytes = Base64.decode(fotoBase64, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(imageBytes)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            holder.imgPost.setImageBitmap(bitmap)
        } else {
            holder.imgPost.setImageResource(R.drawable.placeholder_image)
        }

        if (post.estado == Post.EstadoAnuncio.EN_CURSO) {
            holder.notificationIcon.visibility = View.VISIBLE
        } else {
            holder.notificationIcon.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java).apply {
                putExtra("post_id", post.anuncioID)
                putExtra("post_owner_email", post.usuario.email)
                putExtra("post_provincia", post.usuario.provincia)
                putExtra("post_duracion", calculateDuration(post.fechaInicio, post.fechaFin))
                putExtra("post_descripcion", post.descripcion)
                putExtra("post_foto", post.fotoAnuncio)
                putExtra("post_coste", post.costoCR)
            }
            Log.d("PostAdapter", "Enviando postId: ${post.anuncioID}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPost: ImageView = itemView.findViewById(R.id.imgPost)
        val notificationIcon: ImageView = itemView.findViewById(R.id.notificationIcon)
        val txtProvincia: TextView = itemView.findViewById(R.id.txtProvincia)
        val txtDuracion: TextView = itemView.findViewById(R.id.txtDuracion)
        val txtCoste: TextView = itemView.findViewById(R.id.txtCoste)
    }

    fun calculateDuration(startDate: String, endDate: String): String {
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

    fun filter(query: String) {
        postsList = if (query.isEmpty()) {
            originalPostsList
        } else {
            originalPostsList.filter { post ->
                post.usuario.provincia.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
            }
        }
        notifyDataSetChanged()
    }

    fun updateData(newPostsList: List<Post>) {
        originalPostsList = newPostsList
        postsList = newPostsList
        notifyDataSetChanged()
    }
}