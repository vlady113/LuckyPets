package es.intermodular.equipo2.incidenciasies

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.intermodular.equipo2.incidenciasies.databinding.ActivityLoginBinding
import es.intermodular.equipo2.incidenciasies.datos.Api
import es.intermodular.equipo2.incidenciasies.datos.ApiService
import es.intermodular.equipo2.incidenciasies.datos.RetrofitBuilder
import es.intermodular.equipo2.incidenciasies.modelo.PerfilReponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class LoginActivity : AppCompatActivity() {

    // Variable utilizada para acceder a las vistas del layout de la actividad mediante View Binding
    private lateinit var binding: ActivityLoginBinding

    // Objeto utilizado para almacenar datos de manera persistente, como las preferencias del usuario
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de SharedPreferences para guardar datos persistentes
        sharedPref = getSharedPreferences("pref", MODE_PRIVATE)

        val checkBoxRecordarme = binding.checkBoxRecordarme

        // Configurar el OnClickListener para el botón de inicio de sesión
        binding.btnIniciarSesion.setOnClickListener {
            // Obtener el texto ingresado en los campos de usuario y contraseña
            val usuario = binding.EditTextUsuario.text.toString()
            val contrasenia = binding.EditTextContrasenia.text.toString()

            // Verificar si ambos campos están vacíos
            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                // Mostrar mensaje de error
                showToast("¡Por favor, rellene ambos campos!")
            } else {
                comprobacionCredenciales()
            }
        }

        val editor = sharedPref.edit()

        // Configurar el Listener para el CheckBox para recordar datos
        checkBoxRecordarme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Guardar datos en SharedPreferences si el CheckBox está marcado
                editor.putString("usuario", binding.EditTextUsuario.text.toString())
                editor.putString("contrasenia", binding.EditTextContrasenia.text.toString())
                editor.putBoolean("recordarme", true)
            } else {
                // Eliminar datos de SharedPreferences si el CheckBox no está marcado
                editor.remove("usuario")
                editor.remove("contrasenia")
                editor.remove("recordarme")
            }
            editor.apply() // Aplicar los cambios en SharedPreferences
        }

        // Recuperar y establecer datos si la opción de recordar está habilitada
        val recordarme = sharedPref.getBoolean("recordarme", false)
        if (recordarme) {
            val usuarioGuardado = sharedPref.getString("usuario", "")
            val contraseniaGuardada = sharedPref.getString("contrasenia", "")
            binding.EditTextUsuario.setText(usuarioGuardado)
            binding.EditTextContrasenia.setText(contraseniaGuardada)
            checkBoxRecordarme.isChecked = true
        }
        // Configurar el OnClickListener para el TextView de olvidé contraseña
        binding.textViewOlvideContr.setOnClickListener {
            // Mostrar mensaje de olvidé contraseña
            showToast("Por favor, póngase en contacto con su coordinador TIC")
        }


    }

    private fun comprobacionCredenciales() {
        val email = binding.EditTextUsuario.text.toString()
        val clave = binding.EditTextContrasenia.text.toString()

        Api.retrofitService.login(email, clave).enqueue(object : Callback<PerfilReponse> {
            override fun onResponse(call: Call<PerfilReponse>, response: Response<PerfilReponse>) {
                if (response.isSuccessful) {
                    val perfilResponse = response.body()
                    Log.i("Login usuario", "Respuesta succesfull")
                    Log.i("Loggin Usuario", perfilResponse!!.personal_id.toString())

                    onLoginSuccess(perfilResponse.personal_id)

                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error en las credenciales",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.i("Loggin Usuario", response.toString())
                }
            }

            override fun onFailure(call: Call<PerfilReponse>, t: Throwable) {
                // Manejar el error de conexión aquí
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }

    fun onLoginSuccess(idUsuario: Int) {
        val userPreferences = UserPreferences(this)
        //Mostramos que el usuario se ha logeado
        userPreferences.isLoggedIn = true
        //Guardamos el id de usuario
        userPreferences.userId = idUsuario

        //Iniciamos la actividad
        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        // Guardar datos en SharedPreferences al salir de la actividad
        val editor = sharedPref.edit()
        if (binding.checkBoxRecordarme.isChecked) {
            editor.putString("usuario", binding.EditTextUsuario.text.toString())
            editor.putString("contrasenia", binding.EditTextContrasenia.text.toString())
            editor.putBoolean("recordarme", true)
        } else {
            editor.remove("usuario")
            editor.remove("contrasenia")
            editor.remove("recordarme")
        }
        editor.apply() // Aplicar los cambios en SharedPreferences
    }

    // Método para validar el formato del usuario
    private fun validateUser(usuario: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9._%+-]+@(educantabria\\.es)$")
        return regex.matches(usuario)
    }

    // Método para mostrar un mensaje Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}