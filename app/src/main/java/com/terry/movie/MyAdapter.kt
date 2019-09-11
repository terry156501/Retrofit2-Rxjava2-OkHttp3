package com.terry.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.terry.retrofit2.R

class MyAdapter(mContext: Context, private var list: ArrayList<String>?) : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    private var context: Context? = mContext
    private var itemClickListener: IKotlinItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.movie_item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.text.text = list!![position]

        // 点击事件
        holder.itemView.setOnClickListener {
            itemClickListener!!.onItemClickListener(position)
        }

    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var text: TextView = itemView!!.findViewById(R.id.tv_movie_title)
    }

    fun setOnKotlinItemClickListener(itemClickListener: IKotlinItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface IKotlinItemClickListener {
        fun onItemClickListener(position: Int)
    }

}