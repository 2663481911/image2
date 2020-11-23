package com.dataBinding.image.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.dataBinding.image.R
import kotlinx.android.synthetic.main.photo_view_item.view.*

class PagerPhotoAdapter(val urlList: List<String>): RecyclerView.Adapter<PagePhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagePhotoViewHolder {
        // 添加控件位置
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_view_item, parent, false)

        return PagePhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagePhotoViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(urlList[position])
            .into(holder.itemView.photoView)
    }


    override fun getItemCount(): Int {
        return urlList.size
    }
}

class PagePhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)