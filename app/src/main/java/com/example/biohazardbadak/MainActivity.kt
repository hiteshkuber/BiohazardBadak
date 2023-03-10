package com.example.biohazardbadak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_container, CatFactFragment()).commit()
    }
}