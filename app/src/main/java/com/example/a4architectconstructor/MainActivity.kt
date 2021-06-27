package com.example.a4architectconstructor

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbox.*

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

        roof_window_checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->
            roof_window.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        var stage_picker = findViewById<NumberPicker>(R.id.stage_picker)
        stage_picker.minValue = 1
        stage_picker.maxValue = 3
        stage_picker.value = 3
        stage_picker.setOnValueChangedListener{picker, oldVal, newVal ->
            setStageCount(newVal)
        }

        
    }

    fun setStageCount(newVal: Int) {
        if (newVal == 1) {
            center_window3.visibility = View.GONE
            center_window.visibility = View.GONE
        }
        else if (newVal == 2) {
            center_window3.visibility = View.GONE
            center_window.visibility = View.VISIBLE
        }
        else {
            center_window3.visibility = View.VISIBLE
            center_window.visibility = View.VISIBLE
        }
    }
}