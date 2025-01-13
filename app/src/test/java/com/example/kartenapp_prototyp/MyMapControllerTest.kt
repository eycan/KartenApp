package com.example.kartenapp_prototyp

import android.app.Activity
import android.view.View
import com.example.kartenapp_prototyp.controller.MyMapController
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
    lateinit var mockGeoPoint: GeoPoint

    @Mock
    lateinit var mockIMapController: IMapController

    @Mock
    lateinit var mockMyMapController: MyMapController

    @Before
    fun setup() {
        mockActivity = mock()
        mockIMapController = mock()
        mockMyMapController = mock()
        mockGeoPoint = mock()
    }

    /**
     * Es wird lediglich überprüft, ob die zuständige Methode des MapControllers aufgerufen wird
     */
    @Test
    fun testMapGetsCentered() {
        whenever(mockMapView.controller).thenReturn(mockIMapController)

        val myMapController = MyMapController(mockMapView)
        myMapController.setCenter(mockGeoPoint)

        verify(mockIMapController, times(1)).setCenter(mockGeoPoint)
    }


}