package com.hit.software.test

interface WebResponse {
    fun requestSucceeded(content:String)
    fun requestFailed()
}