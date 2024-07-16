package com.vgt.luckypets.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.PostAdapter
import com.vgt.luckypets.databinding.ActivityPrincipalBinding
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var email: String
    private var userID: Long = 0L
    private lateinit var balanceEditText: EditText
    private lateinit var postAdapter: PostAdapter
    private lateinit var postsList: MutableList<Post>

    companion object {
        private const val REQUEST_CODE_BALANCE = 1001
        private const val REQUEST_CODE_POST = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.principal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        email = intent.getStringExtra("email") ?: getEmailFromPreferences()
        userID = intent.getLongExtra("userID", 0L)
        if (email.isEmpty()) {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        Log.d("PrincipalActivity", "Correo recibido: $email")

        balanceEditText = findViewById(R.id.EditText_MyBalance_Now)
        balanceEditText.setOnClickListener {
            showMyBalanceActivity()
        }
        fetchUserBalance()

        val menuAjustes: ImageView = findViewById(R.id.menuAjustes)
        menuAjustes.setOnClickListener { view ->
            showPopupMenu(view)
        }

        // Configurar RecyclerView
        postsList = mutableListOf()
        postAdapter = PostAdapter(this, postsList) { post ->
            showPostDetails(post)
        }
        binding.rvTarjetasGuardadas.apply {
            layoutManager = LinearLayoutManager(this@PrincipalActivity)
            adapter = postAdapter
        }

        // Configurar EditText para buscar
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                postAdapter.filter(s.toString())
            }
        })

        // Configurar FloatingActionButton
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            checkUserDataAndProceed()
        }

        // Configurar ImageView para actualizar datos
        val actualizarDatos = findViewById<ImageView>(R.id.actualizarDatos)
        actualizarDatos.setOnClickListener {
            refreshPosts()
        }

        // Cargar los posts
        fetchPosts()
    }

    private fun getEmailFromPreferences(): String {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun fetchUserBalance() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val balance = user?.saldoCR ?: 0.0
                    balanceEditText.setText(String.format("%.2f CR", balance))
                } else {
                    // Manejar el error
                    Toast.makeText(this@PrincipalActivity, "Error al obtener el saldo.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@PrincipalActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchPosts() {
        RetrofitBuilder.api.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null && posts.isNotEmpty()) {
                        postsList.clear()
                        postsList.addAll(posts)
                        postAdapter.updateData(posts)
                        Log.d("PrincipalActivity", "Posts cargados correctamente: $posts")
                    } else {
                        Log.d("PrincipalActivity", "No se encontraron anuncios.")
                    }
                } else {
                    Toast.makeText(this@PrincipalActivity, "Error al obtener los anuncios: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("PrincipalActivity", "Error al obtener los anuncios: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@PrincipalActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.e("PrincipalActivity", "Error de red: ${t.localizedMessage}", t)
            }
        })
    }

    private fun refreshPosts() {
        fetchPosts()
        fetchUserBalance() // Añadir esta línea para refrescar el saldo
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
    }

    private fun showPopupMenu(view: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_menu, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Configurar el contenido del popup
        popupView.findViewById<TextView>(R.id.action_my_data).setOnClickListener {
            showMyDataActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_change_email).setOnClickListener {
            showChangeEmailActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_change_password).setOnClickListener {
            showChangePasswordActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_about).setOnClickListener {
            showAboutActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.logOut).setOnClickListener {
            Toast.makeText(this, "Se ha cerrado sesión", Toast.LENGTH_SHORT).show()
            popupWindow.dismiss()
            logOut()
        }

        // Mostrar el popup
        popupWindow.showAsDropDown(view, -100, 0)
    }

    private fun showMyBalanceActivity() {
        val intent = Intent(this, MyBalanceActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("userID", userID)
        startActivityForResult(intent, REQUEST_CODE_BALANCE)
    }

    private fun showMyDataActivity() {
        val intent = Intent(this, MyDataActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showChangeEmailActivity() {
        val intent = Intent(this, ChangeEmailActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showChangePasswordActivity() {
        val intent = Intent(this, ChangePasswordActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun logOut() {
        clearLoginPreferences()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    private fun clearLoginPreferences() {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("email", "")
            putString("password", "")
            putBoolean("remember_me", false)
            apply()
        }
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
        startActivityForResult(intent, REQUEST_CODE_POST)
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
            Log.e("PrincipalActivity", "Error parsing dates: $startDate, $endDate", e)
            "Duración desconocida"
        }
    }

    private fun checkUserDataAndProceed() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        if (areUserDataComplete(user)) {
                            if (user.saldoCR >= 100) {
                                val intent = Intent(this@PrincipalActivity, NewPostActivity::class.java)
                                intent.putExtra("email", email)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@PrincipalActivity, "Saldo insuficiente para publicar un anuncio.", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@PrincipalActivity, MyBalanceActivity::class.java)
                                intent.putExtra("email", email)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@PrincipalActivity, "Por favor, complete sus datos antes de publicar un anuncio.", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@PrincipalActivity, MyDataActivity::class.java)
                            intent.putExtra("email", email)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@PrincipalActivity, "Error al verificar datos del usuario.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@PrincipalActivity, "Error al verificar datos del usuario.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@PrincipalActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun areUserDataComplete(user: Users): Boolean {
        return user.dni?.isNotEmpty() == true &&
                user.nombre?.isNotEmpty() == true &&
                user.apellidos?.isNotEmpty() == true &&
                user.direccion?.isNotEmpty() == true &&
                user.provincia?.isNotEmpty() == true &&
                user.codigoPostal?.isNotEmpty() == true &&
                user.telefono?.isNotEmpty() == true
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_BALANCE && resultCode == Activity.RESULT_OK) {
            val newBalance = data?.getDoubleExtra("new_balance", -1.0)
            if (newBalance != null && newBalance != -1.0) {
                balanceEditText.setText(String.format("%.2f CR", newBalance))
                findViewById<EditText>(R.id.EditText_MyBalance_Now).setText(String.format("%.2f CR", newBalance))
            }
        } else if (requestCode == REQUEST_CODE_POST && resultCode == Activity.RESULT_OK) {
            val deletedPostId = data?.getLongExtra("deleted_post_id", -1L)
            if (deletedPostId != null && deletedPostId != -1L) {
                postsList.removeAll { it.anuncioID == deletedPostId }
                postAdapter.notifyDataSetChanged()
            }
        }
    }

}