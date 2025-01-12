package com.example.kartenapp_prototyp

import android.app.Activity
import android.view.View
import com.example.kartenapp_prototyp.controller.MyMapController
import com.example.kartenapp_prototyp.data.Route
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.mockito.kotlin.*
import org.junit.Before
import org.junit.Test
import org.osmdroid.api.IMapController

@RunWith(MockitoJUnitRunner::class)
class MyMapControllerTest {

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

    @Before
    fun setup() {
        mockActivity = mock()
        mockIMapController = mock()
        mockMyMapController = mock()
        mockGeoPoint = mock()
    }

    @Test
    fun testMapGetsCentered() {

        whenever(mockActivity.findViewById<View>(any())).thenReturn(mockMapView)
        whenever(mockMapView.controller).thenReturn(mockIMapController)

        val myMapController = MyMapController(mockActivity)
        myMapController.setCenter(mockGeoPoint)

        verify(mockIMapController, times(1)).setCenter(mockGeoPoint)
    }


}