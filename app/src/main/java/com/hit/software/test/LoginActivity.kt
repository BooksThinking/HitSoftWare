package com.hit.software.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        jumpRegist()
        jumpQuestion()
    }

    /**
     * 点击regist按钮跳转到注册界面
     */
    fun jumpRegist(){
        val intent = Intent(this,RegistActivity::class.java)
        button_regist.setOnClickListener({startActivity(intent)})
    }

    /**
     * 点击跳转到答题界面
     */
    fun jumpQuestion(){
        val intent = Intent(this,QuestionActivity::class.java)
        button_login.setOnClickListener({startActivity(intent)})
    }
}