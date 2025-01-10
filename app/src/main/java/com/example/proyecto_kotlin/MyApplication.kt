package com.example.proyecto_kotlin

import android.app.Application
import com.example.proyecto_kotlin.ui.vacunas.AppDatabase

class MyApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}