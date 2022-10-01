package com.example.rulesofroad.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rulesofroad.MainActivity
import com.example.rulesofroad.R
import com.example.rulesofroad.adaptaer.SymbolAdapter
import com.example.rulesofroad.database.SymbolDatabase
import com.example.rulesofroad.databinding.FragmentHomeBinding
import com.example.rulesofroad.model.Symbol
import com.example.rulesofroad.util.ItemClickListener

class HomeFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val symbolDatabase by lazy { SymbolDatabase(requireContext()) }
    private lateinit var symbolAdapter: SymbolAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        symbolAdapter = SymbolAdapter(this)
        symbolAdapter.symbolList = symbolDatabase.getAllSymbols()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = symbolAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as MainActivity).menuInflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addMenu) {
            findNavController().navigate(R.id.action_home_fragment_to_save_fragment)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(symbol: Symbol) {
        val bundle = bundleOf("symbol" to symbol)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onDeleteClicked(symbol: Symbol, pos: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete")
            setMessage("Do you want to delete ${symbol.name}?")
            setPositiveButton("Yes") { di, _ ->
                symbolDatabase.deleteSymbol(symbol.id!!)
                symbolAdapter.notifyItemRemoved(pos)
                symbolAdapter.symbolList.removeAt(pos)
                di.dismiss()
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onEditClicked(symbol: Symbol, pos: Int) {
        val bundle = bundleOf("symbol" to symbol)
        findNavController().navigate(R.id.action_homeFragment_to_editFragment, bundle)
    }

    override fun onLikeClicked(symbol: Symbol) {
        // onLikeButtonClicked, save to favorite database
    }
}