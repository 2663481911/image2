package com.dataBinding.image.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.dataBinding.image.R
import com.dataBinding.image.model.HomeData
import kotlinx.android.synthetic.main.gallery_item.view.*


class GalleryAdapter : ListAdapter<HomeData, MyViewHolder>(DiffCallback) {
    companion object {
        const val NORMAL_VIEW_TYPE = 0
        const val FOOTER_VIEW_TYPE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = if (viewType == FOOTER_VIEW_TYPE)
            LayoutInflater.from(parent.context).inflate(R.layout.refresh_bar, parent, false)
                .also {
                    // 用于设置多行的时候居中
//                    it.layoutParams.width = parent.width
                    when(it.layoutParams){
                        is StaggeredGridLayoutManager.LayoutParams -> {
                            (it.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                        }
                        is GridLayoutManager.LayoutParams -> {
                            it.layoutParams.width = parent.width
                        }
                    }
                }
        else
            LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == itemCount - 1) {
            return
        }
        Glide.with(holder.itemView)
            .load(getItem(position).imgSrc)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.itemView.imageView)

        holder.itemView.imageView.setOnClickListener {
            val urList = ArrayList<String>()
            for (photoItem in currentList) {
                urList.add(photoItem.imgSrc)
            }
            Bundle().apply {
                putString("url", getItem(position).imgSrc)
                putInt("pos", position)
                putStringArrayList("urlList", urList)
                holder.itemView.findNavController().navigate(R.id.photoFragment, this)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<HomeData>() {
        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem.imgSrc == newItem.imgSrc
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            FOOTER_VIEW_TYPE
        } else
            NORMAL_VIEW_TYPE

    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


