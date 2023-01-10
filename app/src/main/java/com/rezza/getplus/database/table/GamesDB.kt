package com.rezza.getplus.database.table

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.rezza.getplus.database.DatabaseManager
import com.rezza.getplus.database.MasterDB


class GamesDB : MasterDB() {

    val TAG = "GamesDB"
    val TABLE_NAME = "GAMES"

    private val COLUMN_ID = "_id"
    private val COLUMN_TITLE = "_title"
    private val COLUMN_RELEASE = "_release"
    private val COLUMN_RATTING = "_ratting"
    private val COLUMN_IMAGE = "_image"

    var id : Int = 0
    var title : String = ""
    var release : String = ""
    var image : String = ""
    var ratting : Double = 0.0

    override fun createContentValues(): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, id)
        contentValues.put(COLUMN_TITLE, title)
        contentValues.put(COLUMN_RELEASE, release)
        contentValues.put(COLUMN_IMAGE, image)
        contentValues.put(COLUMN_RATTING, ratting)
        return contentValues
    }

    override fun getCreateTable(): String {
        val create = ("create table " + TABLE_NAME + " "
                + "(" +
                " " + COLUMN_ID + " integer default 0," +
                " " + COLUMN_TITLE + " varchar(100) NULL," +
                " " + COLUMN_RELEASE + " varchar(20) NULL," +
                " " + COLUMN_RATTING + " varchar(20) NULL," +
                " " + COLUMN_IMAGE + " varchar(200) NULL" +
                "  )")
        Log.d(TAG, create)
        return create
    }

    override fun tableName(): String {
        return TABLE_NAME
    }


    @SuppressLint("Range")
    override fun build(res: Cursor?): GamesDB {
        val games = GamesDB()
        games.id        =  res!!.getInt(res.getColumnIndex(COLUMN_ID))
        games.title     =  res.getString(res.getColumnIndex(COLUMN_TITLE))
        games.release   =  res.getString(res.getColumnIndex(COLUMN_RELEASE))
        games.ratting   =  res.getString(res.getColumnIndex(COLUMN_RATTING)).toDouble()
        games.image     =  res.getString(res.getColumnIndex(COLUMN_IMAGE))
        return games
    }

    @SuppressLint("Range")
    override fun buildSingle(res: Cursor?) {
        id        =  res!!.getInt(res.getColumnIndex(COLUMN_ID))
        title     =  res.getString(res.getColumnIndex(COLUMN_TITLE))
        release   =  res.getString(res.getColumnIndex(COLUMN_RELEASE))
        ratting   =  res.getString(res.getColumnIndex(COLUMN_RATTING)).toDouble()
        image     =  res.getString(res.getColumnIndex(COLUMN_IMAGE))
    }

    fun favorite(context : Context){
        Log.d(TAG, "Insert $id = $title")
        insert(context)
    }
    fun unFavorite(context : Context){
        Log.d(TAG, "Delete $id = $title")
        delete(context, "$COLUMN_ID=$id")
    }

    fun getAllData(context: Context?): ArrayList<GamesDB>? {
        val data: ArrayList<GamesDB> = ArrayList()
        val pDB = DatabaseManager(context!!)
        val db = pDB.readableDatabase
        val res = db.rawQuery("select *  from $TABLE_NAME", null)
        try {
            while (res.moveToNext()) {
                data.add(build(res))
            }
            pDB.close()
        } catch (e: Exception) {
            Log.d(TAG, e.message!!)
        } finally {
            res.close()
            pDB.close()
        }
        Log.d(TAG, "DATA " + data.size)
        return data
    }

    fun getData(context: Context?, pId : Int){
        val pDB = DatabaseManager(context!!)
        val db = pDB.readableDatabase
        val res = db.rawQuery("select *  from $TABLE_NAME where $COLUMN_ID=$pId", null)
        try {
            while (res.moveToNext()) {
               buildSingle(res)
            }
            pDB.close()
        } catch (e: Exception) {
            Log.d(TAG, e.message!!)
        } finally {
            res.close()
            pDB.close()
        }

    }

}