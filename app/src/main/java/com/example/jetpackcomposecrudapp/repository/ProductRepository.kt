package com.example.jetpackcomposecrudapp.repository

import com.example.jetpackcomposecrudapp.model.Product
import com.example.jetpackcomposecrudapp.service.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://42e21078f7d9.ngrok-free.app/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun createProduct(product: Product): Response<Any> {
        return api.createProduct(product)
    }

    suspend fun getAllProducts(): Response<List<Product>> {
        return api.getAllProducts()
    }
}
