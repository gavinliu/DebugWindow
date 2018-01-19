package cn.gavinliu.android.sample.debugwindow

import android.app.Application
import cn.gavinliu.android.lib.debugwindow.DebugWindow

/**
 * Created by gavin on 2018/01/19.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DebugWindow.init(this).bindMainActivity(MainActivity::class.java)
    }

}