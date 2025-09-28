package com.example.jetpackcomposecrudapp.model

import com.google.gson.annotations.SerializedName

data class Product(
    val product_name: String,
    val quantity: Int,
    val price: Double
)

