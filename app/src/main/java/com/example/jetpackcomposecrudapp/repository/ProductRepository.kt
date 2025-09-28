package com.example.jetpackcomposecrudapp.repository

import com.example.jetpackcomposecrudapp.model.Product
import com.example.jetpackcomposecrudapp.service.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://4745868192ab.ngrok-free.app/api/") // Use 10.0.2.2 for emulator localhost
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun createProduct(product: Product): Response<Any> {
        return api.createProduct(product)
    }
}
