package com.example.kartenapp_prototyp.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.kartenapp_prototyp.controller.ButtonController
import com.example.kartenapp_prototyp.controller.MyMapController
import com.example.kartenapp_prototyp.controller.PermissionController
import com.example.kartenapp_prototyp.R
import com.example.kartenapp_prototyp.controller.LocationController
import com.example.kartenapp_prototyp.controller.RouteController
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

/**
 * Haupt-Steuerklasse welche die Controller initialisiert oder instanziiert und aufgerufen wird wenn die App startet und somit auch die View ins Leben ruft
 */
class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1

    private lateinit var permissionController: PermissionController
    private lateinit var locationController: LocationController

    private lateinit var myLocationOverlay: MyLocationNewOverlay


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ////////// HANDLE PERMISSIONS //////////
        permissionController = PermissionController(
            this,
            REQUEST_PERMISSIONS_REQUEST_CODE
        )

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        // Setting the View to the map
        setContentView(R.layout.main)

        ////////// MAP CONTROLLER //////////
        MyMapController.createMapController(this)

        ////////// WAYPOINT CONTROLLER //////////
        RouteController.createRouteController(this, MyMapController.map.getMap())

        ////////// LOCATION PROVIDER //////////

        ////////// LOCATION Controller //////////
        locationController = LocationController(this, permissionController)

        ////////// ROUTEN-AUFRUF //////////
        val currentRoute = intent.getLongExtra("CURRENT_ROUTE", -42L)
        if (currentRoute != -42L) {
            RouteController.loadRoute(currentRoute)
        }

        ////////// LOCATION-OVERLAY //////////
        myLocationOverlay = MyLocationNewOverlay(MyMapController.map.getMap())
        MyMapController.map.getMap().overlays.add(myLocationOverlay)

        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()

        ////////// CLICK-LISTENER //////////
        val buttonController = ButtonController(this)
        buttonController.registerRecordButton()
        buttonController.registerProfileButton()
    }

    /**
     * führt ales nötige aus, damit die App nach der Pause weiter läuft
     */
    override fun onResume() {
        super.onResume()
        if (PermissionController.checkLocationPermissions(this)) {
            locationController.startLocationUpdates()
        }
        MyMapController.map.getMap().onResume()
    }

    /**
     * führt alles nötige aus, damit die App korrekt pausiert und keine Hintergrunddienste ausführt
     */
    override fun onPause() {
        super.onPause()
        MyMapController.map.getMap().onPause()
        locationController.removeLocationUpdates()
    }

    /**
     * Überprüft, ob alle benötigten Berechtigungen erhalten wurden.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        for (i in grantResults.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permissions[i])
            }
        }
        if (permissionsToRequest.isEmpty()) {
            locationController.startLocationUpdates() // Ensure location updates if permissions granted
        } else {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }
}
