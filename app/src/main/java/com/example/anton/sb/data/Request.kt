package com.example.anton.sb.data

import android.util.Log
import java.net.URL

class Request(private val url: String) {

    fun run() {
        val addJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, addJsonStr)
    }

}