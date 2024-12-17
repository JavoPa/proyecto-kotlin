package com.example.proyecto_kotlin.ui.salud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_kotlin.databinding.FragmentSaludBinding

class SaludFragment : Fragment() {

    private var _binding: FragmentSaludBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SaludViewModel::class.java)

        _binding = FragmentSaludBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSalud
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}