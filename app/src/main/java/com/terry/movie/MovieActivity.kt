@file:Suppress("DEPRECATION")

package com.terry.movie

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import `in`.srain.cube.views.ptr.header.StoreHouseHeader
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.terry.base.BaseActivity
import com.terry.bean.MovieSubject
import com.terry.retrofit2.MainActivity
import com.terry.retrofit2.R
import com.terry.utils.ToastUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper

/**
 * Created By Terry on 2019/9/10
 */
@Suppress("DEPRECATION")
class MovieActivity : BaseActivity(), MovieContract.IMovieView {
    private var mMoviePresenter: MoviePresenter? = null
    private var mProgressDialog: ProgressDialog? = null
    private var storeHousePtrFrame: PtrFrameLayout? = null
    private var load_more: TextView? = null//加载更多的按钮
    private var mButton:ImageView? = null


    override val curContext: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_t)
        mButton = findViewById(R.id.button)
        mButton?.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        mMoviePresenter = MoviePresenter(this)
        mMoviePresenter?.getMovie()
        initView()
    }

    /**
     * 初始化布局
     */
    private fun initView() {

        val rvMovieList = findViewById<RecyclerView>(R.id.rv_movie_list)
        initPtr()
        val layoutManager = LinearLayoutManager(this)
        rvMovieList?.layoutManager = layoutManager
        rvMovieList?.itemAnimator = DefaultItemAnimator()//设置动画
        rvMovieList?.adapter
        rvMovieList?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )//添加分割线
    }

    //下拉刷新
    private fun initPtr() {
        storeHousePtrFrame = findViewById(R.id.store_house_ptr_frame)
        storeHousePtrFrame?.resistance = 1.7f
        storeHousePtrFrame?.setRatioOfHeaderHeightToRefresh(1.2f)
        storeHousePtrFrame?.setDurationToClose(200)
        storeHousePtrFrame?.setDurationToCloseHeader(1000)
        storeHousePtrFrame?.isPullToRefresh = false
        storeHousePtrFrame?.isKeepHeaderWhenRefresh = true
        val header = StoreHouseHeader(this)
        val scale = resources.displayMetrics.density
        header.setPadding(0, (15 * scale + 0.5f).toInt(), 0, (15 * scale + 0.5f).toInt())
        header.initWithString("刷新")
        header.setTextColor(Color.RED)
        header.setBackgroundColor(Color.parseColor("#11000000"))
        storeHousePtrFrame?.headerView = header
        storeHousePtrFrame?.addPtrUIHandler(header)
        storeHousePtrFrame?.setPtrHandler(object : PtrHandler {
            override fun checkCanDoRefresh(frame: PtrFrameLayout, content: View, header: View): Boolean {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)
            }

            override fun onRefreshBegin(frame: PtrFrameLayout) {
                mMoviePresenter?.loadMoreMovie()//下拉刷新的时候加载更多数据
                frame.postDelayed(
                    { storeHousePtrFrame!!.refreshComplete() },
                    150
                )
            }
        })

    }

    override fun showBottom(lastIndex: Int) {
        val rvMovieList = findViewById<RecyclerView>(R.id.rv_movie_list)
        load_more?.text = "点击加载更多"
        rvMovieList?.scrollToPosition(lastIndex)
    }

    override fun showProgress() {
        mProgressDialog = ProgressDialog.show(this, "提示", "正在获取中,请稍后...")
    }

    override fun hideProgress() {
        mProgressDialog?.hide()
    }


    override fun showData(movies: List<MovieSubject.SubjectsBean>) {
        val commonAdapter =
            object : CommonAdapter<MovieSubject.SubjectsBean>(this, R.layout.movie_item, movies) {

                override fun convert(
                    holder: ViewHolder,
                    subjectsBean: MovieSubject.SubjectsBean,
                    position: Int
                ) {
                    val title =
                        (position + 1).toString() + "、" + subjectsBean.getTitle() + "/" + subjectsBean.getOriginal_title()
                    holder.setText(R.id.tv_movie_title, title)//设置电影名
                    var doc = ""
                    for (directorsBean in subjectsBean.getDirectors()) {
                        doc += directorsBean.getName() + "  "
                    }
                    holder.setText(R.id.tv_movie_doc, "导演:$doc")
                    var casts = ""
                    for (castsBean in subjectsBean.getCasts()) {
                        casts += castsBean.getName() + "  "
                    }
                    holder.setText(R.id.tv_movie_art, "主演:$casts")
                    var genres = ""
                    for (genre in subjectsBean.getGenres()) {
                        genres += "$genre "
                    }
                    holder.setText(R.id.tv_movie_type, subjectsBean.getYear() + " / " + genres)//年份+分级
                    holder.setText(R.id.tv_movie_grade, subjectsBean.getRating().getAverage().toString() + "")//评分
                    val iv_pic = holder.getView<ImageView>(R.id.iv_movie_pic)
                    Glide.with(mActivity)
                        .load(subjectsBean.getImages().getSmall())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_pic)//图片
                }
            }

        val mLoadMoreWrapper = LoadMoreWrapper<RecyclerView>(commonAdapter)
        val view = View.inflate(mActivity, R.layout.load_more, null)
        val mLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = mLayoutParams
        load_more = view.findViewById(R.id.tv_load_more) as TextView
        //监听点击加载更多事件
        load_more!!.setOnClickListener {
            load_more?.text = "加载中..."
            mMoviePresenter?.loadMoreMovie()
        }
        mLoadMoreWrapper.setLoadMoreView(view)
        val rvMovieList = findViewById<RecyclerView>(R.id.rv_movie_list)
        rvMovieList?.adapter = mLoadMoreWrapper
    }

    override fun showInfo(info: String) {
        ToastUtils.showToast(this, info)

    }

}
