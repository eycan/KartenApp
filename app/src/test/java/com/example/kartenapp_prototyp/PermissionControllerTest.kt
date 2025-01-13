package com.example.kartenapp_prototyp.controller

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PermissionControllerTest {

    private lateinit var activity: Activity
    private lateinit var permissionController: PermissionController

    private val requestPermissionsCode = 100

    @Before
    fun setup() {
        activity = mockk(relaxed = true)
        permissionController = spyk(PermissionController(activity, requestPermissionsCode))
    }

    @Test
    fun testRequestPermissionOnDenied() {

        every { ActivityCompat.checkSelfPermission(activity, any()) } returns PackageManager.PERMISSION_DENIED

        permissionController.requestLocationPermissions()

        verify {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestPermissionsCode
            )
        }
    }

    @Test
    fun testCheckLocationPermissionsWhenGranted() {
        val context = mockk<Activity>()
        every { ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) } returns PackageManager.PERMISSION_GRANTED

        val result = PermissionController.checkLocationPermissions(context)

        assert(result)
    }

    @Test
    fun testCheckLocationPermissionWhenRefused() {
        val context = mockk<Activity>()
        every { ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) } returns PackageManager.PERMISSION_DENIED

        val result = PermissionController.checkLocationPermissions(context)

        assert(!result)
    }
}