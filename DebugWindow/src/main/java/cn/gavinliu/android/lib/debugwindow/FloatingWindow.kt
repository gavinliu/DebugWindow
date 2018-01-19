package cn.gavinliu.android.lib.debugwindow

import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

internal class FloatingWindow(context: Context) : Contract.View {

    private val mWindowManager: WindowManager? = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var mView: View? = null

    private var mPresenter: Contract.Presenter? = null

    override fun show(activity: Activity?) {
        if (mPresenter == null || activity == null || activity.window.decorView.windowToken == null) return

        if (mView == null) {
            mView = mLayoutInflater.inflate(mPresenter!!.getDebugLayout(), null, false)
            mPresenter!!.dispatchViewBind(mView!!)

            val layoutParams: WindowManager.LayoutParams = createLayoutParams()
            layoutParams.token = activity.window.decorView.windowToken
            mWindowManager!!.addView(mView, layoutParams)
        }
    }

    override fun hide() {
        if (mView != null) {
            mWindowManager!!.removeView(mView)
            mView = null
        }
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        mPresenter = presenter
    }

    private fun createLayoutParams(): WindowManager.LayoutParams {
        val windowLayoutParams = WindowManager.LayoutParams()
        windowLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
        windowLayoutParams.format = PixelFormat.RGBA_8888
        windowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        windowLayoutParams.flags = windowLayoutParams.flags or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        windowLayoutParams.alpha = 1.0f
        windowLayoutParams.gravity = Gravity.START or Gravity.TOP
        windowLayoutParams.x = 0
        windowLayoutParams.y = 0
        windowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        windowLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        return windowLayoutParams
    }

}