package com.vgt.luckypets.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vgt.luckypets.R
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class PostAdapter(private val context: Context, private val postsList: List<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val usersCache = mutableMapOf<Long, Users?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList[position]

        Log.d("PostAdapter", "Binding post: $post")

        if (post.userID == 0L) {
            Log.e("PostAdapter", "Invalid userID for post: ${post.anuncioID}")
            holder.txtProvincia.text = "Provincia: Desconocida"
        } else {
            if (usersCache.containsKey(post.userID)) {
                val user = usersCache[post.userID]
                Log.d("PostAdapter", "User found in cache: $user")
                holder.txtProvincia.text = "Provincia: ${user?.provincia ?: "Desconocida"}"
            } else {
                Log.d("PostAdapter", "Fetching user data for userID: ${post.userID}")
                RetrofitBuilder.api.getUserById(post.userID).enqueue(object : Callback<Users> {
                    override fun onResponse(call: Call<Users>, response: Response<Users>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            usersCache[post.userID] = user
                            Log.d("PostAdapter", "User data fetched: $user")
                            holder.txtProvincia.text = "Provincia: ${user?.provincia ?: "Desconocida"}"
                        } else {
                            Log.e("PostAdapter", "Error fetching user data: ${response.code()} - ${response.message()}")
                            holder.txtProvincia.text = "Provincia: Desconocida"
                        }
                    }

                    override fun onFailure(call: Call<Users>, t: Throwable) {
                        Log.e("PostAdapter", "Failed to fetch user data: ${t.localizedMessage}", t)
                        holder.txtProvincia.text = "Provincia: Desconocida"
                    }
                })
            }
        }

        holder.txtDuracion.text = "Duración: ${calculateDuration(post.fechaInicio, post.fechaFin)}"
        holder.txtDescripcion.text = "Descripción: ${post.descripcion}"

        val fotoUrl = post.fotoAnuncio?.let { java.lang.String(it) } ?: ""
        Glide.with(context)
            .load(fotoUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imgPost)
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

