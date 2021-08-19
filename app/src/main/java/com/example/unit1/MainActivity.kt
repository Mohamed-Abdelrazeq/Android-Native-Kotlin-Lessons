package com.example.unit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.unit1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val name = "Mohamed"
        val age = 20
        val country = "Egypt"

        val me = Person(name,age, country)

        binding.btnForward.setOnClickListener {
            Intent(this,SecondActivity::class.java).also {
                it.putExtra("Extra_Me",me)
                it.putExtra("Extra_Name",name)
                startActivity(it)
            }
        }

    }

}