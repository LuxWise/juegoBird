package com.example.juego

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class ScoreDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "scores.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_SCORES = "scores"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_SCORES ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_SCORE INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCORES")
        onCreate(db)
    }

    fun addScore(score: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SCORE, score)
        }
        db.insert(TABLE_SCORES, null, values)
        db.close()
    }

    fun getHighScore(): Int {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_SCORES, arrayOf("MAX($COLUMN_SCORE) AS highScore"), null, null, null, null, null)
        val highScore = if (cursor.moveToFirst()) cursor.getInt(cursor.getColumnIndexOrThrow("highScore")) else 0
        cursor.close()
        db.close()
        return highScore
    }
}
