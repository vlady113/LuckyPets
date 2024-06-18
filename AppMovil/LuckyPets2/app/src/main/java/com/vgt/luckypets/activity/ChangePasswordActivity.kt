package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
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

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var currentPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var repeatNewPasswordEditText: EditText
    private lateinit var currentEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_change_password)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentPasswordEditText = findViewById(R.id.EditText_Change_Password)
        newPasswordEditText = findViewById(R.id.EditText_Change_Password_New)
        repeatNewPasswordEditText = findViewById(R.id.EditText_Change_Password_New_Repeat)
        currentEmail = intent.getStringExtra("email") ?: ""
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    fun GuardarCambiosContraseniaNueva(view: View?) {
        val currentPassword = currentPasswordEditText.text.toString().trim()
        val newPassword = newPasswordEditText.text.toString().trim()
        val repeatNewPassword = repeatNewPasswordEditText.text.toString().trim()

        if (currentPassword.isEmpty() || newPassword.isEmpty() || repeatNewPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword.length < 9 || newPassword.length > 25) {
            Toast.makeText(
                this,
                "La nueva contraseña debe tener entre 9 y 25 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (newPassword != repeatNewPassword) {
            Toast.makeText(this, "Las nuevas contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword == currentPassword) {
            Toast.makeText(
                this,
                "La nueva contraseña no puede ser igual a la actual",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val requestData = mapOf(
            "email" to currentEmail,
            "currentPassword" to currentPassword,
            "newPassword" to newPassword
        )

        RetrofitBuilder.api.changePassword(requestData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "¡Contraseña actualizada correctamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                    volverAtras(null)
                } else {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        response.errorBody()?.string() ?: "Error al actualizar la contraseña",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun cancelarContraseniaNueva(view: View?) {
        volverAtras(null)
    }
}
