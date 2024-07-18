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
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class PostActivity : AppCompatActivity() {

    private lateinit var currentUserEmail: String
    private lateinit var postOwnerEmail: String
    private var postId: Long = 0L
    private var lastReservedTime: Long = 0L

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

        val layoutEliminarAnuncio = findViewById<LinearLayout>(R.id.layoutEliminarAnuncio)
        val botoneraUsuario = findViewById<LinearLayout>(R.id.botoneraUsuario)
        val btnConfirmarTransaccion = findViewById<Button>(R.id.btnConfirmarTransaccion)
        val btnReservar = findViewById<Button>(R.id.btnReservar)
        val btnCancelarReserva = findViewById<Button>(R.id.btnCancelarReserva)

        if (currentUserEmail == postOwnerEmail) {
            layoutEliminarAnuncio.visibility = View.VISIBLE
            layoutEliminarAnuncio.setOnClickListener { showConfirmationDialog() }
            botoneraUsuario.visibility = View.GONE
            btnConfirmarTransaccion.visibility = View.VISIBLE
        } else {
            layoutEliminarAnuncio.visibility = View.GONE
            botoneraUsuario.visibility = View.VISIBLE
            btnConfirmarTransaccion.visibility = View.GONE
        }

        btnReservar.setOnClickListener {
            showReserveConfirmationDialog()
        }

        btnCancelarReserva.setOnClickListener {
            showCancelConfirmationDialog()
        }

        val provincia = intent.getStringExtra("post_provincia")
        val duracion = intent.getStringExtra("post_duracion")
        val descripcion = intent.getStringExtra("post_descripcion")
        val fotoBase64 = intent.getStringExtra("post_foto")
        val coste = intent.getDoubleExtra("post_coste", 0.0)

        findViewById<TextView>(R.id.txtProvincia).text = provincia
        findViewById<TextView>(R.id.txtDuracion).text = duracion
        findViewById<TextView>(R.id.txtTelefonoPost).text = getPostOwnerPhone()
        findViewById<TextView>(R.id.txtDescripcion).text = descripcion
        findViewById<TextView>(R.id.txtCoste).text = "$coste CR"

        val imgPost = findViewById<ImageView>(R.id.imgPost)

        if (fotoBase64 != null) {
            val imageBytes = Base64.decode(fotoBase64, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(imageBytes)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgPost.setImageBitmap(bitmap)
        }
    }

    private fun getCurrentUserEmail(): String {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    private fun getPostOwnerPhone(): String {
        val post = intent.getSerializableExtra("post") as? Post
        return post?.usuario?.telefono ?: "No disponible"
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
            val estadoMap = mapOf("estado" to "en_curso")
            RetrofitBuilder.api.updateAnuncioStatus(postId, estadoMap).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PostActivity, "Servicio reservado con éxito", Toast.LENGTH_SHORT).show()
                        // Ocultar botón de reservar y mostrar botón de cancelar reserva
                        findViewById<Button>(R.id.btnReservar).visibility = View.GONE
                        findViewById<Button>(R.id.btnCancelarReserva).visibility = View.VISIBLE
                        lastReservedTime = System.currentTimeMillis()
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
        // Verificar si han pasado al menos 15 minutos desde la última reserva
        val currentTime = System.currentTimeMillis()
        val elapsedMinutes = (currentTime - lastReservedTime) / (1000 * 60)

        if (elapsedMinutes < 15) {
            Toast.makeText(this, "Debe esperar 15 minutos antes de volver a reservar.", Toast.LENGTH_SHORT).show()
            return
        }

        val estadoMap = mapOf("estado" to "pendiente")
        RetrofitBuilder.api.updateAnuncioStatus(postId, estadoMap).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PostActivity, "Reserva cancelada con éxito", Toast.LENGTH_SHORT).show()
                    // Ocultar botón de cancelar reserva y mostrar botón de reservar
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

    fun volverAtras(view: View?) {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    fun Llamar(view: View) {
        val telefonoTextView = findViewById<TextView>(R.id.txtTelefonoPost)
        var telefono = telefonoTextView.text.toString()

        // Formatear el número de teléfono para eliminar cualquier espacio, paréntesis, guiones y otros caracteres
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
}