package com.vgt.luckypets.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vgt.luckypets.R
import com.vgt.luckypets.model.Transaction
import java.time.format.DateTimeFormatter

class TransactionAdapter(private var transactionList: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentTransaction = transactionList[position]
        holder.fechaTextView.text = currentTransaction.fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        holder.montoTextView.text = if (currentTransaction.montoCR >= 0) "+${currentTransaction.montoCR}" else currentTransaction.montoCR.toString()
        holder.tipoTextView.text = currentTransaction.tipo.name.capitalize()
    }

    override fun getItemCount() = transactionList.size

    fun updateTransactions(newTransactions: List<Transaction>) {
        transactionList = newTransactions
        notifyDataSetChanged()
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaTextView: TextView = itemView.findViewById(R.id.txtFecha)
        val montoTextView: TextView = itemView.findViewById(R.id.txtMontoCR)
        val tipoTextView: TextView = itemView.findViewById(R.id.txtTipo)
    }
}
