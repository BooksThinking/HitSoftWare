package com.hit.software.test

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

object WebRequest {
    private val mOkHttpClient = OkHttpClient()

    fun get(webResponse: WebResponse, url: String) {
        mOkHttpClient.newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
        mOkHttpClient.newCall(
            Request.Builder()
                .url(url)
                .build()
        ).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error", "网络请求失败")
                webResponse.errorMsg()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Info", "返回成功")
                webResponse.getResponse(response.body?.string().toString())
            }
        })
    }

    fun post(webResponse: WebResponse,
             url: String,
             postBody: String,
             contentType: String = "application/x-form-urlencoded") {
        mOkHttpClient.newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
        mOkHttpClient.newCall(
            Request.Builder()
                .url(url)
                .post(postBody.toRequestBody(("application/json; charset=utf-8").toMediaType()))
                .header("Content-Type", contentType)
                .build()
        ).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error", "网络请求失败")
                webResponse.errorMsg()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Info", "返回成功")
                webResponse.getResponse(response.body?.string().toString())
            }

        })
    }
}