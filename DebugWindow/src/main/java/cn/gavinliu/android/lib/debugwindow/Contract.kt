package cn.gavinliu.android.lib.debugwindow

import android.app.Activity

/**
 * Created by gavin on 2018/01/19.
 */
internal class Contract {

    internal interface Presenter {
        fun getDebugLayout(): Int

        fun dispatchViewBind(view: android.view.View)
    }

    internal interface View {

        fun setPresenter(presenter: Presenter)

        fun show(activity: Activity?)

        fun hide()
    }

}