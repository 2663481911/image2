package com.dataBinding.image.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.dataBinding.image.analyzeRule.AnalyzeRule
import com.dataBinding.image.analyzeRule.RuleUtil
import com.dataBinding.image.model.HomeData
import com.dataBinding.image.model.Rule

const val DATA_STATUS_NETWORK_ERROR = 404
const val DATA_STATUS_LOAD_NORMAL = 200

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<HomeData>>()
    private var ruleUtil:RuleUtil? = null
    private val _dataStatusLive = MutableLiveData<Int>()

    val dataStatusLive:LiveData<Int>
        get() {
            return _dataStatusLive
        }

    val photoListLive:LiveData<List<HomeData>>
        get() = _photoListLive

    private var getVale = false
    private var pageNum = 1
    var isRefresh:Boolean = true


    fun getHtml(url: String, isTopRefresh:Boolean=true){
//      用于第一次加载把位置调到0
        if (_photoListLive.value == null)
            this.isRefresh = true
        else this.isRefresh = isTopRefresh

        if (ruleUtil == null){
            getRuleUtil()
        }

        // 用于防止加载多次
        if (getVale) return
        getVale = true

        StringRequest(
            Request.Method.GET,
            url.replace("@page", pageNum.toString()),
            {
                getVale = false

                pageNum += 1
                val homeDataList = ruleUtil?.getHomeDataList(it)

                if (!isTopRefresh) {       // 不是顶部刷新
                    val values = _photoListLive.value?.toMutableList()
                    for (value in homeDataList!!){
                       values?.add(value)
                   }
                    _photoListLive.value = values
                }
                else _photoListLive.value = homeDataList
                _dataStatusLive.value = DATA_STATUS_LOAD_NORMAL
            },
            {
                getVale = false
                _dataStatusLive.value = DATA_STATUS_NETWORK_ERROR
                it.printStackTrace()
            },
        ).also {
            VolleySingleton.getRequestQueue(getApplication()).add(it)
        }
    }

    private fun getUrl():String{
        return "https://gank.io/api/v2/data/category/Girl/type/Girl/page/${(0..5).random()}/count/20"
    }


    private fun getRuleUtil(){
        val rule = Rule()
        rule.homeList = ".work-thumbnail"
        rule.homeHref = "a@href"
        rule.homeSrc = "img@abs:src"
        rule.homeTitle = "div.title@text"

        rule.imagePageList = "#imgs_json@text"
        rule.imagePageSrc = "@json:$.[*]..img"
        rule.imageUrlReplaceByJS = "imgSrc = 'http://imgoss.cnu.cc/' + imgSrc;"
        ruleUtil = RuleUtil(rule, AnalyzeRule())
    }

}