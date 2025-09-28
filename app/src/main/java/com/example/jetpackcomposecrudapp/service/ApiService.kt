package com.example.jetpackcomposecrudapp.service

import com.example.jetpackcomposecrudapp.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("products")
    suspend fun createProduct(@Body product: Product): Response<Any>

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>
}