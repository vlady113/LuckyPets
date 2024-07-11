package com.vgt.luckypets.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.ItemTouchHelperCallback
import com.vgt.luckypets.OnStartDragListener
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.CardAdapter
import com.vgt.luckypets.model.TarjetaBancaria
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

        if (email.isNotEmpty()) {
            loadCardLogos()
        } else {
            Toast.makeText(this, "Error: No se proporcionó correo electrónico.", Toast.LENGTH_LONG).show()
            Log.e("ShowCardActivity", "Correo electrónico no proporcionado en el Intent")
            finish()
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
        RetrofitBuilder.api.getTarjetasByEmail(email).enqueue(object : Callback<List<TarjetaBancaria>> {
            override fun onResponse(call: Call<List<TarjetaBancaria>>, response: Response<List<TarjetaBancaria>>) {
                if (response.isSuccessful) {
                    val tarjetas = response.body() ?: emptyList()
                    Log.d("ShowCardActivity", "Tarjetas encontradas: $tarjetas")
                    setupRecyclerView(tarjetas)
                } else {
                    Log.e("ShowCardActivity", "Error al cargar tarjetas: ${response.message()}")
                    Toast.makeText(this@ShowCardActivity, "Error al cargar tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TarjetaBancaria>>, t: Throwable) {
                Log.e("ShowCardActivity", "Error de red: ${t.localizedMessage}")
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(tarjetas: List<TarjetaBancaria>) {
        if (tarjetas.isNotEmpty()) {
            adapter = CardAdapter(this, tarjetas.toMutableList(), cardLogos, { tarjeta ->
                deleteTarjeta(tarjeta)
            }, this)
            recyclerView.adapter = adapter

            val callback = ItemTouchHelperCallback(adapter)
            itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        } else {
            Toast.makeText(this@ShowCardActivity, "No se encontraron tarjetas para el usuario.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTarjeta(tarjeta: TarjetaBancaria) {
        tarjeta.id?.let { id ->
            RetrofitBuilder.api.deleteTarjeta(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ShowCardActivity, "Tarjeta eliminada", Toast.LENGTH_SHORT).show()
                        adapter.removeItem(tarjeta)
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
}
