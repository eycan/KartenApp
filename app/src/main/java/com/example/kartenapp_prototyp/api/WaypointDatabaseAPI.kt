package com.example.kartenapp_prototyp.api

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.kartenapp_prototyp.model.Route
import com.example.kartenapp_prototyp.model.Waypoint

/**
 * Schnittstelle zur Kommunikation mit der SQLite-Datenbank
 */
class WaypointDatabaseAPI(context: Context) {

    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)

    fun insertRoute(route: Route) {
        for (waypoint in route.getList()) {
            insertWaypoint(
                latitude = waypoint.latitude,
                longitude = waypoint.longitude,
                timestamp = waypoint.timestamp,
                route = route.id
                )
        }
    }

    private fun insertWaypoint(latitude: Double, longitude: Double, timestamp: Long, route: Long) {

        val db = databaseHelper.writableDatabase

        // create the ContentValues object
        val values = ContentValues().apply {
        put("longitude", longitude)
        put("latitude", latitude)
        put("timestamp", timestamp)
        put("route", route)
    }

    // insert the data into the table
    db.insert("waypoint", null, values)

    // close the database connection
    db.close()

    }

    fun getEverything() {

        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM waypoint ORDER BY route DESC LIMIT 1000", null)

        printCursorData(cursor)
    }

    fun printCursorData(cursor: Cursor) {
        if (cursor.moveToFirst()) {
            do {
                val columnNames = cursor.columnNames
                val columnValues = columnNames.map { columnName ->
                    val columnIndex = cursor.getColumnIndex(columnName)
                    if (columnIndex != -1) {
                        try {
                            // Attempt to read data as a string. Works for most types
                            cursor.getString(columnIndex)
                        } catch (e: Exception) {
                            "Non-string data"
                        }
                    } else {
                        "Column not found"
                    }
                }
                Log.d("debugme CursorData", "Row: ${columnValues.joinToString(separator = ", ")}")
            } while (cursor.moveToNext())
        } else {
            Log.d("CursorData", "No data found")
        }
        cursor.close()
    }

    fun getUniqueRouteNumber(): Long {

        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM waypoint ORDER BY route DESC LIMIT 1", null)

        val index = cursor.getColumnIndex("route")
        cursor.use {
            if (cursor.moveToFirst()) {
                if (index != -1) {
                    Log.d("debugme", " DATABASE if")
                    return cursor.getLong(index)
                }
                Log.d("debugme", "DATABASE no if")
            } else {
                Log.d("debugme", "DATABASE else")
            }
            return -1
        }
    }

    fun getAllRoutes(): MutableList<Waypoint> {
        val list = mutableListOf<Waypoint>()

        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM waypoint", null)

        cursor.use {

            val latitudeIndex = cursor.getColumnIndex("latitude")
            val longitudeIndex = cursor.getColumnIndex("longitude")
            val timestampIndex = cursor.getColumnIndex("timestamp")
            val routeIndex = cursor.getColumnIndex("route")

            if (latitudeIndex != -1 && longitudeIndex != -1 && timestampIndex != -1 && routeIndex != -1) {

                while (cursor.moveToNext()) {
                    val latitude = cursor.getDouble(latitudeIndex)
                    val longitude = cursor.getDouble(longitudeIndex)
                    val timestamp = cursor.getLong(timestampIndex)
                    val route = cursor.getLong(routeIndex)
                    list.add(
                        Waypoint(
                            longitude = longitude,
                            latitude = latitude,
                            timestamp = timestamp,
                            route = route
                        )
                    )
                }
            }
        }

        cursor.close()
        db.close()

        return list
    }

    fun getAllUniqueRoutes(): MutableList<Waypoint> {
        val list = mutableListOf<Waypoint>()

        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM waypoint GROUP BY route", null)

        cursor.use {

            val latitudeIndex = cursor.getColumnIndex("latitude")
            val longitudeIndex = cursor.getColumnIndex("longitude")
            val timestampIndex = cursor.getColumnIndex("timestamp")
            val routeIndex = cursor.getColumnIndex("route")

            if (latitudeIndex != -1 && longitudeIndex != -1 && timestampIndex != -1 && routeIndex != -1) {

                while (cursor.moveToNext()) {
                    val latitude = cursor.getDouble(latitudeIndex)
                    val longitude = cursor.getDouble(longitudeIndex)
                    val timestamp = cursor.getLong(timestampIndex)
                    val route = cursor.getLong(routeIndex)
                    list.add(
                        Waypoint(
                            longitude = longitude,
                            latitude = latitude,
                            timestamp = timestamp,
                            route = route
                        )
                    )
                }
            }
        }

        cursor.close()
        db.close()

        return list
    }

    fun getRoute(id: Long): MutableList<Waypoint> {
        val list = mutableListOf<Waypoint>()

        val db = databaseHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM waypoint WHERE route=${id}", null)

        cursor.use {

            val latitudeIndex = cursor.getColumnIndex("latitude")
            val longitudeIndex = cursor.getColumnIndex("longitude")
            val timestampIndex = cursor.getColumnIndex("timestamp")
            val routeIndex = cursor.getColumnIndex("route")

            if (latitudeIndex != -1 && longitudeIndex != -1 && timestampIndex != -1 && routeIndex != -1) {

                while (cursor.moveToNext()) {
                    val latitude = cursor.getDouble(latitudeIndex)
                    val longitude = cursor.getDouble(longitudeIndex)
                    val timestamp = cursor.getLong(timestampIndex)
                    val route = cursor.getLong(routeIndex)
                    list.add(
                        Waypoint(
                            longitude = longitude,
                            latitude = latitude,
                            timestamp = timestamp,
                            route = route
                        )
                    )
                }
            }
        }

        // close the cursor and database connection
        cursor.close()
        db.close()

        return list
    }

    fun delete(id: Int) {
        // get the writable database
        val db = databaseHelper.writableDatabase

        // delete the data from the table
        db.delete("waypoint", "id = ?", arrayOf(id.toString()))

        // close the database connection
        db.close()
    }
}