package com.example.proyecto_kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_kotlin.ui.home.HomeViewModel

class DetalleMascotaActivity : AppCompatActivity() {

    private lateinit var mascotaViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_mascota)

        mascotaViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val mascotaId = intent.getIntExtra("MASCOTA_ID", -1)

        if (mascotaId != -1) {
            val mascota = mascotaViewModel.mascotas.value?.find { it.id == mascotaId }
            mascota?.let {
                findViewById<TextView>(R.id.textViewNombre).text = it.nombre
                findViewById<TextView>(R.id.textViewRaza).text = it.raza
                findViewById<TextView>(R.id.textViewEspecie).text = it.especie
                findViewById<TextView>(R.id.textViewFechaNacimiento).text = it.fechaNacimiento
                findViewById<TextView>(R.id.textViewPeso).text = it.peso.toString()
                findViewById<TextView>(R.id.textViewEdad).text = it.edad.toString()


            }
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            finish()
        }
    }
}