package com.example.rulesofroad.util

import com.example.rulesofroad.model.Symbol

interface ItemClickListener {
    fun onItemClicked(symbol: Symbol)
    fun onDeleteClicked(symbol: Symbol, pos: Int)
    fun onEditClicked(symbol: Symbol, pos: Int)
    fun onLikeClicked(symbol: Symbol)
}