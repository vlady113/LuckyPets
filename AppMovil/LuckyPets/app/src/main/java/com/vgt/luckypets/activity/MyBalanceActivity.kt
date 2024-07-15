package com.vgt.luckypets.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vgt.luckypets.R
import com.vgt.luckypets.model.TarjetaBancariaDTO
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBalanceActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var balanceEditText: EditText
    private lateinit var balanceAddEditText: EditText
    private lateinit var spinner: Spinner
    private var tarjetasDisponibles: List<TarjetaBancariaDTO> = emptyList()
    private var initialBalance: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_balance)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_my_balance)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email") ?: ""
        balanceEditText = findViewById(R.id.EditText_Balance_Now)
        balanceAddEditText = findViewById(R.id.EditText_Balance_Add)
        spinner = findViewById(R.id.Spinner_EligeTarjeta)

        findViewById<ImageView>(R.id.menuAtras).setOnClickListener {
            volverAtras()
        }

        if (email.isNotEmpty()) {
            cargarSaldoUsuario()
            cargarTarjetasUsuario()
        } else {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            Log.e("MyBalanceActivity", "Correo electrónico no proporcionado en el Intent")
        }
    }

    private fun cargarSaldoUsuario() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        initialBalance = usuario.saldoCR
                        balanceEditText.setText(String.format("%.2f CR", initialBalance))
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
        RetrofitBuilder.api.getTarjetasByEmail(email).enqueue(object : Callback<List<TarjetaBancariaDTO>> {
            override fun onResponse(call: Call<List<TarjetaBancariaDTO>>, response: Response<List<TarjetaBancariaDTO>>) {
                if (response.isSuccessful) {
                    val tarjetas = response.body() ?: emptyList()
                    Log.d("MyBalanceActivity", "Tarjetas encontradas: $tarjetas")
                    setupSpinner(tarjetas)
                } else {
                    Log.e("MyBalanceActivity", "Error al cargar tarjetas: ${response.message()}")
                    Toast.makeText(this@MyBalanceActivity, "Error al cargar tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TarjetaBancariaDTO>>, t: Throwable) {
                Log.e("MyBalanceActivity", "Error de red: ${t.localizedMessage}")
                Toast.makeText(this@MyBalanceActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner(tarjetas: List<TarjetaBancariaDTO>) {
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

        val cantidadStr = balanceAddEditText.text.toString().trim().replace(",", ".")
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
        startActivityForResult(intent, ADD_CARD_REQUEST_CODE)
    }

    fun verTarjeta(view: View?) {
        val intent = Intent(this, ShowCardActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("new_card")?.let { newCard ->
                if (newCard is TarjetaBancariaDTO) {
                    tarjetasDisponibles = tarjetasDisponibles.toMutableList().apply { add(newCard) }
                    setupSpinner(tarjetasDisponibles)
                }
            }
        }
    }

    private fun volverAtras() {
        val currentBalanceStr = balanceEditText.text.toString().replace(" CR", "").trim().replace(",", ".")
        val currentBalance = currentBalanceStr.toDoubleOrNull() ?: initialBalance
        val intent = Intent()
        if (currentBalance != initialBalance) {
            intent.putExtra("new_balance", currentBalance)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        private const val ADD_CARD_REQUEST_CODE = 1
    }

}
