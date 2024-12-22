package com.example.proyecto_kotlin.ui.vacunas

import android.app.AlertDialog
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
import SharedMascotaViewModel

class VacunasFragment : Fragment() {

    private var _binding: FragmentVacunasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VacunasViewModel
    private lateinit var adapter: VacunasAdapter
    private var mascota: Mascota? = null
    private lateinit var sharedViewModel: SharedMascotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacunasBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(VacunasViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedMascotaViewModel::class.java]

        val root: View = binding.root

        // Observar la mascota seleccionada en el SharedViewModel
        sharedViewModel.mascotaSeleccionada.observe(viewLifecycleOwner) { mascotaSeleccionada ->
            mascota = mascotaSeleccionada // Sincronizamos la variable local

            if (mascota == null) {
                binding.textVacunas.text = "Ninguna mascota seleccionada"
                binding.recyclerViewVacunas.visibility = View.GONE
            } else {
                binding.textVacunas.visibility = View.GONE
                binding.textVacunasTitulo.text = "Vacunas y Antiparasitarios de ${mascota?.nombre}"
                binding.recyclerViewVacunas.visibility = View.VISIBLE

                // Actualizar la lista de vacunas según la mascota seleccionada
                viewModel.vacunas.observe(viewLifecycleOwner) { vacunas ->
                    val vacunasFiltradas = vacunas.filter { it.mascotaId == mascota?.id }
                    if (vacunasFiltradas.isEmpty()) {
                        binding.recyclerViewVacunas.visibility = View.GONE
                        binding.textVacunas.text = "La mascota no tiene vacunas."
                        binding.textVacunas.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewVacunas.visibility = View.VISIBLE
                        binding.textVacunas.visibility = View.GONE
                        adapter.actualizarDatos(vacunasFiltradas)
                    }
                }
            }
        }

        // Configurar el adaptador
        adapter = VacunasAdapter(
            emptyList(),
            onEditarClick = { vacuna -> editarVacuna(vacuna) },
            onEliminarClick = { vacuna -> eliminarVacuna(vacuna) }
        )
        binding.recyclerViewVacunas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewVacunas.adapter = adapter

        // Botón para agregar nueva vacuna
        binding.buttonAgregarVacuna.setOnClickListener {
            mascota?.id?.let { mascotaId ->
                val dialog = AgregarVacunaDialog(
                    mascotaId = mascotaId,
                    onVacunaAgregada = { vacuna ->
                        viewModel.agregarVacuna(vacuna)
                    }
                )
                dialog.show(parentFragmentManager, "AgregarVacunaDialog")
            } ?: run {
                Toast.makeText(requireContext(), "No hay ninguna mascota seleccionada", Toast.LENGTH_SHORT).show()
            }
        }

        return root
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
}
