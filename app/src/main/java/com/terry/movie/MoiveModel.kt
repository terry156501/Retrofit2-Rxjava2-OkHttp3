package com.terry.movie

import android.util.Log
import com.terry.api.Api
import com.terry.api.MovieService
import com.terry.base.OnHttpCallBack
import com.terry.bean.MovieSubject
import com.terry.http.RetrofitUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created By Terry on 2019/9/10
 */
class MovieModel : MovieContract.IMovieModel {
    override fun getMovie(start: Int, count: Int, callBack: OnHttpCallBack<MovieSubject>) {
        val douban:Retrofit = RetrofitUtils.newInstence(Api.BASE_URL)
        douban
            .create(MovieService::class.java)
            .getTop250(start, count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(object : Observer<MovieSubject> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    if (e is HttpException) {
                        val httpException = e
                        val code = httpException.code()
                        if (code == 500 || code == 404) {
                            callBack.onFaild("服务器出错")
                        }
                    } else if (e is ConnectException) {
                        callBack.onFaild("网络断开,请打开网络!")
                    } else if (e is SocketTimeoutException) {
                        callBack.onFaild("网络连接超时!!")
                    } else {
                        callBack.onFaild("发生未知错误" + e.message)
                        Log.e("Tag",e.message)
                    }
                }

                override fun onNext(movies: MovieSubject) {
                    callBack.onSuccessful(movies)
                    Log.e("TAG",movies.toString())
                }
            })
    }
}
