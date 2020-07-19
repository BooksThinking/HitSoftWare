package com.hit.software.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
    }


    /**
     * 通过这个方法动态的更改控件之中的信息
     */
    fun write_question(question: String, candidates: List<String>){
        // 设置全部radioButton不可见
        val radioButtons: List<RadioButton> = listOf(radioButton_a, radioButton_b, radioButton_c, radioButton_d)
        for (radioButton in radioButtons) {
            radioButton.visibility = View.INVISIBLE
        }
        // 设置控件文本
        question_title.text = question
        for ((index, c) in candidates.withIndex()) {
            radioButtons[index].text = c
            // 设置radioButton可见
            radioButtons[index].visibility = View.VISIBLE
        }
    }

}