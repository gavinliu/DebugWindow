package cn.gavinliu.android.lib.debugwindow

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View

class DebugWindow private constructor(application: Application) : ActivityLifecycleCallbacks, SensorEventListener, Contract.Presenter {

    private var mMainClazz: Class<out Activity>? = null

    private var mDebugLayoutId: Int = 0

    private var mViewBinder: ViewBinder? = null

    private val mFloatWindow: FloatingWindow = FloatingWindow(application)

    private val mSensorManager: SensorManager? = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    private val mSensor: Sensor? = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var mActivity: Activity? = null

    private val mApplication: Application = application

    interface ViewBinder {
        fun bindView(view: View)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DebugWindow? = null

        fun getInstance(application: Application): DebugWindow {
            if (instance == null) {
                synchronized(DebugWindow::class) {
                    if (instance == null) {
                        instance = DebugWindow(application)
                        application.registerActivityLifecycleCallbacks(instance)
                    }
                }
            }
            return instance!!
        }

        fun get(): DebugWindow {
            return instance!!
        }
    }

    fun bindMainActivity(clazz: Class<out Activity>): DebugWindow {
        mMainClazz = clazz
        return this
    }

    fun bindDebugLayout(id: Int): DebugWindow {
        mDebugLayoutId = id
        return this
    }

    fun bindView(viewBinder: ViewBinder) {
        mViewBinder = viewBinder
    }

    fun show() {
        mFloatWindow.show(mActivity)
    }

    fun hide() {
        mFloatWindow.hide()
    }

    override fun getDebugLayout(): Int {
        return mDebugLayoutId
    }

    override fun dispatchViewBind(view: View) {
        mViewBinder!!.bindView(view)
    }

    override fun onActivityResumed(activity: Activity?) {
        mActivity = activity
        if (mMainClazz!!.isInstance(activity)) {
            mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        mActivity = null
        if (mMainClazz!!.isInstance(activity)) {
            mSensorManager!!.unregisterListener(this)
            mFloatWindow.hide()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val type: Int = event!!.sensor.type

        if (type == Sensor.TYPE_ACCELEROMETER) {
            val x: Float = event.values[0]
            if (Math.abs(x) > 15) {
                mFloatWindow.setPresenter(this)
                mFloatWindow.show(mActivity)
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