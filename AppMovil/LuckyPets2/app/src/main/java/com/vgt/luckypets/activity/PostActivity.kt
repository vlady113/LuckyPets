package com.vgt.luckypets.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import java.io.ByteArrayInputStream

class PostActivity : AppCompatActivity() {

    private lateinit var currentUserEmail: String
    private lateinit var postOwnerEmail: String

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

        val layoutEliminarAnuncio = findViewById<LinearLayout>(R.id.layoutEliminarAnuncio)
        if (currentUserEmail == postOwnerEmail) {
            layoutEliminarAnuncio.visibility = View.VISIBLE
        } else {
            layoutEliminarAnuncio.visibility = View.GONE
        }

        val provincia = intent.getStringExtra("post_provincia")
        val duracion = intent.getStringExtra("post_duracion")
        val descripcion = intent.getStringExtra("post_descripcion")
        val fotoBase64 = intent.getStringExtra("post_foto")

        findViewById<TextView>(R.id.txtProvincia).text = provincia
        findViewById<TextView>(R.id.txtDuracion).text = duracion
        findViewById<TextView>(R.id.txtDescripcion).text = descripcion

        val imgPost = findViewById<ImageView>(R.id.imgPost)

        if (fotoBase64 != null) {
            val imageBytes = Base64.decode(fotoBase64, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(imageBytes)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgPost.setImageBitmap(bitmap)
        }
    }

    private fun getCurrentUserEmail(): String {
        // Lógica para obtener el correo electrónico del usuario actual, por ejemplo, desde SharedPreferences
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("email", "") ?: ""
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
