package com.example.proyecto_kotlin.ui.ficha

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.proyecto_kotlin.databinding.FragmentFichaBinding
import com.example.proyecto_kotlin.ui.ficha.FichaFragmentArgs
import com.example.proyecto_kotlin.ui.home.HomeViewModel
import com.example.proyecto_kotlin.ui.salud.SaludViewModel

class FichaFragment : Fragment() {

    private var _binding: FragmentFichaBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFichaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val slideshowViewModel =
            ViewModelProvider(this).get(FichaViewModel::class.java)

        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        FichaFragmentArgs
        val args = FichaFragmentArgs.fromBundle(requireArguments())
        val mascotaId = args.mascotaId

        if (mascotaId == -1) {
            binding.textViewNombre.text = "Ninguna mascota seleccionada"
        } else {
            val mascota = homeViewModel.mascotas.value?.find { it.id == mascotaId }
            mascota?.let {
                binding.textViewNombre.text = it.nombre
                binding.textViewRaza.text = "${it.raza}"
                binding.textViewEspecie.text = "${it.especie}"
                binding.textViewFechaNacimiento.text = "${it.fechaNacimiento}"
                binding.textViewPeso.text = "${it.peso} kg"
                binding.textViewEdad.text = "${calcularEdad(it.fechaNacimiento)} años"

                val alergiasLayout = binding.linearLayoutAlergias
                alergiasLayout.removeAllViews()
                if (it.alergias.isNullOrEmpty()) {
                    val noAlergiasTextView = TextView(requireContext())
                    noAlergiasTextView.text = "Sin alergias conocidas"
                    noAlergiasTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    noAlergiasTextView.setTextColor(Color.parseColor("#757575"))
                    alergiasLayout.addView(noAlergiasTextView)
                } else {
                    it.alergias.forEach { alergia ->
                        val alergiaTextView = TextView(requireContext())
                        alergiaTextView.text = alergia
                        alergiaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                        alergiaTextView.setTextColor(Color.parseColor("#757575"))
                        alergiasLayout.addView(alergiaTextView)
                    }
                }

                val antecedentesLayout = binding.linearLayoutAntecedentes
                antecedentesLayout.removeAllViews()
                if (it.antecedentes.isNullOrEmpty()) {
                    val noAntecedentesTextView = TextView(requireContext())
                    noAntecedentesTextView.text = "Sin antecedentes médicos"
                    noAntecedentesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    noAntecedentesTextView.setTextColor(Color.parseColor("#757575"))
                    antecedentesLayout.addView(noAntecedentesTextView)
                } else {
                    it.antecedentes.forEach { antecedente ->
                        val antecedenteTextView = TextView(requireContext())
                        antecedenteTextView.text = antecedente
                        antecedenteTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                        antecedenteTextView.setTextColor(Color.parseColor("#757575"))
                        antecedentesLayout.addView(antecedenteTextView)
                    }
                }
            }
        }

        binding.btnVolver.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSalud.setOnClickListener {
            val action = FichaFragmentDirections.actionNavFichaToNavSalud(mascotaId)
            findNavController().navigate(action)
        }

        return root
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