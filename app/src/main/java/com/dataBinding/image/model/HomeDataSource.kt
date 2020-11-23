package com.dataBinding.image.model

import android.app.VoiceInteractor
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest


class HomeDataSource : PageKeyedDataSource<Int, HomeData>() {

    lateinit var url:String
    // 加载这一次页面
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, HomeData>
    ) {
        StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {

            },
            Response.ErrorListener {


            }
        ).also {

        }
    }


    // 加载下一页
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, HomeData>) {
        TODO("Not yet implemented")
    }

    // 加载上一页
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, HomeData>) {
        TODO("Not yet implemented")
    }
}