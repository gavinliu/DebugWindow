package cn.gavinliu.android.lib.debugwindow

import android.content.Context
import android.view.WindowManager

/**
 * Created by gavin on 2018/01/19.
 */
internal class FloatingWindow private constructor(context: Context) {

    private val mWindowManager: WindowManager? = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    companion object {
        private var instance: FloatingWindow? = null

        fun create(context: Context): FloatingWindow? {
            if (instance == null) {
                synchronized(FloatingWindow::class) {
                    if (instance == null) {
                        instance = FloatingWindow(context)
                    }
                }
            }
            return instance!!
        }

        fun get(): FloatingWindow? {
            return instance!!
        }
    }

    fun show() {

    }

    fun hide() {

    }

}