package com.example.kartenapp_prototyp.controller

import android.app.Activity
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kartenapp_prototyp.model.RouteModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.osmdroid.util.GeoPoint
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationControllerTest {

    private lateinit var locationController: LocationController
    private lateinit var mockMapController: MyMapController
    private lateinit var mockPermissionController: PermissionController
    private lateinit var mockFusedLocationClient: FusedLocationProviderClient
    private lateinit var mockActivity: Activity

    @Before
    fun setup() {
        mockMapController = mockk(relaxed = true)
        mockPermissionController = mockk(relaxed = true)
        mockFusedLocationClient = mockk(relaxed = true)
        mockActivity = mockk(relaxed = true)

        mockkStatic(LocationServices::class)
        mockkStatic(ContextCompat::class)
        mockkStatic(ActivityCompat::class)

        every { LocationServices.getFusedLocationProviderClient(any<Activity>()) } returns mockFusedLocationClient

        locationController = LocationController(
            myMapController = mockMapController,
            activity = mockActivity,
            permissionController = mockPermissionController
        )
    }

    @After
    fun tearDown() {
        unmockkStatic(LocationServices::class)
        unmockkStatic(ContextCompat::class)
    }

    @Test
    fun testLocationPermissionGranted() {
        every { PermissionController.checkLocationPermissions(mockActivity) } returns true

        every { ContextCompat.checkSelfPermission( any(), any()) } returns PackageManager.PERMISSION_GRANTED

        locationController.startLocationUpdates()

        verify(atLeast = 1) {
            mockFusedLocationClient.requestLocationUpdates(any(), any<LocationCallback>(), any())
        }
    }

    @Test
    fun testLocationPermissionDenied() {
        every { PermissionController.checkLocationPermissions(mockActivity) } returns false
        every { ActivityCompat.checkSelfPermission( any(), any()) } returns PackageManager.PERMISSION_DENIED

        locationController = LocationController(
            myMapController = mockMapController,
            activity = mockActivity,
            permissionController = mockPermissionController
        )

        verify { mockPermissionController.requestLocationPermissions() }
    }

    @Test
    fun testResultUpdatesMapAndRoute() {
        val mockLocation = mockk<Location>()
        every { mockLocation.latitude } returns 42.13
        every { mockLocation.longitude } returns 13.420

        locationController.onMyLocationResult(mockLocation)

        verify {
            mockMapController.setCenter(GeoPoint(42.13, 13.420))
            RouteModel.updateRoute(GeoPoint(42.13, 13.420))
        }
    }
}