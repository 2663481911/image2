package com.dataBinding.image.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dataBinding.image.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {
    lateinit var galleryFragmentBinding: FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryFragmentBinding = FragmentGalleryBinding.inflate(layoutInflater)
//        return inflater.inflate(R.layout.fragment_gallery, container, false)
        return galleryFragmentBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val galleryAdapter = GalleryAdapter()
//        val layoutManager = GridLayoutManager(requireContext(), 2)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        galleryFragmentBinding.recyclerView.apply {
            adapter = galleryAdapter
            this.layoutManager = layoutManager
        }

        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        //观察数据变化
        galleryViewModel.photoListLive.observe(this.viewLifecycleOwner, {
            galleryAdapter.submitList(it)
            if (galleryViewModel.isRefresh)
                galleryFragmentBinding.recyclerView.scrollToPosition(0)
            galleryFragmentBinding.gallerySwipe.isRefreshing = false
        })

        galleryViewModel.dataStatusLive.observe(this.viewLifecycleOwner, {
            galleryAdapter.footerViewStatus = it
            // 用于网络错误
            galleryAdapter.notifyItemChanged(galleryAdapter.itemCount - 1)
            if (it == DATA_STATUS_NETWORK_ERROR) galleryFragmentBinding.gallerySwipe.isRefreshing =
                false
        })


        galleryViewModel.photoListLive.value ?: galleryViewModel.getHtml(
            "http://www.cnu.cc/inspirationPage/recent-0?page=@page"
        )

        // 顶部刷新
        galleryFragmentBinding.gallerySwipe.setOnRefreshListener {
            galleryViewModel.getHtml("http://www.cnu.cc/inspirationPage/recent-0?page=@page", true)
        }

        // 底部刷新
        galleryFragmentBinding.recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
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