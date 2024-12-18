package com.example.proyecto_kotlin.ui.vacunas

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VacunasFragment : Fragment() {

    private lateinit var vacunasViewModel: VacunasViewModel
    private lateinit var adapter: VacunasAdapter
    private var mascotaId: String? = null // ID de la mascota seleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtener el ID de la mascota desde los argumentos
        arguments?.let {
            mascotaId = it.getString("MASCOTA_ID")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vacunas, container, false)

        vacunasViewModel = ViewModelProvider(this)[VacunasViewModel::class.java]

        // Configurar RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewVacunas)
        adapter = VacunasAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observar las vacunas asociadas a la mascota
        mascotaId?.let { id ->
            vacunasViewModel.obtenerVacunasPorMascota(id).observe(viewLifecycleOwner) { vacunas ->
                adapter.updateData(vacunas)
            }
        }

        // FAB para agregar nueva vacuna
        val fabAgregarVacuna: FloatingActionButton = view.findViewById(R.id.fabAgregarVacuna)
        fabAgregarVacuna.setOnClickListener {
            mostrarDialogoAgregarVacuna()
        }

        return view
    }

    private fun mostrarDialogoAgregarVacuna() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_agregar_vacuna, null)

        val etTipoVacuna: EditText = dialogView.findViewById(R.id.etTipoVacuna)
        val etFechaAplicacion: EditText = dialogView.findViewById(R.id.etFechaAplicacion)
        val etProximaDosis: EditText = dialogView.findViewById(R.id.etProximaDosis)
        val btnGuardar: Button = dialogView.findViewById(R.id.btnGuardarVacuna)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Agregar Nueva Vacuna")
            .setView(dialogView)
            .create()

        btnGuardar.setOnClickListener {
            val tipo = etTipoVacuna.text.toString()
            val fechaAplicacion = etFechaAplicacion.text.toString()
            val proximaDosis = etProximaDosis.text.toString()

            if (tipo.isNotEmpty() && fechaAplicacion.isNotEmpty() && proximaDosis.isNotEmpty()) {
                mascotaId?.let { id ->
                    val nuevaVacuna = Vacuna(id, tipo, fechaAplicacion, proximaDosis)
                    vacunasViewModel.agregarVacuna(nuevaVacuna)
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Vacuna agregada correctamente", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
