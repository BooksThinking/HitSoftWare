package com.hit.software.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        write_question()
    }


    /**
     * 通过这个方法动态的更改控件之中的信息
     */
    fun write_question(){
        question_title.setText("")
    }

}