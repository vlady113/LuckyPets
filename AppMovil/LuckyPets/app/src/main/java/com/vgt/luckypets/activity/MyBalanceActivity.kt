package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import com.vgt.luckypets.model.TarjetaBancaria
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBalanceActivity : AppCompatActivity() {

    private lateinit var email: String
    private var userID: Long = 0L
    private lateinit var balanceEditText: EditText
    private lateinit var balanceAddEditText: EditText
    private lateinit var spinner: Spinner
    private var tarjetasDisponibles: List<TarjetaBancaria> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_balance)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_my_balance)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email") ?: ""
        userID = intent.getLongExtra("userID", 0L)
        balanceEditText = findViewById(R.id.EditText_Balance_Now)
        balanceAddEditText = findViewById(R.id.EditText_Balance_Add)
        spinner = findViewById(R.id.Spinner_EligeTarjeta)

        if (email.isNotEmpty()) {
            cargarSaldoUsuario()
        } else {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            Log.e("MyBalanceActivity", "Correo electrónico no proporcionado en el Intent")
        }
    }

    override fun onResume() {
        super.onResume()
        if (email.isNotEmpty()) {
            cargarTarjetasUsuario()
        }
    }

    private fun cargarSaldoUsuario() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        balanceEditText.setText(String.format("%.2f CR", usuario.saldoCR))
                        Log.d("MyBalanceActivity", "Saldo del usuario cargado correctamente")
                    } ?: run {
                        Toast.makeText(this@MyBalanceActivity, "Error al cargar datos: Respuesta vacía", Toast.LENGTH_SHORT).show()
                        Log.e("MyBalanceActivity", "Error al cargar datos: Respuesta vacía")
                    }
                } else {
                    Toast.makeText(this@MyBalanceActivity, "Error al cargar datos: ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("MyBalanceActivity", "Error al cargar datos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MyBalanceActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                Log.e("MyBalanceActivity", "Error de red", t)
            }
        })
    }

    private fun cargarTarjetasUsuario() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    Log.d("MyBalanceActivity", "Usuario encontrado: $usuario")
                    usuario?.userID?.let { userID ->
                        Log.d("MyBalanceActivity", "Usuario ID: $userID")
                        if (userID != 0L) {
                            RetrofitBuilder.api.getTarjetasByUserId(userID).enqueue(object : Callback<List<TarjetaBancaria>> {
                                override fun onResponse(call: Call<List<TarjetaBancaria>>, response: Response<List<TarjetaBancaria>>) {
                                    if (response.isSuccessful) {
                                        val tarjetas = response.body() ?: emptyList()
                                        Log.d("MyBalanceActivity", "Tarjetas encontradas: $tarjetas")
                                        setupSpinner(tarjetas)
                                    } else {
                                        Log.e("MyBalanceActivity", "Error al cargar tarjetas: ${response.message()}")
                                        Toast.makeText(this@MyBalanceActivity, "Error al cargar tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<List<TarjetaBancaria>>, t: Throwable) {
                                    Log.e("MyBalanceActivity", "Error de red: ${t.localizedMessage}")
                                    Toast.makeText(this@MyBalanceActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        } else {
                            Log.e("MyBalanceActivity", "User ID is 0")
                            Toast.makeText(this@MyBalanceActivity, "Error: ID de usuario es 0", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Log.e("MyBalanceActivity", "User ID is null")
                        Toast.makeText(this@MyBalanceActivity, "Error: ID de usuario es nulo", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("MyBalanceActivity", "Error al obtener usuario: ${response.message()}")
                    Toast.makeText(this@MyBalanceActivity, "Error al obtener usuario: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.e("MyBalanceActivity", "Error de red: ${t.localizedMessage}")
                Toast.makeText(this@MyBalanceActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner(tarjetas: List<TarjetaBancaria>) {
        tarjetasDisponibles = tarjetas
        val cardNumbers = if (tarjetas.isNotEmpty()) {
            tarjetas.map { "**** **** **** ${it.numeroTarjeta.toString().takeLast(4)}" }
        } else {
            listOf("No se encontró tarjeta")
        }
        val adapter = ArrayAdapter(this, R.layout.spinner_item_selected, cardNumbers)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun addBalance(view: View?) {
        if (tarjetasDisponibles.isEmpty()) {
            Toast.makeText(this, "Debe añadir una tarjeta bancaria primero.", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidadStr = balanceAddEditText.text.toString().trim()
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "Por favor, introduzca una cantidad válida.", Toast.LENGTH_SHORT).show()
            return
        }

        val cantidad = cantidadStr.toDoubleOrNull()
        if (cantidad == null || cantidad <= 0) {
            Toast.makeText(this, "La cantidad debe ser un número positivo.", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        val nuevoSaldo = usuario.saldoCR + cantidad
                        usuario.saldoCR = nuevoSaldo

                        RetrofitBuilder.api.updateUsuario(usuario.email, usuario).enqueue(object : Callback<Users> {
                            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                                if (response.isSuccessful) {
                                    balanceEditText.setText(String.format("%.2f CR", nuevoSaldo))
                                    balanceAddEditText.text.clear()
                                    Toast.makeText(this@MyBalanceActivity, "Saldo añadido correctamente.", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this@MyBalanceActivity, "Error al actualizar saldo: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Users>, t: Throwable) {
                                Toast.makeText(this@MyBalanceActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } ?: run {
                        Toast.makeText(this@MyBalanceActivity, "Error al cargar datos: Respuesta vacía", Toast.LENGTH_SHORT).show()
                        Log.e("MyBalanceActivity", "Error al cargar datos: Respuesta vacía")
                    }
                } else {
                    Toast.makeText(this@MyBalanceActivity, "Error al cargar datos: ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("MyBalanceActivity", "Error al cargar datos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MyBalanceActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addTarjeta(view: View?) {
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("userID", userID)
        startActivity(intent)
    }

    fun verTarjeta(view: View?) {
        val intent = Intent(this, ShowCardActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("userID", userID)
        startActivity(intent)
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("userID", userID)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
