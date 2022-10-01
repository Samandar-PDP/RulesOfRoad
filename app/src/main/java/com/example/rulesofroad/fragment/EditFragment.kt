package com.example.rulesofroad.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.rulesofroad.R
import com.example.rulesofroad.database.SymbolDatabase
import com.example.rulesofroad.databinding.FragmentEditBinding
import com.example.rulesofroad.model.Symbol
import com.example.rulesofroad.util.Constants.array
import com.example.rulesofroad.util.toBitmap
import com.example.rulesofroad.util.toByteArray
import com.example.rulesofroad.util.toast
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private var symbol: Symbol? = null
    private val symbolDatabase by lazy { SymbolDatabase(requireContext()) }
    private lateinit var type: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        symbol = arguments?.getSerializable("symbol") as? Symbol
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symbol?.let {
            binding.apply {
                imageView.setImageBitmap(it.image?.toBitmap())
                editDesc.setText(it.desc)
                editName.setText(it.name)
                autoComplete.setText(it.type)
            }
        }
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, array)
        binding.autoComplete.setAdapter(arrayAdapter)
        binding.autoComplete.setOnItemClickListener { adapterView, view, i, l ->
            type = adapterView.getItemAtPosition(i).toString()
        }
        binding.editBtn.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val desc = binding.editDesc.text.toString().trim()
            val isImageAvailable = binding.imageView.drawable != null
            if (name.isNotBlank() && desc.isNotBlank() && isImageAvailable && ::type.isInitialized) {
                symbolDatabase.editSymbol(
                    Symbol(
                        symbol?.id,
                        name,
                        desc,
                        binding.imageView.toByteArray(),
                        type
                    )
                )
                toast("Edited")
                findNavController().popBackStack()
            } else {
                toast("Enter data!")
            }
        }
        binding.imageView.setOnClickListener {
            // pickImageFromNewGallery.launch("image/*")
        }
    }

    private val pickImageFromNewGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(Date())
            val contentResolver = requireContext().contentResolver?.openInputStream(uri)
            val file = File(requireContext().filesDir, "$date.jpg")
            val fileOutputStream = FileOutputStream(file)
            contentResolver?.copyTo(fileOutputStream)
            contentResolver?.close()
            fileOutputStream.close()
            binding.imageView.setImageURI(null)
            binding.imageView.setImageURI(Uri.parse(file.absolutePath))
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}