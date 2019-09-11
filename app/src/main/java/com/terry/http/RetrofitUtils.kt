package com.terry.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



/**
 * Created By Terry on 2019/9/10
 */
object RetrofitUtils {
    private val READ_TIMEOUT = 60//读取超时时间
    private val CONN_TIMEOUT = 12//连接超时时间

    private var mRetrofit: Retrofit? = null

    fun newInstence(url: String): Retrofit {
        mRetrofit = null
        val builder = OkHttpClient.Builder()
        builder.writeTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)//写操作超时时间
        builder.readTimeout(CONN_TIMEOUT.toLong(), TimeUnit.SECONDS)//读操作超时时间
        mRetrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return mRetrofit as Retrofit
    }


}
