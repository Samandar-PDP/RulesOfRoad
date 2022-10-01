package com.example.rulesofroad.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.rulesofroad.MainActivity
import com.example.rulesofroad.R
import com.example.rulesofroad.database.SymbolDatabase
import com.example.rulesofroad.databinding.FragmentDetailBinding
import com.example.rulesofroad.model.Symbol
import com.example.rulesofroad.util.toBitmap
import com.example.rulesofroad.util.toast

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var symbol: Symbol? = null
    private val symbolDatabase by lazy { SymbolDatabase(requireContext()) }
    private var isLiked: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        symbol = arguments?.getSerializable("symbol") as? Symbol
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symbol?.let {
            binding.apply {
                imageView.setImageBitmap(it.image?.toBitmap())
                textView.text = it.name
                textView2.text = it.desc
                checkBox.isChecked = it.isLiked == 1
            }
        }
        binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.checkBox.setButtonDrawable(R.drawable.ic_liked)
                symbolDatabase.savFavSymbol(
                    Symbol(
                        symbol?.name,
                        symbol?.desc,
                        symbol?.image,
                        symbol?.type,
                       1
                    )
                )
                toast("Saved to favorites")
            } else {
                toast("Deleted from favorites")
                symbolDatabase.deleteFavSymbol(symbol?.id!!)
                binding.checkBox.setButtonDrawable(R.drawable.ic_disliked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}