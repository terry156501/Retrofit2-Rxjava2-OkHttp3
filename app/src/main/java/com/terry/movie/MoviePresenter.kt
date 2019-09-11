package com.terry.movie

import com.terry.base.OnHttpCallBack
import com.terry.bean.MovieSubject
import java.util.ArrayList

/**
 * Created By Terry on 2019/9/10
 */
class MoviePresenter(
    private var mIMovieView: MovieContract.IMovieView
) : MovieContract.IMoviePresenter {
    private var mIMovieModel: MovieContract.IMovieModel = MovieModel()
    var start = 0
    var count = 4
    internal var mMovies: MutableList<MovieSubject.SubjectsBean> = ArrayList()

    override fun getMovie() {
        mIMovieView.showProgress()
        //每次刷新加载4个
        mIMovieModel.getMovie(start, count, object : OnHttpCallBack<MovieSubject> {
            override fun onSuccessful(t: MovieSubject) {//获取电影信息成功了,返回movies对象
                mIMovieView.hideProgress()
                mMovies.addAll(t.getSubjects())
                mIMovieView.showData(mMovies)
                mIMovieView.showBottom(start - 5)
            }

            override fun onFaild(errorMsg: String) {
                mIMovieView.hideProgress()
                mIMovieView.showInfo(errorMsg)
            }
        })
        start += 4//改变请求的起点
    }

    /**
     * 加载更多
     */
    override fun loadMoreMovie() {
        getMovie()
    }
}
