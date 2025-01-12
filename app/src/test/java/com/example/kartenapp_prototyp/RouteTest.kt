package com.example.kartenapp_prototyp

import com.example.kartenapp_prototyp.data.Route
import com.example.kartenapp_prototyp.data.Waypoint
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RouteTest {

    val timestamp1: Long = 1000

    val timestamp2: Long = 4000

    val longitude1: Double = 44.00

    val longitude2: Double = 42.00

    val latitude1: Double = 10.00

    val latitude2: Double = 12.00

    val routeID: Long = 3L

    lateinit var list: MutableList<Waypoint>

    lateinit var route: Route

    lateinit var waypoint1: Waypoint

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

        list = mutableListOf<Waypoint>()
        list.add(waypoint1)
        list.add(waypoint2)

        route = Route(mock(), 3L)
    }

    @Test
    fun testDuration() {
        assertEquals(3000L, route.getDurationInMillis(list))
    }
}