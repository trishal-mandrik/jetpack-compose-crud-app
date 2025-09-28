package com.example.jetpackcomposecrudapp.repository

import com.example.jetpackcomposecrudapp.model.Product
import com.example.jetpackcomposecrudapp.service.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://405bd8cbfd37.ngrok-free.app/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun createProduct(product: Product): Response<Any> {
        return api.createProduct(product)
    }

    suspend fun getAllProducts(): Response<List<Product>> {
        return api.getAllProducts()
    }

    suspend fun deleteProduct(id: Int): Response<Any> {
        return api.deleteProduct(id)
    }
}
