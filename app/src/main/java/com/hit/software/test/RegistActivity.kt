package com.hit.software.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_regist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegistActivity : AppCompatActivity(), WebResponse {

    private var mainScope: CoroutineScope? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        mainScope = MainScope()
        setRegisterButton()

    }

    private fun setRegisterButton() {
        button_register.setOnClickListener {
            val request = "{\"user_name\":\""+editTextRegisterUsername.text.toString()+ "\",\"user_password\":\""+editTextRegisterPassword.text.toString()+"\"}"
            Log.d("Info", "发送注册请求: $request")
            val This = this
            mainScope!!.launch { WebRequest.post(This, "", request)}
        }
    }

    /**
     * 回调方法
     */

    override fun requestSucceeded(content: String) {
        val registerResult = ProcessResponse.userRegister(content)
        if (registerResult == "success") {
            Toast.makeText(applicationContext, "注册成功", Toast.LENGTH_SHORT).show()
            mainScope!!.cancel()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else {
            Log.d("Warning", "响应出错: $content")
            Toast.makeText(applicationContext, "注册失败", Toast.LENGTH_SHORT).show()
        }

    }

    override fun requestFailed() {
        Toast.makeText(applicationContext, "注册失败", Toast.LENGTH_SHORT).show()
    }

}