package com.example.proyecto_kotlin.ui.ficha

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R
import com.example.proyecto_kotlin.databinding.FragmentFichaBinding
import com.example.proyecto_kotlin.ui.ficha.FichaFragmentArgs
import com.example.proyecto_kotlin.ui.home.HomeViewModel
import com.example.proyecto_kotlin.ui.salud.SaludViewModel

class FichaFragment : Fragment() {

    private var _binding: FragmentFichaBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private var mascota: Mascota? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFichaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        FichaFragmentArgs
        val args = FichaFragmentArgs.fromBundle(requireArguments())
        val mascotaId = args.mascotaId

        if (mascotaId == -1) {
            binding.textViewNombre.text = "Ninguna mascota seleccionada"
        } else {
            mascota = homeViewModel.mascotas.value?.find { it.id == mascotaId }
            mascota?.let {
                binding.textViewNombre.text = it.nombre
                binding.textViewRaza.text = it.raza
                binding.textViewEspecie.text = it.especie
                binding.textViewFechaNacimiento.text = it.fechaNacimiento
                binding.textViewPeso.text = "${it.peso} kg"
                binding.textViewEdad.text = "${calcularEdad(it.fechaNacimiento)} años"

                actualizarAlergias(it.alergias)
                actualizarAntecedentes(it.antecedentes)
            }
        }

        binding.btnAgregarAlergia.setOnClickListener {
            mostrarDialogoAgregarAlergia()
        }

        binding.btnAgregarAntecedente.setOnClickListener {
            mostrarDialogoAgregarAntecedente()
        }

        binding.btnCambiarPeso.setOnClickListener {
            mostrarDialogoCambiarPeso()
        }
        binding.btnVolver.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSalud.setOnClickListener {
            val action = FichaFragmentDirections.actionNavFichaToNavSalud(mascotaId)
            findNavController().navigate(action)
        }

        binding.btnVacunas.setOnClickListener {
            val action = FichaFragmentDirections.actionNavFichaToNavVacunas(mascotaId)
            findNavController().navigate(action)
        }

