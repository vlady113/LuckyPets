package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmCodeResetActivity : AppCompatActivity() {
    private lateinit var email: String  // Email obtenido del activity anterior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_code_reset)
        setupUI()

        // Recuperar email del intento
        email = intent.getStringExtra("email") ?: ""

        val confirmButton = findViewById<Button>(R.id.btnConfirmarCodigo)
        confirmButton.setOnClickListener { confirmCode() }
    }

    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_confirm_code_reset)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun confirmCode() {
        val codeEditText = findViewById<EditText>(R.id.EditText_RestablecerCodigo)
        val code = codeEditText.text.toString().trim()

        if (code.length == 9) {
            RetrofitBuilder.api.verifyResetCode(mapOf("email" to email, "code" to code))
                .enqueue(object : Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        if (response.isSuccessful && response.body() == true) {
                            // Código verificado con éxito, proceder a cambiar contraseña
                            val intent = Intent(
                                this@ConfirmCodeResetActivity,
                                NewPasswordActivity::class.java
                            )
                            intent.putExtra(
                                "email",
                                email
                            )  // Pasar el email a la siguiente actividad
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@ConfirmCodeResetActivity,
                                "Código incorrecto o expirado.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Toast.makeText(
                            this@ConfirmCodeResetActivity,
                            "Error de red: ${t.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        } else {
            Toast.makeText(this, "El código debe tener 9 caracteres.", Toast.LENGTH_SHORT).show()
        }
    }

    fun volverAtras(view: View?) {
        startActivity(Intent(this, ResetPasswordActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        })
        finish()
    }


}

