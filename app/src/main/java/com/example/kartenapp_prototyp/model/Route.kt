package com.example.kartenapp_prototyp.model

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

/**
 * Datenklasse für das erstellen und Verwalten von Routen
 */
data class Route(

    private val map: MapView,
    var id: Long

) {
    private val polyline: Polyline = Polyline(map)
    private val list: ArrayList<Waypoint> = ArrayList<Waypoint>()

    fun getList(): ArrayList<Waypoint> {
        return list
    }

    init {
        polyline.outlinePaint.strokeWidth = 5f
        polyline.outlinePaint.color = android.graphics.Color.BLUE
        map.overlayManager.add(polyline)
    }

    private fun addTooRoute(waypoint: Waypoint) {
        polyline.addPoint(waypoint.getGeoPoint())
        map.invalidate()
        list.add(waypoint)
    }

    fun updateRoute(point: GeoPoint) {
        addTooRoute(
            Waypoint(
            System.currentTimeMillis(),
            longitude = point.longitude,
            latitude = point.latitude,
            route = RouteModel.getCurrentRoute()?.id ?: -1
        )
        )
    }

    fun loadRoute(list: List<Waypoint>) {
        for (waypoint in list) {
            addTooRoute(waypoint)
        }
    }

    fun GetStartTimeAsString(): String {
        if (list.size < 1) return "UNAVAILABLE"
        return list[0].getTimestampAsDate()
    }

    fun getDistanceInMeter(): Double {
        var result = 0.0
        for(i in 2..<list.size) {
            result += list[i-1].getDistanceInMeters(list[i])
        }
        return result
    }

    fun getDurationInMillis(list: List<Waypoint>): Long {
        if (list.isEmpty()) return -1
        return list[0].getTimeDifferenceInMillis(list[list.size - 1])
    }

    fun getAverageSpeed(): Double {
        if (list.isEmpty()) return -1.0
        val time: Double = (getDurationInMillis(list).toDouble() / 1000) / 3600 // Millis to hour
        val distance: Double = getDistanceInMeter() / 1000 // Meter to kiloMeter
        return (distance / time)
    }
}