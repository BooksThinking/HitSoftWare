package com.hit.software.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val bundle = intent.extras
        val correct = bundle!!.getSerializable("correct") as Int
        val total = bundle.getSerializable("total") as Int
    }
}