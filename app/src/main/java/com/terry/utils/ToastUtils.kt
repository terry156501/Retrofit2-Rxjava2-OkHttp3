package com.terry.utils

import android.content.Context
import android.widget.Toast

/**
 * Created By Terry on 2019/9/10
 */
object ToastUtils {
    private var mToast: Toast? = null

    /**
     * 显示对话框
     *
     * @param content 要显示的内容
     */
    fun showToast(context: Context, content: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(content)
        }
        mToast!!.show()
    }
}
