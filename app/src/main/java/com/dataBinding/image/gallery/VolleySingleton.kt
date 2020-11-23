package com.dataBinding.image.gallery

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class VolleySingleton  constructor(context: Context){
    companion object{
        private var INSTANCE:VolleySingleton ?= null

        fun getRequestQueue(context: Context):RequestQueue{
            INSTANCE?: synchronized(this) {
                VolleySingleton(context).also { INSTANCE = it }
            }
            return Volley.newRequestQueue(context)
        }
    }




}