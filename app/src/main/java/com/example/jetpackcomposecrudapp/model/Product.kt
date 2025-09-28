package com.example.jetpackcomposecrudapp.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_name", alternate = ["productname"])
    val product_name: String,
    val quantity: Int,
    @SerializedName("price")
    val priceString: String?,
    @SerializedName("productid")
    val productId: Int?
) {
    val price: Double
        get() = priceString?.toDoubleOrNull() ?: 0.0

    // Secondary constructor for creating a product to send to the server
    constructor(product_name: String, quantity: Int, price: Double) : this(
        product_name,
        quantity,
        price.toString(),
        null
    )
}
