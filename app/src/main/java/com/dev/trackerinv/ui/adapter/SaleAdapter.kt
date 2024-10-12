package com.dev.trackerinv.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.trackerinv.R
import com.dev.trackerinv.data.model.Sale

class SaleAdapter(private val salesList: List<Sale>) : RecyclerView.Adapter<SaleAdapter.SaleViewHolder>() {

    class SaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val customerName: TextView = itemView.findViewById(R.id.customerName)
        val customerPhone: TextView = itemView.findViewById(R.id.customerPhone)
        val platform: TextView = itemView.findViewById(R.id.platform)
        val date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_sale_item, parent, false)
        return SaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        val sale = salesList[position]
        holder.productName.text = sale.product_name
        holder.customerName.text = sale.cus_name
        holder.customerPhone.text = sale.cus_ph
        holder.platform.text = sale.platform
        holder.date.text = sale.date
    }

    override fun getItemCount(): Int {
        return salesList.size
    }
}
