package com.example.rulesofroad.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rulesofroad.model.Symbol

class SymbolDatabase(context: Context) : SQLiteOpenHelper(context, "sym.db", null, 2), // Database updated
    DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE symbol_table (sym_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, name TEXT NOT NULL, des_c TEXT NOT NULL, image BLOB NOT NULL, type TEXT NOT NULL)"
        db?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) = Unit

    override fun saveSymbol(symbol: Symbol) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", symbol.name)
        contentValues.put("des_c", symbol.desc)
        contentValues.put("image", symbol.image)
        contentValues.put("type", symbol.type)
        database.insert("symbol_table", null, contentValues)
        database.close()
    }

    override fun editSymbol(symbol: Symbol) {

    }

    override fun deleteSymbol(id: Int) {

    }

    override fun getAllSymbols(): MutableList<Symbol> {
        val database = this.readableDatabase
        val query = "SELECT * FROM symbol_table"
        val symbolList = mutableListOf<Symbol>()
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                symbolList.add(
                    Symbol(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3),
                        cursor.getString(4)
                    )
                )
            } while (cursor.moveToNext())
        }
        return symbolList
    }
}