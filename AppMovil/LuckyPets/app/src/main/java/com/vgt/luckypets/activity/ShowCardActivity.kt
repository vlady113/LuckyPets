package com.vgt.luckypets.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.ItemTouchHelperCallback
import com.vgt.luckypets.OnStartDragListener
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.CardAdapter
import com.vgt.luckypets.model.TarjetaBancariaDTO
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowCardActivity : AppCompatActivity(), OnStartDragListener {

    private lateinit var email: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardLogos: Map<String, String>
    private lateinit var adapter: CardAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_card)

        email = intent.getStringExtra("email") ?: ""
        recyclerView = findViewById(R.id.rvTarjetasGuardadas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.menuAtras).setOnClickListener {
            volverAtras()
        }

        findViewById<ImageView>(R.id.actualizarDatosTarjetas).setOnClickListener {
            actualizarTarjetas()
        }

        if (email.isNotEmpty()) {
            loadCardLogos()
        } else {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            Log.e("ShowCardActivity", "Correo electrónico no proporcionado en el Intent")
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (email.isNotEmpty()) {
            loadTarjetas()
        }
    }

    private fun loadCardLogos() {
        RetrofitBuilder.api.getCardLogos().enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    cardLogos = response.body() ?: emptyMap()
                    Log.d("ShowCardActivity", "Logos de tarjetas cargados correctamente: $cardLogos")
                    loadTarjetas()
                } else {
                    Log.e("ShowCardActivity", "Error al cargar logos de tarjetas: ${response.message()}")
                    Toast.makeText(this@ShowCardActivity, "Error al cargar logos de tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                Log.e("ShowCardActivity", "Error de red: ${t.localizedMessage}")
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadTarjetas() {
        RetrofitBuilder.api.getTarjetasByEmail(email).enqueue(object : Callback<List<TarjetaBancariaDTO>> {
            override fun onResponse(call: Call<List<TarjetaBancariaDTO>>, response: Response<List<TarjetaBancariaDTO>>) {
                if (response.isSuccessful) {
                    val tarjetas = response.body() ?: emptyList()
                    Log.d("ShowCardActivity", "Tarjetas encontradas: $tarjetas")
                    setupRecyclerView(tarjetas)
                } else {
                    Log.e("ShowCardActivity", "Error al cargar tarjetas: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@ShowCardActivity, "Error al cargar tarjetas: ${response.code()} - ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TarjetaBancariaDTO>>, t: Throwable) {
                Log.e("ShowCardActivity", "Error de red: ${t.localizedMessage}", t)
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(tarjetas: List<TarjetaBancariaDTO>) {
        if (tarjetas.isNotEmpty()) {
            adapter = CardAdapter(this, tarjetas.toMutableList(), cardLogos, { tarjeta ->
                showDeleteConfirmationDialog(tarjeta)
            }, this)
            recyclerView.adapter = adapter

            val callback = ItemTouchHelperCallback(adapter)
            itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        } else {
            Toast.makeText(this@ShowCardActivity, "No se encontraron tarjetas para el usuario.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog(tarjeta: TarjetaBancariaDTO) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Está seguro de que desea eliminar esta tarjeta bancaria?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            deleteTarjeta(tarjeta)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun deleteTarjeta(tarjeta: TarjetaBancariaDTO) {
        tarjeta.id?.let { id ->
            RetrofitBuilder.api.deleteTarjeta(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ShowCardActivity, "Tarjeta eliminada", Toast.LENGTH_SHORT).show()
                        adapter.removeItem(tarjeta)
                        actualizarTarjetas() // Recargar las tarjetas y actualizar el spinner
                    } else {
                        Toast.makeText(this@ShowCardActivity, "Error al eliminar tarjeta: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
        } ?: run {
            Toast.makeText(this, "Error: ID de tarjeta no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private fun volverAtras() {
        finish()
    }

    private fun actualizarTarjetas() {
        RetrofitBuilder.api.getTarjetasByEmail(email).enqueue(object : Callback<List<TarjetaBancariaDTO>> {
            override fun onResponse(call: Call<List<TarjetaBancariaDTO>>, response: Response<List<TarjetaBancariaDTO>>) {
                if (response.isSuccessful) {
                    val tarjetas = response.body() ?: emptyList()
                    adapter.updateItems(tarjetas)
                    Toast.makeText(this@ShowCardActivity, "Lista de tarjetas actualizada", Toast.LENGTH_SHORT).show()
                    updateSpinner() // Método para actualizar el spinner
                } else {
                    Toast.makeText(this@ShowCardActivity, "Error al actualizar tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TarjetaBancariaDTO>>, t: Throwable) {
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateSpinner() {
        RetrofitBuilder.api.getTarjetasByEmail(email).enqueue(object : Callback<List<TarjetaBancariaDTO>> {
            override fun onResponse(call: Call<List<TarjetaBancariaDTO>>, response: Response<List<TarjetaBancariaDTO>>) {
                if (response.isSuccessful) {
                    val tarjetas = response.body() ?: emptyList()
                    val spinnerAdapter = ArrayAdapter(
                        this@ShowCardActivity,
                        R.layout.spinner_item_selected,
                        tarjetas.map { "**** **** **** ${it.numeroTarjeta.toString().takeLast(4)}" }
                    )
                    spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    findViewById<Spinner>(R.id.Spinner_TarjetaTipo).adapter = spinnerAdapter
                } else {
                    Toast.makeText(this@ShowCardActivity, "Error al cargar tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TarjetaBancariaDTO>>, t: Throwable) {
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val ADD_CARD_REQUEST_CODE = 1
    }

    fun addTarjeta(view: View?) {
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("email", email)
        startActivityForResult(intent, ADD_CARD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("new_card")?.let { newCard ->
                if (newCard is TarjetaBancariaDTO) {
                    adapter.addItem(newCard)
                    recyclerView.scrollToPosition(adapter.itemCount - 1)
                    updateSpinner() // Actualizar el spinner
                }
            }
        }
    }

}