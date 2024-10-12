package com.dev.trackerinv.data.model

import android.graphics.Bitmap
// sale product (platform(flipkart, amazon, myntra, ajio, meesho,
// offline), date picker, stock_id(dropdown), product_name(onselect),
// p_img(onselect), cust_name, cust_phoneNo, quantity, sale_price, gst(cgst, sgst, igst),
// total invoice value( sp+gst ), delivery_channel(flipkart, amazon, myntra, ajio, meesho, delhivery, offline),
// delivery and other charges, net income(sale_price - delivery and other charges) )
data class Sale(
    val id: String,
    val image: String,
    val platform: String,
    val date: String,
    val stock_id: String,
    val product_name: String,
    val cus_name: String,
    val cus_ph: String,
    val quantity: Int,
    val sp: Double,
    val gst: GST,
//    val total_price: Double
    val delivery_channel: String,
    val delivery_and_other_expenses: Double,
//    val net_income: Double
)
