package com.example.proyecto_kotlin.ui.vacunas

import SharedMascotaViewModel
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R
import com.example.proyecto_kotlin.databinding.FragmentVacunasBinding
import com.example.proyecto_kotlin.ui.home.HomeViewModel

class VacunasFragment : Fragment() {

    private var _binding: FragmentVacunasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VacunasViewModel
    private lateinit var adapter: VacunasAdapter
    private var mascota: Mascota? = null
    private lateinit var sharedViewModel : SharedMascotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacunasBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(VacunasViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedMascotaViewModel::class.java]

        val root: View = binding.root

        // Configurar el adaptador
        adapter = VacunasAdapter(
            emptyList(),
            onEditarClick = { vacuna -> editarVacuna(vacuna) },
            onEliminarClick = { vacuna -> eliminarVacuna(vacuna) }
        )
        binding.recyclerViewVacunas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewVacunas.adapter = adapter

        sharedViewModel.mascotaSeleccionada.observe(viewLifecycleOwner){ mascota ->
            if (mascota == null ){
                binding.textVacunasTitulo.text = "Ninguna mascota seleccionada"
                binding.recyclerViewVacunas.visibility = View.GONE
            } else {
                binding.textVacunasTitulo.text = "Vacunas y Antiparasitarios de ${mascota.nombre}"
                cargarVacunas(mascota)
            }

        }

        // Botón para agregar nueva vacuna
        binding.buttonAgregarVacuna.setOnClickListener {
            sharedViewModel.mascotaSeleccionada.value?.let { mascota ->
                val dialog = AgregarVacunaDialog(
                    mascotaId = mascota.id,
                    onVacunaAgregada = { nuevaVacuna ->
                        viewModel.agregarVacuna(nuevaVacuna)
                    }
                )
                dialog.show(parentFragmentManager, "AgregarVacunaDialog")
            } ?: Toast.makeText(
                requireContext(),
                "Selecciona una mascota primero",
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }

    private fun cargarVacunas(mascota: Mascota){
        viewModel.vacunas.observe(viewLifecycleOwner){ vacunas ->
            val vacunasFiltradas = vacunas.filter { it.mascotaId == mascota.id }

            if (vacunasFiltradas.isEmpty()){
                binding.recyclerViewVacunas.visibility = View.GONE
                binding.textVacunas.text = "No hay vacunas registradas para ${mascota.nombre}"
                binding.textVacunas.visibility = View.VISIBLE
            } else {
                binding.textVacunas.visibility = View.GONE
                binding.recyclerViewVacunas.visibility = View.VISIBLE
                adapter.actualizarDatos(vacunasFiltradas)
            }
        }
    }

    private fun agregarVacuna() {
        // Inflar el diseño del diálogo existente
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_agregar_elemento, null)

        // Configurar el diálogo
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Configurar elementos del diálogo
        val tvHeader = dialogView.findViewById<TextView>(R.id.tvHeaderDialog)
        val etInput = dialogView.findViewById<EditText>(R.id.etInputDialog)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirmDialog)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancelDialog)

        // Configurar el encabezado del diálogo
        tvHeader.text = "Agregar Vacuna"

        // Configurar comportamiento del botón "Confirmar"
        btnConfirm.setOnClickListener {
            val vacunaNombre = etInput.text.toString()

            if (vacunaNombre.isNotEmpty()) {
                val mascotaId = obtenerMascotaSeleccionada()
                val nuevaVacuna = Vacuna(
                    id = (viewModel.vacunas.value?.size ?: 0) + 1,
                    nombre = vacunaNombre,
                    fechaAplicacion = "2023-01-01", // Valores predeterminados o dinámicos según necesites
                    proximaDosis = "2024-01-01",
                    mascotaId = mascotaId // Asigna el ID de la mascota seleccionada
                )
                viewModel.agregarVacuna(nuevaVacuna)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Por favor, ingresa un nombre válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar comportamiento del botón "Cancelar"
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Mostrar el diálogo
        dialog.show()
    }

    private fun editarVacuna(vacuna: Vacuna) {
        // Inflar el diseño del diálogo existente
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_agregar_elemento, null)

        // Configurar el diálogo
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Configurar elementos del diálogo
        val tvHeader = dialogView.findViewById<TextView>(R.id.tvHeaderDialog)
        val etInput = dialogView.findViewById<EditText>(R.id.etInputDialog)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirmDialog)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancelDialog)

        // Configurar encabezado y datos iniciales
        tvHeader.text = "Editar Vacuna"
        etInput.setText(vacuna.nombre)

        // Configurar comportamiento del botón "Confirmar"
        btnConfirm.setOnClickListener {
            val nuevoNombre = etInput.text.toString()

            if (nuevoNombre.isNotEmpty()) {
                val vacunaEditada = vacuna.copy(nombre = nuevoNombre)
                viewModel.editarVacuna(vacunaEditada)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Por favor, ingresa un nombre válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar comportamiento del botón "Cancelar"
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Mostrar el diálogo
        dialog.show()
    }


    private fun eliminarVacuna(vacuna: Vacuna) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar Vacuna")
        builder.setMessage("¿Estás seguro que deseas eliminar esta vacuna?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            viewModel.eliminarVacuna(vacuna)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerMascotaSeleccionada(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("MASCOTA_ID", -1)
    }
}
