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
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI
import com.example.kartenapp_prototyp.controller.LocationController
import com.example.kartenapp_prototyp.model.RouteModel
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

/**
 * Haupt-Steuerklasse welche die Controller initialisiert oder instanziiert und aufgerufen wird wenn die App startet und somit auch die View ins Leben ruft
 */
class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1

    private lateinit var permissionController: PermissionController
    private lateinit var locationController: LocationController
    private lateinit var myMapController: MyMapController

    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var database: WaypointDatabaseAPI

    private lateinit var map: MapView


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
        map = findViewById(R.id.map)
        myMapController = MyMapController(map)

        ////////// DATABASE //////////
        database = WaypointDatabaseAPI(this)

        ////////// WAYPOINT CONTROLLER //////////
        RouteModel.createRouteController(this, map, database)

        ////////// LOCATION PROVIDER //////////

        ////////// LOCATION Controller //////////
        locationController = LocationController(myMapController, this, permissionController)

        ////////// ROUTEN-AUFRUF //////////
        val currentRoute = intent.getLongExtra("CURRENT_ROUTE", -42L)
        if (currentRoute != -42L) {
            RouteModel.loadRoute(currentRoute)
        }

        ////////// LOCATION-OVERLAY //////////
        myLocationOverlay = MyLocationNewOverlay(map)
        map.overlays.add(myLocationOverlay)

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
        map.onResume()
    }

    /**
     * führt alles nötige aus, damit die App korrekt pausiert und keine Hintergrunddienste ausführt
     */
    override fun onPause() {
        super.onPause()
        map.onPause()
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
