package com.hit.software.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), WebResponse {

    val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setLoginButton()
        setRegisterButton()
    }

    /**
     * 点击login按钮发送网络请求
     */
    private fun setLoginButton() {
        button_register.setOnClickListener {
            val request = "{\"user_name\":\""+editTextUserName.text.toString()+ "\",\"user_password\":\""+editTextPassword.text.toString()+"\"}"
            Log.d("Info", "发送网络请求: $request")
            val This = this
            mainScope.launch { WebRequest.post(This, "", request) }

        }
    }

    /**
     * 点击register按钮跳转到注册界面
     */
    private fun setRegisterButton() {
        mainScope.cancel()
        val intent = Intent(this,RegistActivity::class.java)
        button_regist1.setOnClickListener {startActivity(intent)}
    }

    /**
     * 回调方法
     */

    override fun getResponse(content: String) {
        val loginResult = ProcessResponse.userLogin(content)
        if (loginResult == "success") {
            Toast.makeText(applicationContext, "登录成功", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, QuestionActivity::class.java))
//            mainScope.cancel()
        }
        else {
            Toast.makeText(applicationContext, "登录失败", Toast.LENGTH_SHORT).show()
        }

    }

    override fun errorMsg() {
        Toast.makeText(applicationContext, "登录失败", Toast.LENGTH_SHORT).show()
    }
}