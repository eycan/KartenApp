package com.example.kartenapp_prototyp.view;

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.kartenapp_prototyp.R
import com.example.kartenapp_prototyp.controller.MyMapController
import com.example.kartenapp_prototyp.data.Route
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.util.Locale

/**
 * View-Klasse die UI-Veränderungen auf dem Haupt-Bildschirm(Kartenanzeige) vornimmt
 */
class MainDisplay {
    companion object {

        /**
         * Sorgt für die Anzeige der Strecke und Geschwindigkeit einer Route
         */
        fun displayRouteInformation(activity: Activity, route: Route) {
            val text: TextView = activity.findViewById(R.id.route_description_text)
            text.text = String.format(Locale.GERMANY, "Strecke: %.1f m | Geschwindigkeit: %.2f km/h", route.getDistanceInMeter(), route.getAverageSpeed())
        }

        /**
         * erstellt einen Start- und Stopmarker für eine Route
         */
        fun displayStartStopMarker(activity: Activity, route: Route) {
            if (route.getList().isEmpty()) return
            val list = route.getList()
            val start = list[0].getGeoPoint()
            val end = list[list.size - 1].getGeoPoint()
            displayMarker(
                activity,
                start,
                "Start",
                "Start der Route",
                ResourcesCompat.getDrawable(activity.resources, R.drawable.location_start, null)
            )
            displayMarker(
                activity,
                end, "Ende",
                "Ende der Route",
                ResourcesCompat.getDrawable(activity.resources, R.drawable.location_end, null)
            )
        }

        /**
         * erstellt einen Marker auf der karte
         */
        private fun displayMarker(activity: Activity, point: GeoPoint, title: String, desc: String, res: Drawable?) {
            val map = MyMapController.map.getMap()
            val marker = Marker(map)

            marker.position = point
            marker.title = "Titel"
            marker.snippet = "Beschreibung"
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            if (res is BitmapDrawable) {
                val originalBitmap = res.bitmap

                // Resize the bitmap
                val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 22, 44, true)  // New width and height

                val scaledDrawable = BitmapDrawable(activity.resources, scaledBitmap)
                marker.icon = scaledDrawable
            }

            // Add the marker to the map
            map.overlays.add(marker)
            map.invalidate() // Refresh the map
        }

    }
}
