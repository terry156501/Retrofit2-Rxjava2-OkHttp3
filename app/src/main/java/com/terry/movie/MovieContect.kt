package com.terry.movie

import android.content.Context
import com.terry.base.OnHttpCallBack
import com.terry.bean.MovieSubject

/**
 * Created By Terry on 2019/9/10
 */
class MovieContract {
    /**
     * V层
     */
    interface IMovieView {

        val curContext: Context//获取上下文对象
        fun showBottom(lastIndex: Int)

        fun showProgress() //显示进度条

        fun hideProgress() //隐藏进度条

        fun showData(movies: List<MovieSubject.SubjectsBean>) //显示数据到View上

        fun showInfo(info: String)

    }

    /**
     * P层
     */
    internal interface IMoviePresenter {
        fun getMovie() //获取数据

        fun loadMoreMovie() //加载更多
    }

    /**
     * M层
     */
    internal interface IMovieModel {
        fun getMovie(start: Int, count: Int, callBack: OnHttpCallBack<MovieSubject>) //获取信息
    }
}
