package com.example.composetutorial

abstract class SensorHandler(
    protected val sensorType: Int
){
    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null
    abstract val sensorExists: Boolean

    abstract fun startListening()
    abstract fun stopListening()

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit){
        onSensorValuesChanged = listener
    }
}