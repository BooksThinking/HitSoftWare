package com.hit.software.test

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_review.*
import kotlinx.coroutines.cancel

class ReviewActivity : Activity() {

    private var QA: ArrayList<List<String>>? = null
    private var correctAnswers: ArrayList<Int>? = null
    private var index = 0
    private var total = 0
    private var answers: HashMap<Int, Int>? = null
    private val radioButtons: List<Int> = listOf(R.id.radioButton_a_review, R.id.radioButton_b_review, R.id.radioButton_c_review, R.id.radioButton_d_review)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        val bundle = intent.extras
        QA = bundle!!.getSerializable("qa") as ArrayList<List<String>>
        correctAnswers = bundle.getSerializable("ca") as ArrayList<Int>
        answers = bundle.getSerializable("answers") as HashMap<Int, Int>
        total = bundle.getSerializable("total") as Int
        button_pre_review.isEnabled = false
        textViewQuestionNumber_review.text = String.format("1/%d", total)
        for (radioButton in radioButtons) {
            findViewById<RadioButton>(radioButton).isEnabled = false
        }
        setNextButton()
        setPreButton()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    private fun setNextButton() {
        button_next_review.setOnClickListener {
            if (index == 0) {
                button_pre_review.isEnabled = true
            }
            else if (index == total - 2) {
                button_next_review.isEnabled = false
            }
            index++
            if (index < total) {
                textViewQuestionNumber_review.text = String.format("%d/%d", index + 1, total)
                writeQuestion()
                radioGroup_review.clearCheck()
                if (answers!![index] != null) {
                    findViewById<RadioButton>(radioButtons[answers!![index]!! - 1]).isChecked = true
                }
            }
        }
    }

    private fun setPreButton() {
        button_pre_review.setOnClickListener {
            if (index == 1) {
                button_pre_review.isEnabled = false
            }
            else if (index == total - 1) {
                button_next_review.isEnabled = true
            }
            index--
            textViewQuestionNumber_review.text = String.format("%d/%d", index + 1, total)
            writeQuestion()
            radioGroup_review.clearCheck()
            if (answers!![index] != null) {
                findViewById<RadioButton>(radioButtons[answers!![index]!! - 1]).isChecked = true
            }

        }
    }

    /**
     * 通过这个方法动态的更改控件之中的信息
     */
    private fun writeQuestion() {
        // 设置控件文本
        textViewQuestion_review.text = QA!![index][0]
        val options = listOf("A", "B", "C", "D")
        textViewShowAnswer.text = String.format("您的答案：%s    正确答案：%s", options[answers!![index]!! - 1], options[correctAnswers!![index] - 1])
        for (i in 0..3) {
            findViewById<RadioButton>(radioButtons[i]).text = String.format("%s. %s", options[i], QA!![index][i + 1])
        }
    }

}