package cn.gavinliu.android.lib.debugwindow

import android.app.Activity
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle

/**
 * Created by gavin on 2018/01/19.
 */
class DebugWindow private constructor(application: Application) : Application.ActivityLifecycleCallbacks, SensorEventListener {

    private var mMainClazz: Class<out Activity>? = null

    private val mSensorManager: SensorManager? = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    private val mSensor: Sensor? = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    companion object {
        @Volatile
        private var instance: DebugWindow? = null

        fun init(application: Application): DebugWindow {
            if (instance == null) {
                synchronized(DebugWindow::class) {
                    if (instance == null) {
                        instance = DebugWindow(application)
                        application.registerActivityLifecycleCallbacks(instance)
                        FloatingWindow.create(application)
                    }
                }
            }
            return instance!!
        }
    }

    fun bindMainActivity(clazz: Class<out Activity>) {
        mMainClazz = clazz
    }

    override fun onActivityResumed(activity: Activity?) {
        if (mMainClazz!!.isInstance(activity)) {
            mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        if (mMainClazz!!.isInstance(activity)) {
            mSensorManager!!.unregisterListener(this)
            FloatingWindow.get()!!.hide()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val type: Int = event!!.sensor.type

        if (type == Sensor.TYPE_ACCELEROMETER) {
            val x: Float = event.values[0]
            if (Math.abs(x) > 15) {
                FloatingWindow.get()!!.show()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }
}