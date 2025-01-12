package com.example.kartenapp_prototyp.data

import org.osmdroid.util.GeoPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.*

/**
 * Datenklasse für die Verwaltung von Wegpunkten
 */
data class Waypoint(
    val timestamp: Long,
    val longitude: Double,
    val latitude: Double,
    val route: Long
) {
    fun getTimestampAsDate(): String {
        val date = Date(timestamp)
        val timeFormat = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault())
        return timeFormat.format(date)
    }

    fun getTimeDifferenceInMillis(waypoint: Waypoint): Long {
        return abs(timestamp - waypoint.timestamp)
    }

    fun getDistanceInMeters(waypoint: Waypoint): Double {
        if (waypoint.latitude == latitude && waypoint.longitude == longitude) return 0.0

        val radius = 6371000.0

        val latitudeInRadians1 = Math.toRadians(latitude)
        val latitudeInRadians2 = Math.toRadians(waypoint.latitude)
        val longitudeInRadians1 = Math.toRadians(longitude)
        val longitudeInRadians2 = Math.toRadians(waypoint.longitude)

        val deltaLongitude = longitudeInRadians2 - longitudeInRadians1
        val deltaLatitude = latitudeInRadians2 - latitudeInRadians1

        val a = sin(deltaLatitude / 2).pow(2) +
                cos(latitudeInRadians1) * cos(latitudeInRadians2) *
                sin(deltaLongitude / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return radius * c
    }

    fun getGeoPoint(): GeoPoint {
        return GeoPoint(latitude, longitude)
    }
}