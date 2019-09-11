package com.terry.base

/**
 * Created By Terry on 2019/9/10
 */
interface OnHttpCallBack<T> {
    fun onSuccessful(t: T) //成功了就回调这个方法,可以传递任何形式的数据给调用者

    fun onFaild(errorMsg: String) //失败了就调用这个方法,可以传递错误的请求消息给调用者
}
