package com.vgt.luckypets.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.R
import com.vgt.luckypets.adapter.TransactionAdapter
import com.vgt.luckypets.model.Transaction
import java.time.LocalDate

class TransactionActivity : AppCompatActivity() {

    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transactionList: MutableList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_transaction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_transaction)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvMyPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        transactionList = mutableListOf(
            Transaction(1, "Usuario1", LocalDate.parse("2024-01-13"), 100.00, Transaction.TipoTransaccion.adicion, null, null),
            Transaction(2, "Usuario2", LocalDate.parse("2024-02-22"), 200.50, Transaction.TipoTransaccion.adicion, null, null),
            Transaction(3, "Usuario3", LocalDate.parse("2024-03-03"), -50.75, Transaction.TipoTransaccion.sustraccion, null, null)
            // Añadir más transacciones de prueba según sea necesario
        )

        transactionAdapter = TransactionAdapter(transactionList)
        recyclerView.adapter = transactionAdapter

        findViewById<ImageView>(R.id.actualizarDatos).setOnClickListener {
            actualizarLista()
        }
    }

    private fun actualizarLista() {
        val nuevasTransacciones = listOf(
            Transaction(4, "Usuario4", LocalDate.parse("2024-07-01"), 500.00, Transaction.TipoTransaccion.adicion, null, null),
            Transaction(5, "Usuario5", LocalDate.parse("2024-07-02"), -300.00, Transaction.TipoTransaccion.sustraccion, null, null),
            Transaction(6, "Usuario6", LocalDate.parse("2024-07-03"), 150.75, Transaction.TipoTransaccion.adicion, null, null)
        )
        transactionAdapter.updateTransactions(nuevasTransacciones)
    }

    fun volverAtras(view: View?) {
        finish()
    }
}
