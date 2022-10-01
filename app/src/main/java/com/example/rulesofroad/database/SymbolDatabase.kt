package com.example.rulesofroad.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rulesofroad.model.Symbol

class SymbolDatabase(context: Context) :
    SQLiteOpenHelper(context, "sym.db", null, 3), // Database updated
    DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE symbol_table (sym_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, name TEXT NOT NULL, des_c TEXT NOT NULL, image BLOB NOT NULL, type TEXT NOT NULL, isLiked INTEGER NOT NULL)"
        db?.execSQL(query)
        val favoriteQuery =
            "CREATE TABLE favorite_table (fav_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, fav_name TEXT NOT NULL, fav_desc TEXT NOT NULL, fav_image BLOB NOT NULL, fav_type TEXT NOT NULL, favIsLiked INTEGER NOT NULL)"
        db?.execSQL(favoriteQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) = Unit

    override fun saveSymbol(symbol: Symbol) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", symbol.name)
        contentValues.put("des_c", symbol.desc)
        contentValues.put("image", symbol.image)
        contentValues.put("type", symbol.type)
        contentValues.put("isLiked", symbol.type)
        database.insert("symbol_table", null, contentValues)
        database.close()
    }

    override fun editSymbol(symbol: Symbol) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("sym_id", symbol.id)
        contentValues.put("name", symbol.name)
        contentValues.put("des_c", symbol.desc)
        contentValues.put("image", symbol.image)
        contentValues.put("type", symbol.type)
        database.update(
            "symbol_table",
            contentValues,
            "sym_id = ?",
            arrayOf(symbol.id.toString())
        )
        database.close()
    }

    override fun deleteSymbol(id: Int) {
        val database = this.writableDatabase
        database.delete("symbol_table", "sym_id = ?", arrayOf(id.toString()))
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
                        cursor.getString(4),
                        cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        return symbolList
    }

    override fun savFavSymbol(symbol: Symbol) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("fav_name", symbol.name)
        contentValues.put("fav_desc", symbol.desc)
        contentValues.put("fav_image", symbol.image)
        contentValues.put("fav_type", symbol.type)
        contentValues.put("favIsLiked", symbol.type)
        database.insert("favorite_table", null, contentValues)
        database.close()
    }

    override fun getFavSymbols(): MutableList<Symbol> {
        val database = this.readableDatabase
        val query = "SELECT * FROM favorite_table"
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
                        cursor.getString(4),
                        cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        return symbolList
    }

    override fun deleteFavSymbol(id: Int) {
        val database = this.writableDatabase
        database.delete("favorite_table", "fav_id = ?", arrayOf(id.toString()))
    }
}