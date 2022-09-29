package com.example.rulesofroad.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rulesofroad.R
import com.example.rulesofroad.database.SymbolDatabase
import com.example.rulesofroad.databinding.FragmentSaveBinding
import com.example.rulesofroad.model.Symbol
import com.example.rulesofroad.util.toByteArray
import com.example.rulesofroad.util.toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private val symbolDatabase by lazy { SymbolDatabase(requireContext()) }
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }


    private fun initViews(view: View) {

        val array = arrayOf(
            "Ogohlantiruvchi",
            "Imtiyozli",
            "Ta'qiqlovchi",
            "Buyuruvchi",
            "Servis belgilari"
        )
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, array)
        binding.autoComplete.setAdapter(arrayAdapter)
        binding.autoComplete.setOnItemClickListener { adapterView, view, i, l ->
            type = adapterView.getItemAtPosition(i).toString()
        }

        binding.imageView.setOnClickListener {
            pickImageFromNewGallery.launch("image/*")
        }
        binding.saveBtn.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val desc = binding.editDesc.text.toString().trim()
            val isImageAvailable = binding.imageView.drawable != null
            if (name.isNotBlank() && desc.isNotBlank() && isImageAvailable && ::type.isInitialized) {

                symbolDatabase.saveSymbol(Symbol(name, desc, binding.imageView.toByteArray(), type))
                toast("Saqlandi ")
            } else {
                toast("Ma'lumotlarni to'ldiring!")
            }
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
            binding.imageView.setImageURI(Uri.parse(file.absolutePath))
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}