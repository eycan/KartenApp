package com.example.kartenapp_prototyp

import android.app.Activity
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI
import com.example.kartenapp_prototyp.model.RouteModel
import org.junit.Assert.assertNull
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.osmdroid.views.MapView

/**
 * Dieser Test soll überprüfen, ob der RouteController Routen korrekt bearbeitet
 */
@RunWith(MockitoJUnitRunner::class)
class RouteModelTest {

    // Mocks and objects
    private lateinit var mockActivity: Activity
    private lateinit var mockMapView: MapView
    private lateinit var mockDatabase: WaypointDatabaseAPI

    @Test
    fun getCurretRouteWithoutInitializing() {
        assertNull(RouteModel.getCurrentRoute())
    }

    @Test
    fun testAccessUninitializedRoute() {
        mockActivity = Mockito.mock(Activity::class.java)
        mockMapView = Mockito.mock(MapView::class.java)
        mockDatabase = Mockito.mock(WaypointDatabaseAPI::class.java)

        RouteModel.createRouteController(mockActivity, mockMapView, mockDatabase)

        try {
            RouteModel.route
            fail("Hier sollte eigentlich eine Exception auftreten")
        } catch (e: UninitializedPropertyAccessException) {

        }
    }
}