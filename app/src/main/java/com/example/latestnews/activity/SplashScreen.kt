package com.example.latestnews.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.latestnews.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val thread = Thread(object : Runnable{
            override fun run() {
                Thread.sleep(1500)
                val intent = Intent(this@SplashScreen , MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
        thread.start()
    }
}