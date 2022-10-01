package com.example.rulesofroad.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rulesofroad.R
import com.example.rulesofroad.database.SymbolDatabase
import com.example.rulesofroad.databinding.FragmentFavoriteBinding
import com.example.rulesofroad.util.toBitmap

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val symbolDatabase by lazy { SymbolDatabase(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val symbol = symbolDatabase.getFavSymbols()[0]
        binding.apply {
            imageView.setImageBitmap(symbol.image?.toBitmap())
            textView.text = symbol.name
        }
    }
}