package com.rezza.getplus.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rezza.getplus.database.table.GamesDB


class DatabaseManager(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "GamesTest.db"
    }

    var tables: ArrayList<MasterDB> = ArrayList()

    override fun onCreate(db: SQLiteDatabase?) {
        addTables()
        for (masterDB in tables) {
            db!!.execSQL("DROP TABLE IF EXISTS " + masterDB.tableName())
            db.execSQL(masterDB.getCreateTable())
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(p0);
    }

    fun insert(pTable: String, pContent: ContentValues): Boolean {
        val db = this.writableDatabase
        val x = db.insert(pTable, null, pContent)
        return x == 1L
    }

    fun update(pTable: String?, pContent: ContentValues?, where: String?): Int {
        val db = this.writableDatabase
        return db.update(pTable, pContent, where, null)
    }

    fun updateColumn(pTable: String, query: String, pWhere: String) {
        val db = this.writableDatabase
        db.execSQL("UPDATE $pTable SET $query WHERE $pWhere")
    }

    fun delete(pTable: String, where: String): Boolean {
        val db = this.writableDatabase
        db.execSQL("DELETE  FROM $pTable WHERE $where")
        //        db.delete(pTable,where,null);
        return true
    }
    fun delete(pTable: String): Boolean {
        val db = this.writableDatabase
        db.execSQL("DELETE  FROM $pTable")
        return true
    }

    private fun addTables() {
        tables.add(GamesDB())
    }


}