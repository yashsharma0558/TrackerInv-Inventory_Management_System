package com.dev.trackerinv.model

import android.graphics.Bitmap
// sale product (platform(flipkart, amazon, myntra, ajio, meesho,
// offline), date picker, stock_id(dropdown), product_name(onselect),
// p_img(onselect), cust_name, cust_phoneNo, quantity, sale_price, gst(cgst, sgst, igst),
// total invoice value( sp+gst ), delivery_channel(flipkart, amazon, myntra, ajio, meesho, delhivery, offline),
// delivery and other charges, net income(sale_price - delivery and other charges) )
data class Sale(
    val image: String,
    val id: String,
    val inv_no: String,
    val inv_date: String,
    val cus_name: String,
)
