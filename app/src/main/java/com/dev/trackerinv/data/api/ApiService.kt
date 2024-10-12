package com.dev.trackerinv.data.api

import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("add_sale")
    suspend fun createSale(@Body sale: Sale): Response<ApiResponse>

    @POST("add_purchase")
    suspend fun createPurchase(@Body purchase: Purchase): Response<ApiResponse>

    @GET("get_sale/{id}")
    suspend fun getSaleById(@Path("id") id: String): Response<Sale>

    @GET("get_purchase/{id}")
    suspend fun getPurchaseById(@Path("id") id: String): Response<Purchase>

    @GET("get_purchases")
    suspend fun getAllPurchases(): List<Purchase>

    @GET("get_sales")
    suspend fun getAllSales(): List<Sale>
}
