package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
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

class MyDataActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var dniEditText: EditText
    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var direccionEditText: EditText
    private lateinit var provinciaEditText: EditText
    private lateinit var codigoPostalEditText: EditText
    private lateinit var telefonoEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_data)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        email = intent.getStringExtra("email") ?: ""
        if (email.isEmpty()) {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG)
                .show()
            finish()
            return
        }

        dniEditText = findViewById(R.id.EditText_DNI)
        nombreEditText = findViewById(R.id.EditText_Nombre)
        apellidosEditText = findViewById(R.id.EditText_Apellidos)
        direccionEditText = findViewById(R.id.EditText_Direccion)
        provinciaEditText = findViewById(R.id.EditText_Provincia)
        codigoPostalEditText = findViewById(R.id.EditText_CodigoPostal)
        telefonoEditText = findViewById(R.id.EditText_Telefono)

        if (email.isNotEmpty()) {
            cargarDatosUsuario()
        } else {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG)
                .show()
            Log.e("MyDataActivity", "Correo electrónico no proporcionado en el Intent")
        }

        val menuAjustesDatos: ImageView = findViewById(R.id.menuAjustesDatos)
        menuAjustesDatos.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun cargarDatosUsuario() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        dniEditText.setText(usuario.dni)
                        nombreEditText.setText(usuario.nombre)
                        apellidosEditText.setText(usuario.apellidos)
                        direccionEditText.setText(usuario.direccion)
                        provinciaEditText.setText(usuario.provincia)
                        codigoPostalEditText.setText(usuario.codigoPostal)
                        telefonoEditText.setText(usuario.telefono)
                        Log.d("MyDataActivity", "Datos del usuario cargados correctamente")
                    } ?: run {
                        Toast.makeText(
                            this@MyDataActivity,
                            "Error al cargar datos: Respuesta vacía",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("MyDataActivity", "Error al cargar datos: Respuesta vacía")
                    }
                } else {
                    Toast.makeText(
                        this@MyDataActivity,
                        "Error al cargar datos: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("MyDataActivity", "Error al cargar datos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(
                    this@MyDataActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("MyDataActivity", "Error de red", t)
            }
        })
    }

    private fun showPopupMenu(view: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_menu_data, null)

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupView.findViewById<TextView>(R.id.action_change_email).setOnClickListener {
            showChangeEmailActivity()
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.action_change_password).setOnClickListener {
            showChangePasswordActivity()
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(view, -100, 0)
    }

    private fun showChangeEmailActivity() {
        val intent = Intent(this, ChangeEmailActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    private fun showChangePasswordActivity() {
        val intent = Intent(this, ChangePasswordActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    fun guardarCambiosDatos(view: View?) {
        val usuario = Users(
            userID = 0L,
            email = email,
            dni = dniEditText.text.toString().trim(),
            nombre = nombreEditText.text.toString().trim(),
            apellidos = apellidosEditText.text.toString().trim(),
            direccion = direccionEditText.text.toString().trim(),
            provincia = provinciaEditText.text.toString().trim(),
            codigoPostal = codigoPostalEditText.text.toString().trim(),
            telefono = telefonoEditText.text.toString().trim(),
            password = "", // no se modifica aquí
            fechaRegistro = "", // no se modifica aquí
            saldoCR = 0.0, // no se modifica aquí
            codigo_restablecimiento = null,
            codigo_expiry = null,
            es_administrador = false
        )

        RetrofitBuilder.api.updateUsuario(email, usuario).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@MyDataActivity,
                        "¡Datos actualizados correctamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("MyDataActivity", "¡Datos del usuario actualizados correctamente!")
                    // Volver a PrincipalActivity
                    val intent = Intent(this@MyDataActivity, PrincipalActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@MyDataActivity,
                        "Error al actualizar datos",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("MyDataActivity", "Error al actualizar datos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(
                    this@MyDataActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("MyDataActivity", "Error de red", t)
            }
        })
    }

    fun cancelarCambiosDatos(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}