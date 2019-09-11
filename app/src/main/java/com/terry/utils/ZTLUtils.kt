package com.terry.utils

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import com.terry.retrofit2.R

/**
 * Created By Terry on 2019/9/10
 */
class ZTLUtils(internal var activity: Activity) {

    fun setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            this.activity.window.addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
            // 透明导航栏
            this.activity.window.addFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )
            val tintManager = SystemStatusManager(this.activity)
            tintManager.setNavigationBarTintEnabled(true)
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R.color.colorPrimary)
            this.activity.window.decorView.fitsSystemWindows = true
        }
    }
}
