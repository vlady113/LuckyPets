package com.vgt.luckypets.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
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

        val layoutEliminarAnuncio = findViewById<LinearLayout>(R.id.layoutEliminarAnuncio)
        if (currentUserEmail == postOwnerEmail) {
            layoutEliminarAnuncio.visibility = View.VISIBLE
            layoutEliminarAnuncio.setOnClickListener { showConfirmationDialog() }
        } else {
            layoutEliminarAnuncio.visibility = View.GONE
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