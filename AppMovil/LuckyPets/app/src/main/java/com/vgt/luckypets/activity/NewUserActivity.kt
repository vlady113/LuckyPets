package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vgt.luckypets.R
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class NewUserActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var repeatEmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var ageCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        initViews()
    }

    private fun initViews() {
        emailEditText = findViewById(R.id.EditText_Email)
        repeatEmailEditText = findViewById(R.id.EditText_Email_Repeat)
        passwordEditText = findViewById(R.id.EditText_Password)
        repeatPasswordEditText = findViewById(R.id.EditText_Password_Repeat)
        ageCheckBox = findViewById(R.id.checkBoxMayorDeEdad)
    }

    fun procederRegistro(view: View) {
        val email = emailEditText.text.toString().trim()
        val repeatEmail = repeatEmailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val repeatPassword = repeatPasswordEditText.text.toString().trim()

        if (email.isEmpty() || repeatEmail.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            Toast.makeText(this, "¡Todos los campos deben ser completados!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Validar el formato del correo electrónico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "¡Formato de correo electrónico no válido!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (email != repeatEmail) {
            Toast.makeText(this, "¡Los correos electrónicos no coinciden!", Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (password.length < 9 || password.length > 25) {
            Toast.makeText(
                this,
                "¡La contraseña debe tener entre 9 y 25 caracteres!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (password != repeatPassword) {
            Toast.makeText(this, "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!ageCheckBox.isChecked) {
            Toast.makeText(
                this,
                "¡Tienes que ser mayor de edad para registrarte!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val newUser = Users(
            userID = 0, // Este valor es ignorado en la creación
            dni = "",
            nombre = "",
            apellidos = "",
            email = email,
            password = password,
            direccion = "",
            provincia = "",
            codigoPostal = "",
            telefono = "",
            fechaRegistro = LocalDate.now().toString(),
            saldoCR = 0.0,
            codigo_restablecimiento = "",
            codigo_expiry = "",
            es_administrador = false
        )

        RetrofitBuilder.api.registerUser(newUser).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@NewUserActivity, "¡Registro exitoso!", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@NewUserActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@NewUserActivity,
                        "Error en el registro: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(
                    this@NewUserActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    fun cancelarRegistro(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
