package com.example.level_up_appmovil.model

data class Product(
    val id: String, // Corresponds to CÓDIGO
    val name: String,
    val category: String,
    val description: String,
    val price: Double,
    val imageUrl: String // URL to an image of the product
)
