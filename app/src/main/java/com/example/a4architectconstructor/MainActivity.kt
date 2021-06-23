package com.example.a4architectconstructor

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var settings = findViewById<ImageView>(R.id.settings);
        var toolbox = findViewById<View>(R.id.toolbox)
        settings.setOnClickListener() {
            if (toolbox.visibility == View.GONE) {
                toolbox.visibility = View.VISIBLE
            }
            else {
                toolbox.visibility = View.GONE
            }
        }
    }
}