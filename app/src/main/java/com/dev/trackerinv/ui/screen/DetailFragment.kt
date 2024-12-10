package com.dev.trackerinv.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import com.dev.trackerinv.R
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import com.dev.trackerinv.databinding.FragmentDetailBinding
import com.dev.trackerinv.databinding.FragmentSaleBinding
import com.dev.trackerinv.ui.utils.ImagePickerUtil
import com.google.gson.Gson

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = arguments?.getString("type")
        if (type == "sale") {
            // Deserialize the JSON string back to a Sale object
            val saleJson = arguments?.getString("selectedSale")
            val gson = Gson()
            val sale = gson.fromJson(saleJson, Sale::class.java)
            displaySalesData(sale)

        } else if (type == "purchase") {
            // Deserialize the JSON string back to a Purchase object
            val purchaseJson = arguments?.getString("selectedPurchase")
            val gson = Gson()
            val purchase = gson.fromJson(purchaseJson, Purchase::class.java)
            displayPurchaseData(purchase)
        }
    }

    private fun displayPurchaseData(purchase:Purchase) {
        val bitmap = ImagePickerUtil.base64ToBitmap(purchase.image)
        binding.imageView.setImageBitmap(bitmap)
        val str = "Invoice Number = "+purchase.inv_no + "\nInvoice Date = "+purchase.inv_date +"\nSupplier Name = "+purchase.sup_name+ "\nSupplier Phone = "+purchase.sup_ph + "\nQuantity = "+purchase.quantity + "\nGST:\nCGST = "+purchase.gst.cgst+"\nIGST = "+purchase.gst.igst + "\nSGST = "+purchase.gst.sgst + "\nPrice per unit = "+purchase.price_per_unit
        binding.placeholderTextView.text = str
    }

    private fun displaySalesData(sale:Sale) {
        val bitmap = ImagePickerUtil.base64ToBitmap(sale.image)
        binding.imageView.setImageBitmap(bitmap)
        val str = "Product name = "+ sale.product_name + "\nPlatform name = "+sale.platform +"\nDate = "+sale.date + "\nStock id "+ sale.stock_id+ "\nCustomer name = "+sale.cus_name+"\nCustomer number"+sale.cus_ph+"\nQuantity = "+sale.quantity+"\nGST:\nCGST = "+sale.gst.cgst+"\nIGST = "+sale.gst.igst + "\nSGST = "+sale.gst.sgst + "\nDelivery Channel = "+sale.delivery_channel+ "\nDelivery and other charges "+sale.delivery_and_other_expenses
        binding.placeholderTextView.text = str
    }

}