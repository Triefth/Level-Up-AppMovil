package com.example.level_up_appmovil.viewmodel

import com.example.level_up_appmovil.model.Product

data class CatalogoUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val error: String? = null
)
