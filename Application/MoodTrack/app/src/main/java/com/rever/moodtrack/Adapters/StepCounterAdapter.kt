package com.rever.moodtrack.Adapters
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelStoreOwner

class StepCounterAdapter: SensorEventListener {
    private var stepCount = 0
    private var availableSensor = true //Assumes everyone has sensors until proven otherwise


    fun startCount(sensorManager: SensorManager){
        val stepsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepsSensor != null)
            sensorManager.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        else
            availableSensor = false

    }
    fun getCurrentStep():String{
        if(!availableSensor)
            return "Not available"
        //Retract raw value to get todays value
        return stepCount.toString()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {

        stepCount = event.values[0].toInt()
    }
}