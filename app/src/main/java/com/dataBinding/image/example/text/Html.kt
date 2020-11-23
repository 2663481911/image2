package com.dataBinding.image.example.text

import android.R.attr.data
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*


private fun getHtml(url: String, userAgent: String = ""): String? {
    val okHttpClient = OkHttpClient()
    val request: Request = Request.Builder()
        .url(url)
        .header("user-agent", userAgent)
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

class DataResponse{
    @SerializedName("data")
    val dataList:List<Data>? = null


}
class Data{

    @SerializedName("createdAt")
    val createdAt:String = ""

    @SerializedName("title")
    val title:String = ""

    @SerializedName("url")
    val url:String = ""

}

fun main() {
    val html = getHtml("https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/20")
//    println(html)
    for( i in Gson().fromJson(html, DataResponse::class.java).dataList!!){
        println(i.title + i.createdAt + i.url)
    }

}