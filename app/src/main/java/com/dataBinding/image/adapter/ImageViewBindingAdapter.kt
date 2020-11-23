package com.dataBinding.image.adapter

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dataBinding.image.R
import com.squareup.picasso.Picasso

abstract class ImageViewBindingAdapter {

    // 静态方法
    companion object{
        @JvmStatic
        @BindingAdapter("image")
        fun setImage(imageView:ImageView, imageUrl:String){
            imageUrl.let {
                Picasso.get()
                    .load(imageUrl)
                    .into(imageView)
            }
        }
    }
}