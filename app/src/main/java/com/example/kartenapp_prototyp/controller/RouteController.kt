package com.example.kartenapp_prototyp.controller

import android.annotation.SuppressLint
import android.app.Activity
import com.example.kartenapp_prototyp.data.Route
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI
import com.example.kartenapp_prototyp.view.MainDisplay
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

/**
 * Controller der die aktuelle Route verwaltet
 */
class RouteController {

    companion object {

        /**
         * aktuelle Route
         */
        lateinit var route: Route

        /**
         * Activity des aktuellen Main-Screens
         */
        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        /**
         * Gibt die Map an, auf der Routen aktuell dargestellt werden
         */
        lateinit var map: MapView

        /**
         * Gibt an, ob aktuell eine Route aufgenommen wird oder nicht
         */
        private var recording: Boolean = false

        /**
         * Initialisiert wichtige statische Attribute für die Klasse
         */
        fun createRouteController(activity: Activity, map: MapView) {
            this.activity = activity
            this.map = map
        }

        /**
         * Gibt die aktuelle Route zurück oder null wenn keine Route vorhanden ist
         */
        fun getCurrentRoute(): Route? {
            if (Companion::route.isInitialized) {
                return route
            }
            return null
        }

        /**
         * überprüft ob aktuell eine Route aufgenommen wird, wenn ja, fügt die Methode
         * den aktuellen Wegpunkt der Route hinzu
         */
        fun updateRoute(geoPoint: GeoPoint, map: MapView) {
            if (recording) {
                if (Companion::route.isInitialized) {
                    route.updateRoute(geoPoint)
                } else {
                    newRoute()
                    route.updateRoute(geoPoint)
                }
            }
        }

        /**
         * startet das Aufnehmen einer Route
         */
        fun startRecording(map: MapView) {
            recording = true
            newRoute()
        }

        /**
         * Stoppt das aufnehmen einer Route die abgeschlossene Route zurück
         */
        fun stopRecording(): Route {
            recording = false
            return route
        }

        /**
         * ruft eine Route anhand der ID aus der Datenbank ab und beauftragt das UI mit der Darstellung
         */
        fun loadRoute(routeID: Long) {
            val database = WaypointDatabaseAPI(activity)
            route = Route(map, routeID)
            route.loadRoute(database.getRoute(routeID))
            MainDisplay.displayRouteInformation(activity, route)
            MainDisplay.displayStartStopMarker(activity, route)
        }

        /**
         * Gibt eine neue einzigartige Routen-ID zurück
         */
        private fun getUniqueRouteId(): Long {
            val database = WaypointDatabaseAPI(activity)
            val result = (database.getUniqueRouteNumber() + 1)
            return result
        }

        /**
         * Erstellt eine neue Route
         */
        fun newRoute() {
            val map = MyMapController.map.getMap()
            route = Route(map, getUniqueRouteId())
        }
    }
}