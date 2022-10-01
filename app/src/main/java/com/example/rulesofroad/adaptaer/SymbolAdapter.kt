package com.example.rulesofroad.adaptaer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rulesofroad.R
import com.example.rulesofroad.databinding.ItemLayoutBinding
import com.example.rulesofroad.model.Symbol
import com.example.rulesofroad.util.ItemClickListener
import com.example.rulesofroad.util.toBitmap

class SymbolAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<SymbolAdapter.SymbolViewHolder>() {

    var symbolList: MutableList<Symbol> = mutableListOf()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolViewHolder {
        context = parent.context
        return SymbolViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SymbolViewHolder, position: Int) {
        holder.bind(symbolList[position])
    }

    override fun getItemCount(): Int = symbolList.size

    inner class SymbolViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(symbol: Symbol) {
            binding.apply {
                imageView.setImageBitmap(symbol.image?.toBitmap())
                textTitle.text = symbol.desc
                textDelete.setOnClickListener {
                    itemClickListener.onDeleteClicked(symbol, adapterPosition)
                }
                textEdit.setOnClickListener {
                    itemClickListener.onEditClicked(symbol, adapterPosition)
                }
                itemView.setOnClickListener {
                    itemClickListener.onItemClicked(symbol)
                }
            }
        }
    }
}