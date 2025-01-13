package com.example.kartenapp_prototyp.controller

import com.example.kartenapp_prototyp.model.Route
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

/**
 * Controller der das Verhalten der Map steuert
 */
class MyMapController(
    private val map: MapView
) {
    private val mapController: IMapController = map.controller
    private var keepFocus: Boolean = true
    private var showMarker: Boolean = true

    init {
        map.setTileSource(TileSourceFactory.MAPNIK)
        mapController.setZoom(18)
    }

    /**
     * Gibt die aktuelle Karte zurück
     */
    fun getMap(): MapView {
        return map
    }

    /**
     * Zentriert den eigenen Standort auf der Karte
     */
    fun setCenter(geoPoint: GeoPoint) {
        if (keepFocus) {
            mapController.setCenter(geoPoint)
        }
    }

    /**
     * Ändert ob bei einem Location-Update setCenter aufgerufen wird oder nicht
     */
    fun toggleFocus(focus: Boolean = !keepFocus) {
        keepFocus = focus
    }

    /**
     * Ändert ob die eigene Position auf der Karte angezeigt wird
     */
    fun toggleMarker(point: GeoPoint, show: Boolean = !showMarker) {
        showMarker = show
    }
}
