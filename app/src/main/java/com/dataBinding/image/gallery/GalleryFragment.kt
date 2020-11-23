package com.dataBinding.image.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dataBinding.image.R
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val galleryAdapter = GalleryAdapter()
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.apply {
            adapter = galleryAdapter
//            layoutManager = GridLayoutManager(requireContext(), 2)
            this.layoutManager = layoutManager
        }

        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        galleryViewModel.photoListLive.observe(this.viewLifecycleOwner, Observer {
            galleryAdapter.submitList(it)
            if (galleryViewModel.isRefresh)
                recyclerView.scrollToPosition(0)
            gallery_swipe.isRefreshing = false
        }
        )


        galleryViewModel.photoListLive.value ?: galleryViewModel.getHtml(
            "http://www.cnu.cc/inspirationPage/recent-0?page=@page"
        )

        // 顶部刷新
        gallery_swipe.setOnRefreshListener {
//            galleryViewModel.getHtml("http://www.cnu.cc/inspirationPage/recent-0?page=@page", true)
        }

        // 底部刷新
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) return
                val intArray = IntArray(2)
                layoutManager.findLastVisibleItemPositions(intArray)
                if (intArray[0] == galleryAdapter.itemCount - 1) {
                    galleryViewModel.getHtml(
                        "http://www.cnu.cc/inspirationPage/recent-0?page=@page",
                        false
                    )
                }
            }
        })

    }

}