package com.example.kartenapp_prototyp.controller

import android.app.Activity
import android.widget.Button
import com.example.kartenapp_prototyp.R
import com.example.kartenapp_prototyp.api.WaypointDatabaseAPI
import com.example.kartenapp_prototyp.model.RouteModel
import com.example.kartenapp_prototyp.ui.view.ButtonDisplay
import kotlinx.coroutines.*

/**
 * Controller der alle Buttons steuert und auf ihre Aufrufe reagiert
 */
class ButtonController(
    private val activity: Activity
) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    /**
     * aktiviert einen Listener auf dem Aufnahme-Button
     */
    fun registerRecordButton() {
        val button: Button = activity.findViewById(R.id.button1)
        button.setOnClickListener {
            println("debugme - record Button wurde getippt")
            if (button.text.contains("aufnehmen")) {
                startRoute(button)
            } else {
                stopRoute(button)
            }
        }
    }

    private fun startRoute(button: Button) {

        ButtonDisplay.startRecordingButton(button)

        scope.launch {
            RouteModel.startRecording()
        }
    }

    private fun stopRoute(button: Button) {

        ButtonDisplay.stopRecordingButton(button)

        scope.launch {
            val route = RouteModel.stopRecording()
            val database = WaypointDatabaseAPI(activity)
            database.insertRoute(route)
        }
    }

    fun registerProfileButton() {

        val button: Button = activity.findViewById(R.id.button_to_profile)

        button.setOnClickListener {
            ButtonDisplay.moveToProfile(activity)
        }
    }

    fun registerMainButton() {

        val button: Button = activity.findViewById(R.id.button_to_main)

        button.setOnClickListener {
            ButtonDisplay.moveToMain(activity)
        }
    }
}