package com.example.kartenapp_prototyp.controller

import android.app.Activity
import com.example.kartenapp_prototyp.R
import com.example.kartenapp_prototyp.data.Route
import com.example.kartenapp_prototyp.view.MainDisplay
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

/**
 * Controller der das Verhalten der Map steuert
 */
class MyMapController(
    private val activity: Activity,
) {
    private val map: MapView = activity.findViewById(R.id.map)
    private val mapController: IMapController = map.controller
    private var keepFocus: Boolean = true
    private var showMarker: Boolean = true

    init {
        map.setTileSource(TileSourceFactory.MAPNIK)
        mapController.setZoom(18)
    }

    fun getMap(): MapView {
        return map
    }

    fun setCenter(geoPoint: GeoPoint) {
        if (keepFocus) {
            mapController.setCenter(geoPoint)
        }
    }

    fun toggleFocus(focus: Boolean = !keepFocus) {
        keepFocus = focus
    }

    fun toggleMarker(point: GeoPoint, show: Boolean = !showMarker) {
        showMarker = show
    }

    fun displayStartStopMarker(route: Route) {
        if (!showMarker) return
        MainDisplay.displayStartStopMarker(activity, route)
    }

    companion object {
        lateinit var map: MyMapController

        fun createMapController(activity: Activity) {
            map = MyMapController(activity)
        }
    }

}
