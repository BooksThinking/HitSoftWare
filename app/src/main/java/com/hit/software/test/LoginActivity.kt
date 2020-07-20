package com.hit.software.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), WebResponse {

    private var mainScope: CoroutineScope? = null
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
            val request = "{\"user_name\":\""+editTextUsername.text.toString()+ "\",\"user_password\":\""+editTextPassword.text.toString()+"\"}"
            Log.d("Info", "发送登录请求: $request")
            val This = this
            mainScope!!.launch { WebRequest.post(This, "", request) }

        }
    }

    /**
     * 点击register按钮跳转到注册界面
     */
    private fun setGotoRegisterButton() {
        mainScope!!.cancel()
        val intent = Intent(this,RegistActivity::class.java)
        button_goto_register.setOnClickListener {
            startActivity(intent)
        }
    }

    /**
     * 回调方法
     */

    override fun requestSucceeded(content: String) {
        val loginResult = ProcessResponse.userLogin(content)
        if (loginResult == "success") {
            Toast.makeText(applicationContext, "登录成功", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            mainScope!!.cancel()
            finish()
        }
        else {
            Log.d("Warning", "响应出错: $content")
            Toast.makeText(applicationContext, "登录失败", Toast.LENGTH_SHORT).show()
        }

    }

    override fun requestFailed() {
        Toast.makeText(applicationContext, "登录失败", Toast.LENGTH_SHORT).show()
    }
}