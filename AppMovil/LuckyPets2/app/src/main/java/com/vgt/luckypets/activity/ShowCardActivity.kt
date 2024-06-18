package com.vgt.luckypets.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.CardAdapter
import com.vgt.luckypets.callback.ItemMoveCallback
import com.vgt.luckypets.model.TarjetaBancaria
import com.vgt.luckypets.model.Users
import com.vgt.luckypets.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowCardActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardLogos: Map<String, String>
    private lateinit var adapter: CardAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.show_card)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = intent.getStringExtra("email") ?: ""
        recyclerView = findViewById(R.id.rvTarjetasGuardadas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadCardLogos()
    }

    private fun loadCardLogos() {
        RetrofitBuilder.api.getCardLogos().enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    cardLogos = response.body() ?: emptyMap()
                    Log.d("ShowCardActivity", "Card logos loaded successfully: $cardLogos")
                    loadTarjetas()
                } else {
                    Log.e("ShowCardActivity", "Error loading card logos: ${response.message()}")
                    Toast.makeText(this@ShowCardActivity, "Error al cargar logos de tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                Log.e("ShowCardActivity", "Network error: ${t.localizedMessage}")
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadTarjetas() {
        RetrofitBuilder.api.getUsuarioByEmail(email).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    Log.d("ShowCardActivity", "Usuario encontrado: $usuario")
                    usuario?.userID?.let { userID ->
                        RetrofitBuilder.api.getTarjetasByUserId(userID).enqueue(object :
                            Callback<List<TarjetaBancaria>> {
                            override fun onResponse(call: Call<List<TarjetaBancaria>>, response: Response<List<TarjetaBancaria>>) {
                                if (response.isSuccessful) {
                                    val tarjetas = response.body() ?: emptyList()
                                    Log.d("ShowCardActivity", "Tarjetas encontradas: $tarjetas")
                                    adapter = CardAdapter(tarjetas.toMutableList(), cardLogos, { tarjeta ->
                                        deleteTarjeta(tarjeta)
                                    }, { viewHolder ->
                                        itemTouchHelper.startDrag(viewHolder)
                                    })
                                    recyclerView.adapter = adapter // Asignar el adaptador aquí

                                    val callback = ItemMoveCallback(adapter)
                                    itemTouchHelper = ItemTouchHelper(callback)
                                    itemTouchHelper.attachToRecyclerView(recyclerView)
                                } else {
                                    Log.e("ShowCardActivity", "Error loading tarjetas: ${response.message()}")
                                    Toast.makeText(this@ShowCardActivity, "Error al cargar tarjetas: ${response.message()}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<List<TarjetaBancaria>>, t: Throwable) {
                                Log.e("ShowCardActivity", "Network error: ${t.localizedMessage}")
                                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    } ?: run {
                        Log.e("ShowCardActivity", "User ID is null")
                    }
                } else {
                    Log.e("ShowCardActivity", "Error obtaining user: ${response.message()}")
                    Toast.makeText(this@ShowCardActivity, "Error al obtener usuario: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.e("ShowCardActivity", "Network error: ${t.localizedMessage}")
                Toast.makeText(this@ShowCardActivity, "Error de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
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

    fun volverAtras(view: View?) {
        val intent = Intent(this, MyBalanceActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
