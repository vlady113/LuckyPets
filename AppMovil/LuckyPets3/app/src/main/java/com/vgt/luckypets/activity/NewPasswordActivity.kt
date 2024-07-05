package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordActivity : AppCompatActivity() {
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_new_password)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        passwordEditText = findViewById(R.id.EditText_NewPassword)
        repeatPasswordEditText = findViewById(R.id.EditText_NewPassword_Repeat)

        // Retrieve the email from the Intent
        email = intent.getStringExtra("email") ?: ""
        if (email.isBlank()) {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            Log.d("NewPasswordActivity", "Correo electrónico no disponible.")
            finish() // Close activity if email is not available
        }
    }

    fun confirmarNuevaContrasenia(view: View) {
        Log.d("NewPasswordActivity", "Botón presionado.")
        val password = passwordEditText.text.toString().trim()
        val repeatPassword = repeatPasswordEditText.text.toString().trim()

        if (password != repeatPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length !in 9..25) {
            Toast.makeText(
                this,
                "La contraseña debe tener entre 9 y 25 caracteres.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Log.d("NewPasswordActivity", "Enviando solicitud de cambio de contraseña para: $email")
        val apiService = RetrofitBuilder.api
        apiService.changePassword(mapOf("email" to email, "newPassword" to password))
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@NewPasswordActivity,
                            "Contraseña actualizada correctamente.",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@NewPasswordActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@NewPasswordActivity,
                            "Error al actualizar contraseña: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(
                            "NewPasswordActivity",
                            "Fallo al actualizar: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(
                        this@NewPasswordActivity,
                        "Error de red: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("NewPasswordActivity", "Error de red", t)
                }
            })
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, ResetPasswordActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
