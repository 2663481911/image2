package com.dataBinding.image.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.core.view.size
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.dataBinding.image.R
import kotlinx.android.synthetic.main.fragment_phono.*


class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phono, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val urlList:ArrayList<String> = arguments?.get("urlList") as ArrayList<String>

        PagerPhotoAdapter(urlList).also {
            viewPage.adapter = it
        }


        // 添加小圆点
        for(i in 0 until urlList.size){
            val imageView = ImageView(this.requireContext());
            imageView.layoutParams = ViewGroup.LayoutParams(20, 20)
            imageView.setPadding(20, 0, 20, 0)
            imageView.setBackgroundResource(R.drawable.indicator)
            indicator_view.addView(imageView)
        }

        // 改变小圆点
        viewPage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until  indicator_view.size){
                    indicator_view[i].setBackgroundResource(R.drawable.indicator)
                }
                indicator_view[position].setBackgroundResource(R.drawable.indicator_cur)
            }
        })

        viewPage.setCurrentItem(requireArguments().getInt("pos"), false)
    }

}