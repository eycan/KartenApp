package com.example.kartenapp_prototyp.ui.view

import android.app.Activity
import android.content.Intent
import android.widget.Button
import com.example.kartenapp_prototyp.activity.MainActivity
import com.example.kartenapp_prototyp.activity.ProfileActivity

/**
 * View-Klasse die Verändung an den Buttons des UI vornimmt
 */
class ButtonDisplay {
    companion object {

        /**
         * Alle Veränderungen des GUI auf den start Aufnahme-Start
         */
        fun startRecordingButton(button: Button) {
            button.text = "Route stoppen"
        }

        /**
         * Alle Veränderungen des GUI auf den Aufnahme-Stop
         */
        fun stopRecordingButton(button: Button) {
            button.text = "Route aufnehmen"
        }

        /**
         * Anweisung die View zu zum Profil zu wechseln
         */
        fun moveToProfile(activity: Activity) {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivity(intent)
        }

        /**
         * Anweisung die View zu zu Main zu wechseln
         */
        fun moveToMain(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("CURRENT_ROUTE", -42)
            activity.startActivity(intent)
        }
    }
}