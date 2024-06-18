package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.vgt.luckypets.databinding.ActivityLoginBinding
import com.vgt.luckypets.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this

        // Cargar los detalles de inicio de sesión guardados, si existen
        val (savedEmail, savedPassword) = loginViewModel.getLoginDetails()
        if (savedEmail != null && savedPassword != null) {
            binding.EditTextEmail.setText(savedEmail)
            binding.EditTextContrasenia.setText(savedPassword)
            binding.checkBoxRecordarme.isChecked = true
        }

        // Añadir TextWatcher al campo de correo electrónico
        binding.EditTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (binding.checkBoxRecordarme.isChecked) {
                    binding.checkBoxRecordarme.isChecked = false
                    binding.EditTextContrasenia.text.clear()
                }
            }
        })

        // Observar los LiveData para cambios de estado de usuario y mensajes de error
        loginViewModel.user.observe(this) { user ->
            user?.let {
                val intent = Intent(this, PrincipalActivity::class.java)
                intent.putExtra("email", it.email)
                Log.d("LoginActivity", "Enviando email: ${it.email}")
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }

        // Manejar el evento de clic en el botón de inicio de sesión
        binding.btnIniciarSesion.setOnClickListener {
            val email = binding.EditTextEmail.text.toString().trim()
            val password = binding.EditTextContrasenia.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "¡Los campos de correo electrónico y contraseña no pueden estar vacíos!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "¡Ingrese un correo electrónico válido!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            if (password.length < 9 || password.length > 25) {
                Toast.makeText(
                    this,
                    "¡La contraseña debe tener entre 9 y 25 caracteres!",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (binding.checkBoxRecordarme.isChecked) {
                saveLoginDetails(email, password)
            } else {
                clearLoginDetails()
            }

            val loginData = mapOf("email" to email, "password" to password)
            loginViewModel.loginUser(loginData)
        }

        // Manejar el evento de clic para registrarse y restablecer contraseña
        binding.textViewRegistrarse.setOnClickListener {
            startActivity(Intent(this, NewUserActivity::class.java))
        }

        binding.textViewOlvideContr.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }

    private fun saveLoginDetails(email: String, password: String) {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("email", email)
            putString("password", password)
            putBoolean("remember_me", true)
            apply()
        }
    }

    private fun clearLoginDetails() {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("email", "")
            putString("password", "")
            putBoolean("remember_me", false)
            apply()
        }
    }
}
