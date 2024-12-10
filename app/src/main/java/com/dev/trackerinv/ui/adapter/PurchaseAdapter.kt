package com.dev.trackerinv.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.trackerinv.R
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.ui.utils.ImagePickerUtil

class PurchaseAdapter(private val purchaseList: List<Purchase>, private val onItemClicked: (Purchase) -> Unit) : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {

    class PurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val invoiceNumber: TextView = itemView.findViewById(R.id.invoiceNumber)
        val invoiceDate: TextView = itemView.findViewById(R.id.invoiceDate)
        val supplierName: TextView = itemView.findViewById(R.id.supplierName)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val pricePerUnit: TextView = itemView.findViewById(R.id.pricePerUnit)
        val image: ImageView = itemView.findViewById(R.id.productImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_purchase_item, parent, false)
        return PurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val purchase = purchaseList[position]
        holder.invoiceNumber.text = purchase.inv_no
        holder.invoiceDate.text = purchase.inv_date
        holder.supplierName.text = purchase.sup_name
        holder.quantity.text = purchase.quantity.toString()
        holder.pricePerUnit.text = purchase.price_per_unit.toString()
        val bitmap = ImagePickerUtil.base64ToBitmap(purchase.image)
        holder.image.setImageBitmap(bitmap)
        holder.itemView.setOnClickListener {
            onItemClicked(purchase)
        }
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }
}
