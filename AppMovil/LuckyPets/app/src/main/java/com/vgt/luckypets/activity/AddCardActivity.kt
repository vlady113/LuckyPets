package com.vgt.luckypets.activity

import android.app.Activity
import android.app.DatePickerDialog
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
import com.vgt.luckypets.model.TarjetaBancaria
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import com.google.gson.Gson
import com.vgt.luckypets.R
import com.vgt.luckypets.model.TarjetaBancariaDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class AddCardActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var numTarjetaEditText: EditText
    private lateinit var titularTarjetaEditText: EditText
    private lateinit var fechaExpEditText: EditText
    private lateinit var tarjetaTipoSpinner: Spinner
    private lateinit var tarjetaCVVEditText: EditText
    private lateinit var cardLogos: Map<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_card)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email") ?: ""
        numTarjetaEditText = findViewById(R.id.EditText_NumTarjeta)
        titularTarjetaEditText = findViewById(R.id.EditText_TitularTarjeta)
        fechaExpEditText = findViewById(R.id.EditText_FechaExp)
        tarjetaTipoSpinner = findViewById(R.id.Spinner_TarjetaTipo)
        tarjetaCVVEditText = findViewById(R.id.EditText_TarjetaCVV)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.card_types_array,
            R.layout.spinner_item_selected
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            tarjetaTipoSpinner.adapter = adapter
        }

        loadCardLogos()
    }

    private fun loadCardLogos() {
        RetrofitBuilder.api.getCardLogos().enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    cardLogos = response.body() ?: emptyMap()
                } else {
                    Toast.makeText(this@AddCardActivity, "Error al cargar logos de tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                Toast.makeText(this@AddCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showDatePickerExp(view: View) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                fechaExpEditText.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun volverAtras(view: View?) {
        val intent = Intent(this, MyBalanceActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

    fun guardarCambiosDatos(view: View?) {
        val numTarjeta = numTarjetaEditText.text.toString().trim()
        val titularTarjeta = titularTarjetaEditText.text.toString().trim()
        val fechaExpStr = fechaExpEditText.text.toString().trim()
        val tarjetaTipo = tarjetaTipoSpinner.selectedItem.toString().trim()
        val tarjetaCVV = tarjetaCVVEditText.text.toString().trim()

        if (numTarjeta.isEmpty() || titularTarjeta.isEmpty() || fechaExpStr.isEmpty() || tarjetaTipo.isEmpty() || tarjetaCVV.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val fechaExp: LocalDate
        try {
            val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            fechaExp = LocalDate.parse(fechaExpStr, inputFormatter)
            Log.d("AddCardActivity", "Fecha de expiración parseada correctamente: $fechaExp")
        } catch (e: DateTimeParseException) {
            Toast.makeText(this, "Formato de fecha inválido. Use día / mes / año.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("AddCardActivity", "Número de tarjeta: $numTarjeta")
        Log.d("AddCardActivity", "Titular de la tarjeta: $titularTarjeta")
        Log.d("AddCardActivity", "Fecha de expiración: $fechaExpStr")
        Log.d("AddCardActivity", "Tipo de tarjeta: $tarjetaTipo")
        Log.d("AddCardActivity", "CVC: $tarjetaCVV")

        verificarTarjetaExistente(numTarjeta.toLong(), titularTarjeta, fechaExp, tarjetaTipo, tarjetaCVV.toInt(), email)
    }

    private fun verificarTarjetaExistente(numTarjeta: Long, titularTarjeta: String, fechaExp: LocalDate, tarjetaTipo: String, tarjetaCVC: Int, email: String) {
        RetrofitBuilder.api.getTarjetaByNumero(numTarjeta).enqueue(object : Callback<TarjetaBancaria> {
            override fun onResponse(call: Call<TarjetaBancaria>, response: Response<TarjetaBancaria>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddCardActivity, "El número de tarjeta ya está registrado.", Toast.LENGTH_SHORT).show()
                } else {
                    if (response.code() == 404) {
                        // Tarjeta no encontrada, procedemos a crearla
                        crearTarjeta(numTarjeta, titularTarjeta, fechaExp, tarjetaTipo, tarjetaCVC, email)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("AddCardActivity", "Error al verificar la tarjeta: ${response.message()}, cuerpo de error: $errorBody")
                        Toast.makeText(this@AddCardActivity, "Error al verificar la tarjeta: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<TarjetaBancaria>, t: Throwable) {
                Log.e("AddCardActivity", "Error de red al verificar la tarjeta: ${t.localizedMessage}")
                Toast.makeText(this@AddCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun crearTarjeta(numTarjeta: Long, titularTarjeta: String, fechaExp: LocalDate, tarjetaTipo: String, tarjetaCVC: Int, email: String) {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    response.body()?.let { usuario ->
                        if (usuario.email != email) {
                            Log.e("AddCardActivity", "Email del usuario no coincide: esperado $email, recibido ${usuario.email}")
                            Toast.makeText(this@AddCardActivity, "Error: email del usuario no coincide.", Toast.LENGTH_SHORT).show()
                            return
                        }
                        val tarjetaDTO = TarjetaBancariaDTO(
                            numeroTarjeta = numTarjeta,
                            fechaCaducidad = fechaExp.toString(), // Fecha en formato ISO
                            titularTarjeta = titularTarjeta,
                            emisorTarjeta = tarjetaTipo,
                            cvv = tarjetaCVC,
                            imgTarjeta = cardLogos[tarjetaTipo] ?: cardLogos["Otros"] ?: "",
                            usuarioEmail = email
                        )
                        Log.d("AddCardActivity", "Tarjeta DTO a enviar: $tarjetaDTO")

                        // Convertir la tarjeta a JSON para verificar la estructura
                        val gson = Gson()
                        val tarjetaJson = gson.toJson(tarjetaDTO)
                        Log.d("AddCardActivity", "Tarjeta DTO JSON: $tarjetaJson")

                        RetrofitBuilder.api.createTarjeta(tarjetaDTO).enqueue(object : Callback<TarjetaBancaria> {
                            override fun onResponse(call: Call<TarjetaBancaria>, response: Response<TarjetaBancaria>) {
                                if (response.isSuccessful) {
                                    val resultIntent = Intent()
                                    resultIntent.putExtra("new_card", response.body())
                                    setResult(Activity.RESULT_OK, resultIntent)
                                    Toast.makeText(this@AddCardActivity, "¡Tarjeta guardada exitosamente!", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    Log.e("AddCardActivity", "Error al guardar la tarjeta: ${response.message()}, cuerpo de error en Android: $errorBody")
                                    Toast.makeText(this@AddCardActivity, "Error al guardar la tarjeta: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<TarjetaBancaria>, t: Throwable) {
                                Log.e("AddCardActivity", "Error de red al guardar la tarjeta: ${t.localizedMessage}")
                                Toast.makeText(this@AddCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } ?: run {
                        Toast.makeText(this@AddCardActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AddCardActivity", "Error al obtener usuario: ${response.message()}, cuerpo de error: $errorBody")
                    Toast.makeText(this@AddCardActivity, "Error al obtener usuario: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.e("AddCardActivity", "Error de red al obtener usuario: ${t.localizedMessage}")
                Toast.makeText(this@AddCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun cancelarCambiosDatos(view: View?) {
        volverAtras(null)
    }

}
