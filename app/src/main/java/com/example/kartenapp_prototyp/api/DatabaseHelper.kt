package com.example.kartenapp_prototyp.api

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Hilfsklasse um auf die Datenbank zuzugreifen
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "waypoint.db"
        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_POINT = """
        CREATE TABLE waypoint (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            timestamp INTEGER,
            longitude DOUBLE,
            latitude DOUBLE,
            route INTEGER
        )
    """
    }

    override fun onCreate(db: SQLiteDatabase) {
        // create the employee table
        db.execSQL(CREATE_TABLE_POINT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // drop the employee table if it exists
        db.execSQL("DROP TABLE IF EXISTS employee")

        // create the new employee table
        onCreate(db)
    }
}