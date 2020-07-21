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
            .callTimeout(6_000, TimeUnit.MILLISECONDS)
            .connectTimeout(6_000, TimeUnit.MILLISECONDS)
            .readTimeout(10_000, TimeUnit.MILLISECONDS)
            .writeTimeout(10_000, TimeUnit.MILLISECONDS)
            .build()
        mOkHttpClient.newCall(
            Request.Builder()
                .url(url)
                .build()
        ).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error", "网络请求失败")
                webResponse.requestFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Info", "返回成功")
                webResponse.requestSucceeded(response.body?.string().toString())
            }
        })
    }

    fun post(webResponse: WebResponse,
             url: String,
             postBody: String,
             contentType: String = "application/x-form-urlencoded") {
        Log.d("Info", url)
        mOkHttpClient.newBuilder()
            .callTimeout(6_000, TimeUnit.MILLISECONDS)
            .connectTimeout(6_000, TimeUnit.MILLISECONDS)
            .readTimeout(10_000, TimeUnit.MILLISECONDS)
            .writeTimeout(10_000, TimeUnit.MILLISECONDS)
            .build()
        mOkHttpClient.newCall(
            Request.Builder()
                .url(url)
                .post(postBody.toRequestBody(("application/json; charset=utf-8").toMediaType()))
                .header("Content-Type", contentType)
                .build()
        ).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("Error", "网络请求失败 $url")
                webResponse.requestFailed()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Info", "返回成功")
                webResponse.requestSucceeded(response.body?.string().toString())
            }

        })
    }
}