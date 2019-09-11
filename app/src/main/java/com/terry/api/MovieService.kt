package com.terry.api

import com.terry.bean.MovieSubject
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created By Terry on 2019/9/7
 */
interface MovieService {
    //获取豆瓣Top250 榜单
    @GET("top250")
    fun getTop250(
        @Query("start") start: Int,
        @Query("count") count: Int): Observable<MovieSubject>
}