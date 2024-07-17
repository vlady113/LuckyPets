package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.PostAdapter
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MyPostActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var postAdapter: PostAdapter
    private lateinit var postsList: MutableList<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_post)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_my_posts)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email") ?: ""
        if (email.isEmpty()) {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Configurar RecyclerView
        postsList = mutableListOf()
        postAdapter = PostAdapter(this, postsList) { post ->
            showPostDetails(post)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.rvMyPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = postAdapter

        // Configurar ImageView para actualizar datos
        val actualizarDatos = findViewById<View>(R.id.actualizarDatos)
        actualizarDatos.setOnClickListener {
            refreshPosts()
        }

        // Cargar los posts del usuario
        fetchUserPosts()
    }

    private fun fetchUserPosts() {
        RetrofitBuilder.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        val userPosts = posts.filter { it.usuario.email == email }
                        postsList.clear()
                        postsList.addAll(userPosts)
                        postAdapter.updateData(userPosts)
                        Log.d("MyPostActivity", "Posts del usuario cargados correctamente: $userPosts")
                    } else {
                        Log.d("MyPostActivity", "No se encontraron anuncios del usuario.")
                    }
                } else {
                    Toast.makeText(this@MyPostActivity, "Error al obtener los anuncios: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("MyPostActivity", "Error al obtener los anuncios: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MyPostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.e("MyPostActivity", "Error de red: ${t.localizedMessage}", t)
            }
        })
    }

    private fun refreshPosts() {
        fetchUserPosts()
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
    }

    private fun showPostDetails(post: Post) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("post_id", post.anuncioID)
            putExtra("post_provincia", post.usuario.provincia)
            putExtra("post_duracion", calculateDuration(post.fechaInicio, post.fechaFin))
            putExtra("post_descripcion", post.descripcion)
            putExtra("post_foto", post.fotoAnuncio)
            putExtra("post_owner_email", post.usuario.email)
        }
        startActivity(intent)
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
            Log.e("MyPostActivity", "Error parsing dates: $startDate, $endDate", e)
            "Duración desconocida"
        }
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
