package com.example.a4architectconstructor

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbox.*
import java.lang.NumberFormatException

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

        var treeOffsetSlider = findViewById<SeekBar>(R.id.tree_offset_slider)
        treeOffsetSlider.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //доступная ширина
                val availableWidth = (tree.parent as View).width
                //доступное смещение - мах разница между доступной шириной и шириной дерева
                val availableOffset = Math.max(0, availableWidth - tree.width)
                //меняем отступ слева у дерева
                (tree.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = availableOffset*progress/100
                tree.requestLayout()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val treeSize = findViewById<EditText>(R.id.tree_size)
        treeSize.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                try {
                    //новый размер, вводим число
                    val newSize = Integer.valueOf(s.toString())
                    //размер дерева
                    val lp = tree.getLayoutParams() as ViewGroup.MarginLayoutParams
                    lp.width = newSize
                    lp.height = (1.21 * lp.width).toInt()
                    tree.requestLayout()
                }
                catch (_:NumberFormatException) {
                }
            }

        })

        val doorOrientation = findViewById<RadioGroup>(R.id.radio_group_door_orientation)
        doorOrientation.setOnCheckedChangeListener{group, checkedIn ->
            if (checkedIn == R.id.radio_door_left)
                door.setImageResource(R.drawable.door_reverse)
            else if (checkedIn == R.id.radio_door_right)
                door.setImageResource(R.drawable.door)
        }

        //ВРЕМЯ
        time_picker.setOnTimeChangedListener{ picker, hour, minute ->
            changeDaytime(hour, minute)
        }

    }

    //количество этажей в доме
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

    //СМЕНА ДНЯ И НОЧИ
    private fun changeDaytime(hour: Int, minute: Int) {

        val dayTimeHour = hour + minute / 60f
        val dark = if (dayTimeHour < 3 || dayTimeHour > 21) {
            1f
        } else {
            Math.abs(dayTimeHour - 12) / 9
        }
        val light = 1 - dark
        val red = (0xff * light + 0x00 * dark).toInt()
        val green = (0xff * light + 0x18 * dark).toInt()
        val blue = (0xff * light + 0x3e * dark).toInt()
        val isDay = dayTimeHour > 6 && dayTimeHour <= 18
        val skyColor: Int = Color.rgb(red, green, blue)
        canvas.setBackgroundDrawable(ColorDrawable(skyColor))
        var roofWindowImage = R.drawable.roof_window
        var windowImage = R.drawable.window
        if (!isDay) {
            roofWindowImage = R.drawable.roof_window_night
            windowImage = R.drawable.window_night
        }

        roof_window.setImageResource(roofWindowImage)
        left_window.setImageResource(windowImage)
        right_window.setImageResource(windowImage)
        center_window.setImageResource(windowImage)
        left_window2.setImageResource(windowImage)
        right_window2.setImageResource(windowImage)
        left_window3.setImageResource(windowImage)
        right_window3.setImageResource(windowImage)
        center_window3.setImageResource(windowImage)
    }


}