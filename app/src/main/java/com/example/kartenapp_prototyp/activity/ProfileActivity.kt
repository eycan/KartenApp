package com.example.kartenapp_prototyp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kartenapp_prototyp.controller.ButtonController
import com.example.kartenapp_prototyp.R
import com.example.kartenapp_prototyp.view.WaypointAdapter
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI

/**
 * Zweite Steuerklasse für die Profil-View
 */
class ProfileActivity : AppCompatActivity() {

    private lateinit var waypointRecyclerView: RecyclerView
    private lateinit var waypointAdapter: WaypointAdapter

    private lateinit var database: WaypointDatabaseAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        waypointRecyclerView = findViewById(R.id.recyclerView)
        waypointRecyclerView.layoutManager = LinearLayoutManager(this)


        ////////// RETRIEVE DATABASE-DATA //////////
        database = WaypointDatabaseAPI(this)
        val waypointList = database.getAllUniqueRoutes()

        ////////// DISPLAY DATABASE-DATA //////////
        waypointAdapter = WaypointAdapter(waypointList)
        waypointRecyclerView.adapter = waypointAdapter

        ////////// CLICK-LISTENER //////////
        val buttonController = ButtonController(this)
        buttonController.registerMainButton()
    }
}