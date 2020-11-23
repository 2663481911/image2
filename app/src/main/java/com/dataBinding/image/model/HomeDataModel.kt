package com.dataBinding.image.model

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList.OnListChangedCallback
import androidx.lifecycle.ViewModel
import com.dataBinding.image.adapter.HomeRecyclerViewAdapter
import com.dataBinding.image.analyzeRule.AnalyzeRule
import com.dataBinding.image.analyzeRule.RuleUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class HomeDataModel: ViewModel() {

    var homeDataList: ObservableArrayList<HomeData> = ObservableArrayList<HomeData>()
    lateinit var context: Context
    lateinit var rule: Rule
    lateinit var ruleUtil: RuleUtil
    var pageNum: Int = 0
    lateinit var adapter:HomeRecyclerViewAdapter

    fun init(context: Context, adapter: HomeRecyclerViewAdapter){
        this.context = context
        this.rule = readJson()[0]
        this.adapter = adapter
        this.ruleUtil = RuleUtil(rule, AnalyzeRule())
        adapterEvent()
    }

    fun adapterEvent() {
        homeDataList.addOnListChangedCallback(object :
            OnListChangedCallback<ObservableArrayList<HomeData>>(){
            override fun onChanged(sender: ObservableArrayList<HomeData>?) {
                adapter.notifyDataSetChanged();
            }

            override fun onItemRangeChanged(
                sender: ObservableArrayList<HomeData>?,
                positionStart: Int,
                itemCount: Int
            ) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            override fun onItemRangeInserted(
                sender: ObservableArrayList<HomeData>?,
                positionStart: Int,
                itemCount: Int
            ) {
                adapter.notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableArrayList<HomeData>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                TODO("Not yet implemented")
            }

            override fun onItemRangeRemoved(
                sender: ObservableArrayList<HomeData>?,
                positionStart: Int,
                itemCount: Int
            ) {
                TODO("Not yet implemented")
            }

        })
    }





    /**
     * 读取规则
     */
    private fun readJson():List<Rule>{
        val path = context.getExternalFilesDir(null)?.path
        val newStringBuilder = StringBuilder()
        var inputStream: InputStream? = null
        var isr: InputStreamReader? = null
        var reader: BufferedReader? = null
        try {
        inputStream = context.assets.open("rule.json")
            isr = InputStreamReader(inputStream)
            reader = BufferedReader(isr)
            var jsonLine: String?
            while (reader.readLine().also { jsonLine = it } != null) {
                newStringBuilder.append(jsonLine)
            }
        }catch (e: IOException) {
            e.printStackTrace()
        }finally {
            inputStream?.close()
            reader?.close()
            isr?.close()
        }

        val result = newStringBuilder.toString()
        val jsonArray = JSONArray(result)
        val typeOf = object : TypeToken<List<Rule>>() {}.type
        return Gson().fromJson(jsonArray.toString(), typeOf)
    }

    fun getHtml(url: String, userAgent: String = ""): String? {
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
//            .header("user-agent", userAgent)
            .build()
        val call: Call = okHttpClient.newCall(request)

        try {
            val response: Response = call.execute()
            return response.body?.string()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }




}