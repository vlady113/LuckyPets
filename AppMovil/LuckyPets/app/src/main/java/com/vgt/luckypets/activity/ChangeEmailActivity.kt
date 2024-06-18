package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeEmailActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var newEmailEditText: EditText
    private lateinit var repeatNewEmailEditText: EditText
    private lateinit var currentEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_email)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_change_email)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        emailEditText = findViewById(R.id.EditText_Email_Now)
        newEmailEditText = findViewById(R.id.EditText_Email_Change)
        repeatNewEmailEditText = findViewById(R.id.EditText_Email_Change_Repeat)
        currentEmail = intent.getStringExtra("email") ?: ""
        emailEditText.setText(currentEmail)
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    fun GuardarCambiosCorreo(view: View?) {
        val newEmail = newEmailEditText.text.toString().trim()
        val repeatNewEmail = repeatNewEmailEditText.text.toString().trim()

        if (newEmail.isEmpty() || repeatNewEmail.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            Toast.makeText(this, "Introduzca un correo electrónico válido", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (newEmail != repeatNewEmail) {
            Toast.makeText(this, "Los correos electrónicos no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitBuilder.api.getUsuarioByEmail(newEmail).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ChangeEmailActivity,
                        "El correo electrónico ya está registrado",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    actualizarCorreoElectronico(newEmail)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(
                    this@ChangeEmailActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun actualizarCorreoElectronico(newEmail: String) {
        RetrofitBuilder.api.getUsuarioByEmail(currentEmail).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    if (usuario != null) {
                        usuario.email = newEmail
                        RetrofitBuilder.api.updateUsuario(currentEmail, usuario)
                            .enqueue(object : Callback<Users> {
                                override fun onResponse(
                                    call: Call<Users>,
                                    response: Response<Users>
                                ) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            this@ChangeEmailActivity,
                                            "¡Correo electrónico actualizado correctamente!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        volverAtras(null)
                                    } else {
                                        Toast.makeText(
                                            this@ChangeEmailActivity,
                                            "¡Error al actualizar el correo electrónico!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<Users>, t: Throwable) {
                                    Toast.makeText(
                                        this@ChangeEmailActivity,
                                        "Error de red: ${t.localizedMessage}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
                } else {
                    Toast.makeText(
                        this@ChangeEmailActivity,
                        "¡Usuario no encontrado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(
                    this@ChangeEmailActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun cancelarRegistro(view: View?) {
        volverAtras(null)
    }
}
