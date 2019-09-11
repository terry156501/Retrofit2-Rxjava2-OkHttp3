package com.terry.retrofit2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.terry.api.Api
import com.terry.api.MovieService
import com.terry.bean.MovieSubject
import com.terry.movie.MovieActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList
/**
 * Created By Terry on 2019/9/7
 */
class MainActivity : AppCompatActivity() {


//    private var mList:ArrayList<MovieSubject.SubjectsBean>? = null
//    private lateinit var recyclerView: RecyclerView

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        recyclerView = findViewById(R.id.topfragment_recycleview)
//
//        val layoutManager = GridLayoutManager(this, 3)
//        recyclerView.setLayoutManager(layoutManager)
//
//        getTop250()
//    }
//
//
//
//    private fun getTop250() {
//
//        mList = ArrayList()
//
//        val baseUrl = Api.BASE_URL
//
//        val retrofit = Retrofit.Builder()
//            .client(OkHttpClient())
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//
//
//        val doubanApi:MovieService = retrofit.create<MovieService>(MovieService::class.java)
//
//        doubanApi.getTop250(0, 20)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<MovieSubject> {
//                override fun onSubscribe(d: Disposable) {
//                }
//
//
//                override fun onComplete() {
//                    Toast.makeText(this@MainActivity, "Top250的电影", Toast.LENGTH_SHORT).show()
//
//                }
//
//                override fun onError(e: Throwable) {
//                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onNext(top250: MovieSubject) {
//                    mList?.addAll(top250.getSubjects())
//                }
//
//
//            })
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_movie)
//        getTop250()
//        initView()
//    }
//
//
//    private fun initView(){
//        val mRecyclerView = findViewById<RecyclerView>(R.id.topfragment_recycleview)
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//        // layoutManager
//        mRecyclerView.layoutManager = layoutManager
//
//
//        // animation
//        mRecyclerView.itemAnimator = DefaultItemAnimator()
//        val adapter = MyAdapter(this, mList)
//        mRecyclerView.adapter = adapter
//        // itemClick
//        adapter.setOnKotlinItemClickListener(object : MyAdapter.IKotlinItemClickListener {
//            override fun onItemClickListener(position: Int) {
//                Toast.makeText(applicationContext, mList!![position].toString(), Toast.LENGTH_SHORT).show()
//            }
//        })
//    }


    private var tvTitle: TextView? = null
    private var ivTitleBack: ImageView? = null
    private var mButton:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mButton = findViewById(R.id.button)
        tvTitle?.text = "Top250"
        ivTitleBack?.visibility = View.GONE
        mButton?.setOnClickListener{
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
        }
    }
}
