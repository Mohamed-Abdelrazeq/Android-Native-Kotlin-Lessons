package com.example.unit1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val me = intent.getSerializableExtra("Extra_Me") as Person


        println(me.toString())

        tvWelcome.text = "Welcome to The Second Activity ${me.name}"

        btnBackward.setOnClickListener {
             finish()
        }

    }
}