package cn.gavinliu.android.sample.debugwindow

import android.app.Application
import android.view.View
import android.widget.Button
import cn.gavinliu.android.lib.debugwindow.DebugWindow

/**
 * Created by gavin on 2018/01/19.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DebugWindow.getInstance(this)
                .bindDebugLayout(R.layout.debug)
                .bindMainActivity(MainActivity::class.java)
                .bindView(MyViewBinder())
    }

    class MyViewBinder : DebugWindow.ViewBinder {
        override fun bindView(view: View) {
            val close: Button = view.findViewById(R.id.close)
            close.setOnClickListener {
                DebugWindow.get().hide()
            }
        }
    }
}