package com.example.level_up_appmovil.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.level_up_appmovil.LevelUpApp
import com.example.level_up_appmovil.R
import com.example.level_up_appmovil.model.Product
import com.example.level_up_appmovil.viewmodel.CatalogoUiState
import com.example.level_up_appmovil.viewmodel.CatalogoViewModel
import com.example.level_up_appmovil.viewmodel.ViewModelFactory
import java.text.NumberFormat
import java.util.*

@Composable
fun CatalogoRoute(
    onNavigateToPerfil: () -> Unit,
) {
    val appContainer = (LocalContext.current.applicationContext as LevelUpApp).container
    val catalogoViewModel: CatalogoViewModel = viewModel(
        factory = ViewModelFactory(appContainer.productRepository)
    )
    val uiState by catalogoViewModel.uiState.collectAsState()

    CatalogoScreen(
        uiState = uiState,
        onCategorySelected = { category -> catalogoViewModel.selectCategory(category) },
        onNavigateToPerfil = onNavigateToPerfil
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    uiState: CatalogoUiState,
    onCategorySelected: (String?) -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Productos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.black),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onNavigateToPerfil) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Perfil",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        containerColor = colorResource(id = R.color.black)
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
                CategoryFilter(
                    categories = uiState.categories,
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = onCategorySelected
                )
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val filteredProducts = if (uiState.selectedCategory == null) {
                        uiState.products
                    } else {
                        uiState.products.filter { it.category == uiState.selectedCategory }
                    }
                    items(filteredProducts) { product ->
                        ProductCard(product = product)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilter(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Button(
                onClick = { onCategorySelected(null) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == null) colorResource(id = R.color.electric_blue) else colorResource(id = R.color.neon_green)
                )
            ) {
                Text("Todos", color = Color.Black)
            }
        }
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory) colorResource(id = R.color.electric_blue) else colorResource(id = R.color.neon_green)
                )
            ) {
                Text(category, color = Color.Black)
            }
        }
    }
}


@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_gray)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = NumberFormat.getCurrencyInstance(Locale("es", "CL")).format(product.price),
                color = colorResource(id = R.color.electric_blue),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoScreenPreview_Content() {
    val mockProducts = listOf(
        Product("JM001", "Catan", "Juegos de Mesa", "Un clásico juego de estrategia...", 29990.0, ""),
        Product("AC001", "Controlador Inalámbrico Xbox", "Accesorios", "Ofrece una experiencia de juego...", 59990.0, "")
    )
    val mockCategories = listOf("Juegos de Mesa", "Accesorios")
    CatalogoScreen(
        uiState = CatalogoUiState(
            products = mockProducts,
            categories = mockCategories,
            selectedCategory = "Juegos de Mesa"
        ),
        onCategorySelected = {},
        onNavigateToPerfil = {}
    )
}
