package com.dataBinding.image.setting

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

fun initSetting(context: Context, name: String="init.json"){
    val path  = context.getExternalFilesDir(null)?.path
    if (!File(path, name).exists()) {
        val initString = initRead(context, name)
        initSave(context, initString, name)
    }
}


fun initSave(context: Context, str: String, name: String){
    var outputStream: OutputStream?

    try {
        val path  = context.getExternalFilesDir(null)?.path
        outputStream = FileOutputStream(File(path, name))
        outputStream.write(str.toByteArray())
        path?.let { Log.d("file", it) }
    }catch (e: IOException){
        e.printStackTrace()
    }
}

fun initRead(context: Context, name:String):String{
    val str = StringBuffer()
    try {
        with(context.assets.open(name)) {
            val buffer = ByteArray(1024)
            var length: Int
            while (this.read(buffer).also { length = it } != -1) {
//                stream.write(buffer, 0, length)
                 str.append(String(buffer, 0, length))
            }
        }

    }catch (e: IOException){
        e.printStackTrace()
    }
    return str.toString()
}