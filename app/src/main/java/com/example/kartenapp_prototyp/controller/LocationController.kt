package com.example.kartenapp_prototyp.controller

import androidx.core.content.ContextCompat
import com.google.android.gms.location.Priority
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint

/**
 * Controller der den Standort verwaltet und mit der Standort API-kommuniziert
 */
class LocationController(
    private var activity: Activity,
    permissionController: PermissionController
) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    private var locationCallback: LocationCallback
    private var firstLocation: Boolean = true

        init {

            if (PermissionController.checkLocationPermissions(activity)) {
                getLastKnownLocation()
            } else {
                permissionController.requestLocationPermissions()
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {

                    if (locationResult.lastLocation != null) {
                        onMyLocationResult(locationResult.lastLocation!!)
                    }
                }
            }
        }

        fun removeLocationUpdates() {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }

        private fun getLastKnownLocation() {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            MyMapController.map.setCenter(GeoPoint(location.latitude, location.longitude))
                        } else {
                            Log.d("debugme", "Location is doof")
                        }
                    }
                    .addOnFailureListener{
                        Log.d("debugme","Failed to get Location: ${it.message}")
                    }
            }
        }

        fun startLocationUpdates() {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000)
                .setMinUpdateIntervalMillis(2000)     // Minimum wait time between updates
                .setWaitForAccurateLocation(false)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .build()

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, activity.mainLooper)
        }

        fun onMyLocationResult(location: Location) {
            val geoPoint = GeoPoint(location.latitude, location.longitude)
            RouteController.updateRoute(
                geoPoint,
                MyMapController.map.getMap()
            )
            if (firstLocation) {
                MyMapController.map.setCenter(geoPoint)
                firstLocation = false
            }
        }
}