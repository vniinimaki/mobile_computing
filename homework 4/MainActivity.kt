package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = NotificationHandler(applicationContext)
        /*
        var isDark by mutableStateOf(false)

        val sensorService = LightSensor(applicationContext)

        sensorService.startListening()
        sensorService.setOnSensorValuesChangedListener { values ->
            val lux = values[0]
            Log.d(TAG, lux.toString())
            isDark = lux < 60f
        }

         */
        setContent {
            ComposeTutorialTheme {
                Navigation()
            }
        }



    }
}

