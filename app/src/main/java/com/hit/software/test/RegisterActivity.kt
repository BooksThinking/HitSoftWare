package com.hit.software.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegisterActivity : Activity(), WebResponse {

    private var mainScope: CoroutineScope? = null
    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                Toast.makeText(applicationContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
            }
            if (msg.what == 1) {
                button_register.isEnabled = true
                Toast.makeText(applicationContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mainScope = MainScope()
        setRegisterButton()

    }

    private fun setRegisterButton() {
        button_register.setOnClickListener {
            button_register.isEnabled = false
            val request = "{\"user_name\":\""+editTextRegisterUsername.text.toString()+ "\",\"user_password\":\""+editTextRegisterPassword.text.toString()+"\"}"
            Log.d("Info", "发送注册请求: $request")
            val This = this
            mainScope!!.launch { WebRequest.post(This, "http://192.168.43.132:8080/mregister", request)}
        }
    }

    override fun onBackPressed() {
        mainScope!!.cancel()
        finish()
        super.onBackPressed()
    }

    /**
     * 回调方法
     */

    override fun requestSucceeded(content: String) {
        val registerResult = ProcessResponse.userRegister(content)
        if (registerResult == "success") {
            val mMessage = Message()
            mMessage.what = 0
            mMessage.obj = "注册成功"
            mHandler.sendMessage(mMessage)
            mainScope!!.cancel()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            Log.d("Warning", "响应出错: $content")
            val mMessage = Message()
            mMessage.what = 0
            mMessage.obj = "注册失败"
            mHandler.sendMessage(mMessage)
        }

    }

    override fun requestFailed() {
        val mMessage = Message()
        mMessage.what = 0
        mMessage.obj = "注册失败"
        mHandler.sendMessage(mMessage)
    }

}