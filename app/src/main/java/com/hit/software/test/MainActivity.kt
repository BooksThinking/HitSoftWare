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

class MainActivity : AppCompatActivity(), WebResponse {

    private var mainScope: CoroutineScope? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainScope = MainScope()
        setStartButton()
    }

    fun setStartButton() {
        button_register.setOnClickListener {
            Log.d("Info", "请求问题及答案")
            val This = this
            mainScope!!.launch { WebRequest.get(This, "")}
        }
    }

    /**
     * 回调方法
     */

    override fun requestSucceeded(content: String) {
        val QA = ProcessResponse.getQA(content)
        if (QA != null) {
            mainScope!!.cancel()
            val bundle = Bundle()
            bundle.putSerializable("QA", QA)
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        else {
            Log.d("Warning", "响应出错: $content")
            Toast.makeText(applicationContext, "获取题目失败", Toast.LENGTH_SHORT).show()
        }

    }

    override fun requestFailed() {
        Toast.makeText(applicationContext, "获取题目失败", Toast.LENGTH_SHORT).show()
    }
}