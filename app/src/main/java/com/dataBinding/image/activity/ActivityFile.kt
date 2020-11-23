package com.dataBinding.image.activity


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.dataBinding.image.R

import com.dataBinding.image.setting.initRead
import com.dataBinding.image.setting.initSetting

private val TAG = ActivityFile::class.java.name
class ActivityFile : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
//        val readInit = readInit(this)
//        textView.text = readInit
        button.setOnClickListener {
            initSetting(this, "rule.json")
        }

        button2.setOnClickListener {
            val readInit = initRead(this, "rule.json")
            textView.text = readInit
        }
        val path = getExternalFilesDir(null)?.path
        path?.let { Log.d(TAG, it) }

    }
}