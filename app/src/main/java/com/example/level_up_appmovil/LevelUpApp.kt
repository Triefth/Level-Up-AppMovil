package com.example.level_up_appmovil

import android.app.Application
import com.example.level_up_appmovil.di.AppContainer
import com.example.level_up_appmovil.di.DefaultAppContainer

class LevelUpApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Pasamos el contexto de la aplicación al contenedor de dependencias.
        container = DefaultAppContainer(this)
    }
}
