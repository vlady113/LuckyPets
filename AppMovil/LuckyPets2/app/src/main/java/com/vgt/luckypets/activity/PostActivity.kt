package com.vgt.luckypets.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import com.vgt.luckypets.model.Post
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class PostActivity : AppCompatActivity() {

    private lateinit var currentUserEmail: String
    private lateinit var postOwnerEmail: String
    private var postId: Long = 0L
    private var lastReservedTime: Long = 0L
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_post)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentUserEmail = getCurrentUserEmail()
        postOwnerEmail = intent.getStringExtra("post_owner_email") ?: ""
        postId = intent.getLongExtra("post_id", 0L)

        Log.d("PostActivity", "postId recibido: $postId")
        Log.d("PostActivity", "postOwnerEmail recibido: $postOwnerEmail")

        if (postId == 0L) {
            Toast.makeText(this, "ID de anuncio no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fetchPostDetails()
    }

    private fun fetchPostDetails() {
        RetrofitBuilder.api.getPostById(postId).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    post = response.body()!!
                    setupUI()
                } else {
                    Toast.makeText(this@PostActivity, "Error al obtener detalles del anuncio.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@PostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupUI() {
        val layoutEliminarAnuncio = findViewById<LinearLayout>(R.id.layoutEliminarAnuncio)
        val botoneraUsuario = findViewById<LinearLayout>(R.id.botoneraUsuario)
        val btnConfirmarTransaccion = findViewById<Button>(R.id.btnConfirmarTransaccion)
        val btnPendienteReserva = findViewById<Button>(R.id.btnPendienteReserva)
        val btnReservar = findViewById<Button>(R.id.btnReservar)
        val btnCancelarReserva = findViewById<Button>(R.id.btnCancelarReserva)
        val btnCompletado = findViewById<Button>(R.id.btnCompletado)

        if (currentUserEmail == postOwnerEmail) {
            layoutEliminarAnuncio.visibility = View.VISIBLE
            layoutEliminarAnuncio.setOnClickListener { showConfirmationDialog() }
            botoneraUsuario.visibility = View.GONE

            if (post.emailCliente.isNullOrEmpty()) {
                btnConfirmarTransaccion.visibility = View.GONE
                btnPendienteReserva.visibility = View.VISIBLE
            } else {
                btnConfirmarTransaccion.visibility = View.VISIBLE
                btnPendienteReserva.visibility = View.GONE
            }
        } else {
            layoutEliminarAnuncio.visibility = View.GONE
            botoneraUsuario.visibility = View.VISIBLE
            btnConfirmarTransaccion.visibility = View.GONE
            btnPendienteReserva.visibility = View.GONE
        }

        findViewById<TextView>(R.id.txtProvincia).text = post.usuario.provincia
        findViewById<TextView>(R.id.txtDuracion).text = calculateDuration(post.fechaInicio, post.fechaFin)
        findViewById<TextView>(R.id.txtTelefonoPost).text = post.usuario.telefono
        findViewById<TextView>(R.id.txtDescripcion).text = post.descripcion
        findViewById<TextView>(R.id.txtCoste).text = "${post.costoCR} CR"

        val imgPost = findViewById<ImageView>(R.id.imgPost)

        post.fotoAnuncio?.let {
            val imageBytes = Base64.decode(it, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(imageBytes)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgPost.setImageBitmap(bitmap)
        } ?: run {
            imgPost.setImageResource(R.drawable.placeholder_image)
        }

        btnReservar.setOnClickListener {
            showReserveConfirmationDialog()
        }

        btnCancelarReserva.setOnClickListener {
            showCancelConfirmationDialog()
        }

        btnConfirmarTransaccion.setOnClickListener {
            showConfirmTransactionDialog()
        }
    }

    private fun getCurrentUserEmail(): String {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Está seguro de que desea eliminar este anuncio?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            eliminarAnuncio()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showReserveConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar reserva")
        builder.setMessage("¿Está seguro de que desea reservar este servicio?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            reservarAnuncio()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showCancelConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar cancelación")
        builder.setMessage("¿Está seguro de que desea cancelar esta reserva?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            cancelarReserva()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showConfirmTransactionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar transacción")
        builder.setMessage("¿Está seguro de que desea confirmar esta transacción?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            confirmarTransaccion()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun eliminarAnuncio() {
        RetrofitBuilder.api.deleteAnuncio(postId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PostActivity, "Anuncio eliminado correctamente", Toast.LENGTH_SHORT).show()
                    val resultIntent = Intent()
                    resultIntent.putExtra("deleted_post_id", postId)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                } else {
                    Toast.makeText(this@PostActivity, "Error al eliminar el anuncio", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@PostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun reservarAnuncio() {
        if (postId > 0) {
            val estadoMap = mapOf("estado" to "en_curso", "emailCliente" to currentUserEmail)
            RetrofitBuilder.api.updateAnuncioStatus(postId, estadoMap).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PostActivity, "Servicio reservado con éxito", Toast.LENGTH_SHORT).show()
                        findViewById<Button>(R.id.btnReservar).visibility = View.GONE
                        findViewById<Button>(R.id.btnCancelarReserva).visibility = View.VISIBLE
                    } else {
                        Toast.makeText(this@PostActivity, "Error al reservar el servicio", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Toast.makeText(this@PostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@PostActivity, "ID de anuncio no válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelarReserva() {
        val currentTime = System.currentTimeMillis()
        val elapsedMinutes = (currentTime - lastReservedTime) / (1000 * 60)

        if (elapsedMinutes < 15) {
            Toast.makeText(this, "Debe esperar 15 minutos antes de volver a reservar.", Toast.LENGTH_SHORT).show()
            return
        }

        val estadoMap = mapOf("estado" to "pendiente", "emailCliente" to "")
        RetrofitBuilder.api.updateAnuncioStatus(postId, estadoMap).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PostActivity, "Reserva cancelada con éxito", Toast.LENGTH_SHORT).show()
                    findViewById<Button>(R.id.btnCancelarReserva).visibility = View.GONE
                    findViewById<Button>(R.id.btnReservar).visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@PostActivity, "Error al cancelar la reserva", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(this@PostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmarTransaccion() {
        if (postId > 0) {
            RetrofitBuilder.api.confirmarTransaccion(postId, post.emailCliente ?: "").enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Calcula la comisión y el crédito final
                        val postCoste = post.costoCR
                        val comision = 15.0
                        val creditoFinal = postCoste - comision

                        // Actualiza los créditos del cliente y del propietario
                        actualizarCreditos(post.emailCliente ?: "", creditoFinal)
                        actualizarCreditos(postOwnerEmail, -postCoste)

                        Toast.makeText(this@PostActivity, "Transacción confirmada con éxito", Toast.LENGTH_SHORT).show()
                        findViewById<Button>(R.id.btnConfirmarTransaccion).visibility = View.GONE
                        findViewById<Button>(R.id.btnCompletado).visibility = View.VISIBLE
                    } else {
                        Toast.makeText(this@PostActivity, "Error al confirmar la transacción", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@PostActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@PostActivity, "ID de anuncio no válido", Toast.LENGTH_SHORT).show()
        }
    }


    private fun actualizarCreditos(email: String, cantidad: Double) {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val usuario = response.body()!!
                    val nuevosCreditos = usuario.saldoCR + cantidad
                    usuario.saldoCR = nuevosCreditos

                    RetrofitBuilder.api.updateUsuario(email, usuario).enqueue(object : Callback<Users> {
                        override fun onResponse(call: Call<Users>, response: Response<Users>) {
                            if (!response.isSuccessful) {
                                Toast.makeText(this@PostActivity, "Error al actualizar los créditos", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Users>, t: Throwable) {
                            Toast.makeText(this@PostActivity, "Error de red al actualizar los créditos: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@PostActivity, "Error al obtener el usuario", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@PostActivity, "Error de red al obtener el usuario: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun volverAtras(view: View?) {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    fun Llamar(view: View) {
        val telefonoTextView = findViewById<TextView>(R.id.txtTelefonoPost)
        var telefono = telefonoTextView.text.toString()

        telefono = telefono.replace("[^\\d+]".toRegex(), "")

        if (telefono != "No disponible" && telefono.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$telefono")
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Número de teléfono no disponible", Toast.LENGTH_SHORT).show()
        }
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
            Log.e("PostActivity", "Error parsing dates: $startDate, $endDate", e)
            "Duración desconocida"
        }
    }
}