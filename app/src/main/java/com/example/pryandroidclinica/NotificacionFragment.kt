package com.example.pryandroidclinica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pryandroidclinica.R
import com.example.pryandroidclinica.databinding.FragmentHomeBinding

class NotificacionFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_notificacion, container, false)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}