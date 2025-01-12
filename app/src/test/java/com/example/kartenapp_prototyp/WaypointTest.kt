package com.example.kartenapp_prototyp

import com.example.kartenapp_prototyp.data.Waypoint
import junit.framework.TestCase.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class WaypointTest {

    val timestamp1: Long = 1000

    val timestamp2: Long = 4000

    val longitude1: Double = 44.00

    val longitude2: Double = 42.00

    val latitude1: Double = 10.00

    val latitude2: Double = 12.00

    val routeID: Long = 3L

    @Mock
    lateinit var waypoint1: Waypoint

    @Mock
    lateinit var waypoint2: Waypoint

    @Before
    fun setup() {
        waypoint1 = Waypoint(
            timestamp1,
            longitude1,
            latitude1,
            routeID
        )
        waypoint2 = Waypoint(
            timestamp2,
            longitude2,
            latitude2,
            routeID
        )
    }

    @Test
    fun testTimeDifference() {
        assertEquals(3000L, waypoint1.getTimeDifferenceInMillis(waypoint2))
    }

    @Test
    fun testDistance() {
        assertEquals(311000.0, waypoint1.getDistanceInMeters(waypoint2), 1000.0, "Leichte Tolleranz für Fehlberechnungen")
    }
}