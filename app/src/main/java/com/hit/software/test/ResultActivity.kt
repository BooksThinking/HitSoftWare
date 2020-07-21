package com.hit.software.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity(), WebResponse {

    private var mainScope: CoroutineScope? = null
    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 0) {
                Toast.makeText(applicationContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
            }
            if (msg.what == 1) {
                button_reanswer.isEnabled = true
                Toast.makeText(applicationContext, msg.obj.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val bundle = intent.extras
        val correct = bundle!!.getSerializable("correct") as Int
        val total = bundle.getSerializable("total") as Int
        val score = correct / (total * 1.0)
        textViewScore.text = String.format("%d/%d", correct, total)
        when {
            score >= 0.8 -> textViewScore.setTextColor(android.graphics.Color.GREEN)
            score >= 0.6 -> textViewScore.setTextColor(android.graphics.Color.YELLOW)
            else -> textViewScore.setTextColor(android.graphics.Color.RED)
        }
        setReAnswerButton()
        setReviewButton(bundle)
    }

    override fun onBackPressed() {
        mainScope!!.cancel()
        finish()
        super.onBackPressed()
    }

    private fun setReAnswerButton() {
        button_reanswer.setOnClickListener {
            button_reanswer.isEnabled = false
            Log.d("Info", "请求问题及答案")
            val This = this
            mainScope!!.launch { WebRequest.get(This, "http://192.168.43.132:8080/")}
        }
    }

    private fun setReviewButton(bundle: Bundle) {
        button_review.setOnClickListener {
            mainScope!!.cancel()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            button_reanswer.isEnabled = true
        }
    }

    override fun requestSucceeded(content: String) {
        val result = ProcessResponse.getQA(content)
        if (result != null) {
            val QA = result.first
            val correctAnswers = result.second
            mainScope!!.cancel()
            val bundle = Bundle()
            bundle.putSerializable("qa", QA)
            bundle.putSerializable("ca", correctAnswers)
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        else {
            Log.d("Warning", "响应出错: $content")
            val mMessage = Message()
            mMessage.what = 1
            mMessage.obj = "获取题目失败"
            mHandler.sendMessage(mMessage)
        }

    }

    override fun requestFailed() {
        val mMessage = Message()
        mMessage.what = 1
        mMessage.obj = "获取题目失败"
        mHandler.sendMessage(mMessage)
    }
}