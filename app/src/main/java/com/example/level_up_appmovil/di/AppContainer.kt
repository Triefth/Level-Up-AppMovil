package com.example.level_up_appmovil.di

import android.content.Context
import com.example.level_up_appmovil.data.api.repository.ProductRepository
import com.example.level_up_appmovil.data.repository.UserDataRepository

// Define la interfaz para el contenedor de dependencias de la aplicación.
interface AppContainer {
    val productRepository: ProductRepository
    val userDataRepository: UserDataRepository
}

// Implementación por defecto del contenedor de dependencias.
// Recibe el contexto de la aplicación para poder instanciar los repositorios.
class DefaultAppContainer(private val context: Context) : AppContainer {

    // Instancia perezosa del repositorio de productos.
    // Solo se crea una vez cuando se accede por primera vez.
    override val productRepository: ProductRepository by lazy {
        ProductRepository()
    }

    // Instancia perezosa del repositorio de datos de usuario.
    // Se le pasa el contexto para que pueda usar DataStore.
    override val userDataRepository: UserDataRepository by lazy {
        UserDataRepository(context)
    }
}
