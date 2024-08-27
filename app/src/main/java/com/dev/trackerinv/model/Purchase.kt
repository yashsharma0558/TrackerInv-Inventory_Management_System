package com.dev.trackerinv.model

import android.graphics.Bitmap

data class Purchase(
    val image: Bitmap,
    val id: String,
    val inv_no: String,
    val inv_date: String,
    val sup_name: String,
    val sup_ph: String,
    val quantity: Int,
    // GST will have cgst, sgst, igst.
    // 1. exclude igst if cgst or sgst is entered and vice verca.
    // 2. if cgst is opted then sgst is mandatory and vice versa
    val gst: Double,
    val price_per_unit: Double,
    // Total price will be ( price_per_unit * quantity ) + gst
//    val total_price: Double
)