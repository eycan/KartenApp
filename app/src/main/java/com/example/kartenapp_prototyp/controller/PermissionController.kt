package com.example.kartenapp_prototyp.controller

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Controller der alle Berechtigungen der App steuer und anfragt
 */
class PermissionController(
    private val activity: Activity,
    private val REQUEST_PERMISSIONS_REQUEST_CODE: Int
) {


    init {
        println("debugme - init")
        requestPermissionsIfNecessary(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    private fun requestPermissionsIfNecessary(permissions: Array<String>) {
        println("debugme - requestPermissionsIfNecessary")
        val permissionsToRequest = mutableListOf<String>()
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                activity,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    fun requestLocationPermissions() {
        println("debugme - requestLocationPermissions")
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    companion object {

        fun checkLocationPermissions(context: Context): Boolean {
            println("debugme - checkLocationPermissions")
            return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}