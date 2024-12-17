package com.example.proyecto_kotlin.ui.ficha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_kotlin.databinding.FragmentFichaBinding
import com.example.proyecto_kotlin.ui.ficha.FichaFragmentArgs
import com.example.proyecto_kotlin.ui.home.HomeViewModel

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

        // Inicializar el ViewModel
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        // Obtener el ID de la mascota usando SafeArgs
        val args = FichaFragmentArgs.fromBundle(requireArguments())
        val mascotaId = args.mascotaId

        if (mascotaId == -1) {
            // Cargar valores por defecto
            binding.textViewNombre.text = "Ninguna mascota seleccionada"
            binding.textViewRaza.text = ""
            binding.textViewEspecie.text = ""
            binding.textViewFechaNacimiento.text = ""
            binding.textViewPeso.text = ""
            binding.textViewEdad.text = ""
        } else {
            // Cargar detalles de la mascota seleccionada
            val mascota = homeViewModel.mascotas.value?.find { it.id == mascotaId }
            mascota?.let {
                binding.textViewNombre.text = it.nombre
                binding.textViewRaza.text = "Raza: ${it.raza}"
                binding.textViewEspecie.text = "Especie: ${it.especie}"
                binding.textViewFechaNacimiento.text = "Fecha de Nacimiento: ${it.fechaNacimiento}"
                binding.textViewPeso.text = "Peso: ${it.peso} kg"
                binding.textViewEdad.text = "Edad: ${calcularEdad(it.fechaNacimiento)} años"
            }
        }

        // Botón para volver a la lista
        binding.btnVolver.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Función para calcular la edad a partir de la fecha de nacimiento
    private fun calcularEdad(fechaNacimiento: String): Int {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val nacimiento = sdf.parse(fechaNacimiento)
        val hoy = java.util.Calendar.getInstance().time
        val diferencia = hoy.time - nacimiento.time
        return (diferencia / (1000L * 60 * 60 * 24 * 365)).toInt()
    }
}