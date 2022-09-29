package com.example.rulesofroad.database

import com.example.rulesofroad.model.Symbol

interface DatabaseService {
    fun saveSymbol(symbol: Symbol)
    fun editSymbol(symbol: Symbol)
    fun deleteSymbol(id: Int)
    fun getAllSymbols(): MutableList<Symbol>
}