        return root
    }

    private fun actualizarAlergias(alergias: MutableList<String>) {
        val alergiasLayout = binding.linearLayoutAlergias
        alergiasLayout.removeAllViews()

        if (alergias.isEmpty()) {
            val noAlergiasTextView = TextView(requireContext())
            noAlergiasTextView.text = "Sin alergias conocidas"
            noAlergiasTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            noAlergiasTextView.setTextColor(Color.parseColor("#757575"))
            alergiasLayout.addView(noAlergiasTextView)
        } else {
            alergias.forEachIndexed { index, alergia ->
                val alergiaTextView = TextView(requireContext())
                alergiaTextView.text = alergia
                alergiaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                alergiaTextView.setTextColor(Color.parseColor("#757575"))
                alergiaTextView.setOnClickListener {
                    mostrarDialogoEliminarAlergia(index)
                }
                alergiasLayout.addView(alergiaTextView)
            }
        }
    }

    private fun actualizarAntecedentes(antecedentes: MutableList<String>) {
        val antecedentesLayout = binding.linearLayoutAntecedentes
        antecedentesLayout.removeAllViews()

        if (antecedentes.isEmpty()) {
            val noAntecedentesTextView = TextView(requireContext())
            noAntecedentesTextView.text = "No se presentan antecedentes"
            noAntecedentesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            noAntecedentesTextView.setTextColor(Color.parseColor("#757575"))
            antecedentesLayout.addView(noAntecedentesTextView)
        } else {
            antecedentes.forEachIndexed { index, antecedente ->
                val antecedentesTextView = TextView(requireContext())
                antecedentesTextView.text = antecedente
                antecedentesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                antecedentesTextView.setTextColor(Color.parseColor("#757575"))
                antecedentesTextView.setOnClickListener {
                    mostrarDialogoEliminarAntecedente(index)
                }
                antecedentesLayout.addView(antecedentesTextView)
            }
        }
    }

    private fun mostrarDialogoAgregarAlergia() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_agregar_elemento, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val tvHeaderDialog = dialogView.findViewById<TextView>(R.id.tvHeaderDialog)
        val etInputDialog = dialogView.findViewById<EditText>(R.id.etInputDialog)
        val btnConfirmDialog = dialogView.findViewById<Button>(R.id.btnConfirmDialog)
        val btnCancelDialog = dialogView.findViewById<Button>(R.id.btnCancelDialog)

        tvHeaderDialog.text = "Agregar Alergia"
        etInputDialog.hint = "Ingresa una nueva alergia"

        btnConfirmDialog.setOnClickListener {
            val nuevaAlergia = etInputDialog.text.toString().trim()
            if (nuevaAlergia.isNotEmpty()) {
                mascota?.let {
                    it.alergias.add(nuevaAlergia)
                    actualizarAlergias(it.alergias)
                }
                dialog.dismiss()
            }
        }

        btnCancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarDialogoAgregarAntecedente() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_agregar_elemento, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val tvHeaderDialog = dialogView.findViewById<TextView>(R.id.tvHeaderDialog)
        val etInputDialog = dialogView.findViewById<EditText>(R.id.etInputDialog)
        val btnConfirmDialog = dialogView.findViewById<Button>(R.id.btnConfirmDialog)
        val btnCancelDialog = dialogView.findViewById<Button>(R.id.btnCancelDialog)

        tvHeaderDialog.text = "Agregar Antecedente"
        etInputDialog.hint = "Ingresa un nuevo antecedente"

        btnConfirmDialog.setOnClickListener {
            val nuevoAntecedente = etInputDialog.text.toString().trim()
            if (nuevoAntecedente.isNotEmpty()) {
                mascota?.let {
                    it.antecedentes.add(nuevoAntecedente)
                    actualizarAntecedentes(it.antecedentes)
                }
                dialog.dismiss()
            }
        }

        btnCancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun mostrarDialogoEliminarAlergia(index: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar Alergia")
        builder.setMessage("¿Seguro que deseas eliminar esta alergia?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            mascota?.let {
                it.alergias.removeAt(index)
                actualizarAlergias(it.alergias)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun mostrarDialogoEliminarAntecedente(index: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar Antecedente")
        builder.setMessage("¿Seguro que deseas eliminar el antecedente?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            mascota?.let {
                it.antecedentes.removeAt(index)
                actualizarAntecedentes(it.antecedentes)
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun mostrarDialogoCambiarPeso() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_agregar_elemento, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val tvHeaderDialog = dialogView.findViewById<TextView>(R.id.tvHeaderDialog)
        val etInputDialog = dialogView.findViewById<EditText>(R.id.etInputDialog)
        val btnConfirmDialog = dialogView.findViewById<Button>(R.id.btnConfirmDialog)
        val btnCancelDialog = dialogView.findViewById<Button>(R.id.btnCancelDialog)

        tvHeaderDialog.text = "Cambiar Peso"
        etInputDialog.hint = "Ingresa el nuevo peso (kg)"
        etInputDialog.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        btnConfirmDialog.setOnClickListener {
            val nuevoPesoStr = etInputDialog.text.toString().trim()
            if (nuevoPesoStr.isNotEmpty()) {
                val nuevoPeso = nuevoPesoStr.toDoubleOrNull()
                if (nuevoPeso != null && nuevoPeso > 0) {
                    mascota?.let {
                        it.peso = nuevoPeso
                        binding.textViewPeso.text = "${it.peso} kg"
                    }
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Por favor, ingresa un peso válido.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "El campo no puede estar vacío.", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calcularEdad(fechaNacimiento: String): Int {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val nacimiento = sdf.parse(fechaNacimiento)
        val hoy = java.util.Calendar.getInstance().time
        val diferencia = hoy.time - nacimiento.time
        return (diferencia / (1000L * 60 * 60 * 24 * 365)).toInt()
    }
}