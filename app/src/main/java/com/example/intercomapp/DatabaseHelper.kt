package com.example.intercomapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_name,factory, version){
    companion object{
        private val DB_name = "userDB"
        internal val factory = null
        private val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE USERS(name TEXT, address TEXT, pin TEXT)"
        db?.execSQL(createTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS user"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun deleteAccount(name: String, address: String, pin: String):Boolean{
        val qry = "DELETE FROM USERS WHERE name = $name AND address = $address AND pin = $pin"
        val db = this.writableDatabase
        var result = false
        try {
            val cursor = db.execSQL(qry)
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG,"Error Deleting")
        }
        db.close()
        return result
    }
}