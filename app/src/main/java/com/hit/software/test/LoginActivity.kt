package com.hit.software.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginActivity : Activity(), WebResponse {

    private var mainScope: CoroutineScope? = null
    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                Toast.makeText(applicationContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
            }
            if (msg.what == 1) {
                button_login.isEnabled = true
                Toast.makeText(applicationContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mainScope = MainScope()
        setLoginButton()
        setGotoRegisterButton()
    }

    /**
     * 点击login按钮发送网络请求
     */
    private fun setLoginButton() {
        button_login.setOnClickListener {
            button_login.isEnabled = false
            val request = "{\"user_name\":\""+editTextUsername.text.toString()+ "\",\"user_password\":\""+editTextPassword.text.toString()+"\"}"
            Log.d("Info", "发送登录请求: $request")
            val This = this
            mainScope!!.launch { WebRequest.post(This, "http://192.168.43.132:8080/mlogin", request) }

        }
    }

    /**
     * 点击register按钮跳转到注册界面
     */
    private fun setGotoRegisterButton() {
        button_goto_register.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            button_login.isEnabled = true
        }
    }

    /**
     * 回调方法
     */

    override fun requestSucceeded(content: String) {
        val loginResult = ProcessResponse.userLogin(content)
        when (loginResult) {
            "success" -> {
                val mMessage = Message()
                mMessage.what = 0
                mMessage.obj = "登录成功"
                mHandler.sendMessage(mMessage)
                startActivity(Intent(this, MainActivity::class.java))
                mainScope!!.cancel()
                finish()
            }
            "用户名错误" -> {
                Log.d("Warning", "登录失败，用户名错误: $content")
                val mMessage = Message()
                mMessage.what = 1
                mMessage.obj = "登录失败，用户名不存在"
                mHandler.sendMessage(mMessage)
            }
            "密码错误" -> {
            Log.d("Warning", "登录失败，密码错误: $content")
            val mMessage = Message()
            mMessage.what = 1
            mMessage.obj = "登录失败，密码错误"
            mHandler.sendMessage(mMessage)
            }
            else -> {
                Log.d("Warning", "响应出错: $content")
                val mMessage = Message()
                mMessage.what = 1
                mMessage.obj = "登录失败"
                mHandler.sendMessage(mMessage)
            }
        }

    }

    override fun requestFailed() {
        val mMessage = Message()
        mMessage.what = 1
        mMessage.obj = "登录失败，请检查网络连接"
        mHandler.sendMessage(mMessage)
    }
}