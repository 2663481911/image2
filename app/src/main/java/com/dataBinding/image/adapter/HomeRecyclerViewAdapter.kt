package com.dataBinding.image.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dataBinding.image.R
import com.dataBinding.image.databinding.HomeDataRecyclerviewItemBinding
import com.dataBinding.image.model.HomeData


class HomeRecyclerViewAdapter() : RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder>() {

    var homeDataList: List<HomeData>? = null

    constructor(homeDataList: List<HomeData>):this(){
        this.homeDataList = homeDataList
    }

    fun setData(homeDataList: List<HomeData>){
        this.homeDataList = homeDataList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
//        val homeDataRecyclerviewItemBinding: HomeDataRecyclerviewItemBinding =
//            DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.home_data_recyclerview_item,
//                parent,
//                false
//            )
        val homeDataRecyclerviewItemBinding: HomeDataRecyclerviewItemBinding =
            HomeDataRecyclerviewItemBinding.inflate(layoutInflater, parent, false)

        return MyViewHolder(homeDataRecyclerviewItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val homeDAta = homeDataList?.get(position)
        homeDAta?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return homeDataList?.size!!
    }

    class MyViewHolder(private val homeDataRecyclerviewItemBinding: HomeDataRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(homeDataRecyclerviewItemBinding.root){

        fun bind(homeData: HomeData) {
            homeDataRecyclerviewItemBinding.homeData = homeData
            homeDataRecyclerviewItemBinding.executePendingBindings()
        }
    }


}