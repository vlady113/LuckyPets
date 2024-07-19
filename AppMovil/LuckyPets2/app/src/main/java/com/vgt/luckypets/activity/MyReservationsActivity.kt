package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
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

class MyReservationsActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var postAdapter: PostAdapter
    private lateinit var rvMyReservations: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_reservations)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_my_reservations)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = getCurrentUserEmail()

        rvMyReservations = findViewById(R.id.rvMyReservations)
        rvMyReservations.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(this, emptyList()) { post ->
            showPostDetails(post)
        }
        rvMyReservations.adapter = postAdapter

        findViewById<View>(R.id.actualizarDatos).setOnClickListener {
            fetchReservations()
        }

        fetchReservations()
    }

    private fun getCurrentUserEmail(): String {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun fetchReservations() {
        RetrofitBuilder.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        val reservedPosts = posts.filter { it.emailCliente == email && it.estado == Post.EstadoAnuncio.EN_CURSO }
                        postAdapter.updateData(reservedPosts)
                    } else {
                        Toast.makeText(this@MyReservationsActivity, "No se encontraron reservas.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MyReservationsActivity, "Error al obtener las reservas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MyReservationsActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showPostDetails(post: Post) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("post_id", post.anuncioID)
            putExtra("post_provincia", post.usuario.provincia)
            putExtra("post_duracion", postAdapter.calculateDuration(post.fechaInicio, post.fechaFin))
            putExtra("post_descripcion", post.descripcion)
            putExtra("post_foto", post.fotoAnuncio)
            putExtra("post_coste", post.costoCR)
            putExtra("post_owner_email", post.usuario.email)
        }
        startActivity(intent)
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}