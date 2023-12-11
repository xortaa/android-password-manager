package com.example.passwordmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "PasswordData", null, 1)  {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table PasswordDataList (appName TEXT, username TEXT, password TEXT, category TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists PasswordDataList")
    }

    fun savePasswordData(appName:String, username: String, password: String, category: String): Boolean{
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("appName", appName)
        cv.put("username", username)
        cv.put("password", password)
        cv.put("category", category)
        val result = p0.insert("PasswordDataList", null, cv)
        return result != -1L
    }

    fun getText(): Cursor? {
        val p0 = this.writableDatabase
        val cursor = p0.rawQuery("SELECT * FROM PasswordDataList", null)
        return cursor
    }

    fun deletePassword(appName:String, username: String):Int {
        val p0 = this.writableDatabase
        val whereClause = "appName = ? AND username = ?"
        val whereArgs = arrayOf(appName, username)

        return p0.delete("PasswordDataList", whereClause, whereArgs)
    }

    fun updatePassword(appName:String, username:String, newPassword:String, newCategory:String):Int {
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("password", newPassword)
        cv.put("category", newCategory)

        val whereClause = "appName = ? AND username = ?"
        val whereArgs = arrayOf(appName, username)

        return p0.update("PasswordDataList", cv, whereClause, whereArgs)
    }

}