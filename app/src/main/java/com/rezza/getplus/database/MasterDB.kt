package com.rezza.getplus.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log


abstract class MasterDB {

    private var TAG = "MasterDB"

    protected abstract fun createContentValues(): ContentValues?
    abstract fun getCreateTable(): String?
    abstract fun tableName(): String?
    protected abstract fun build(res: Cursor?): MasterDB?
    protected abstract fun buildSingle(res: Cursor?)

    open fun clearData(context: Context?) {
        try {
            Log.d(TAG, "Cleat Data " + tableName())
            val pDB = DatabaseManager(context!!)
            pDB.delete(tableName()!!)
            pDB.close()
        } catch (e: Exception) {
            Log.d(TAG, e.message!!)
        }
    }

    open fun insert(context: Context?): Boolean {
        var x = false
        try {
            val pDB = DatabaseManager(context!!)
            x = pDB.insert(tableName()!!, createContentValues()!!)
            pDB.close()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.message!!)
        }
        return x
    }

    open fun insertAsClear(context: Context?): Boolean {
        var x = false
        clearData(context)
        try {
            val pDB = DatabaseManager(context!!)
            x = pDB.insert(tableName()!!, createContentValues()!!)
            pDB.close()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.message!!)
        }
        return x
    }


    open fun delete(context: Context?, where: String?) {
        try {
            val pDB = DatabaseManager(context!!)
            pDB.delete(tableName()!!, where!!)
            pDB.close()
        } catch (e: java.lang.Exception) {
            Log.d(TAG, e.message!!)
        }
    }
}