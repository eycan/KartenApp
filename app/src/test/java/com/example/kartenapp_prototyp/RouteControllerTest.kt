package com.example.kartenapp_prototyp

import android.app.Activity
import com.example.kartenapp_prototyp.controller.MyMapController
import com.example.kartenapp_prototyp.controller.RouteController
import com.example.kartenapp_prototyp.data.Route
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI
import junit.framework.TestCase.assertNull
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.mockito.kotlin.*
import org.junit.Test
import org.osmdroid.api.IMapController
import io.mockk.every
import io.mockk.mockkObject

/**
 * Dieser Test soll überprüfen, ob der RouteController Routen korrekt bearbeitet
 */
@RunWith(MockitoJUnitRunner::class)
class RouteControllerTest {

    // Mocks and objects
    @Mock
    lateinit var mockActivity: Activity

    @Mock
    lateinit var mockMapView: MapView

    @Mock
    lateinit var mockDatabase: WaypointDatabaseAPI

    @Mock
    lateinit var mockRoute: Route

    @Mock
    lateinit var mockGeoPoint: GeoPoint

    @Mock
    lateinit var mockIMapController: IMapController

     @Mock
    lateinit var mockMyMapController: MyMapController

    @Mock
    lateinit var mockRouteControllerCompanion: MapView

    @Test
    fun getCurretRouteWithoutInitializing() {
        assertNull(RouteController.getCurrentRoute())
    }

    @Test
    fun routeGetsCreatedOnUpdate() {
        RouteController.createRouteController(mockActivity, mockMapView)
        RouteController.startRecording(mockMapView)

        mockkObject(MyMapController.Companion)
        every { MyMapController.map } returns mockMyMapController
        whenever(mockMyMapController.getMap()).thenReturn(mockMapView)

        verify {
            RouteController.newRoute()
        }
    }
}