package com.example.myalarmapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myalarmapp.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private var chosenAudio: Uri? = null

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == Activity.RESULT_OK){
               chosenAudio =  it.data!!.data
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var chosenHour = 0
        var chosenMinute = 0
        val calendarDays = arrayOf(Calendar.MONDAY,Calendar.TUESDAY,Calendar.WEDNESDAY,
                                    Calendar.THURSDAY,Calendar.FRIDAY)

        binding.btnSelectRingtone.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "audio/*"
            }
            resultLauncher.launch(intent)


        }


        binding.btnSelect.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText(getString(R.string.time_picker_title))
                .build()

            picker.show(supportFragmentManager,"time_picker")

            picker.addOnPositiveButtonClickListener {
                chosenHour = picker.hour
                chosenMinute = picker.minute
                binding.tvSelectedTime.text = "Selected time is ${chosenHour}:${chosenMinute}"
                Calendar.MONDAY
            }
        }
        binding.btnSetAlarm.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE,"Random")
                putExtra(AlarmClock.EXTRA_HOUR,chosenHour)
                putExtra(AlarmClock.EXTRA_VIBRATE,binding.switcherVibrate.isEnabled)
                putExtra(AlarmClock.EXTRA_MINUTES,chosenMinute)
                putExtra(AlarmClock.EXTRA_DAYS,calendarDays)
                putExtra(AlarmClock.EXTRA_SKIP_UI,true)
                putExtra(AlarmClock.EXTRA_RINGTONE,chosenAudio)
            }

                startActivity(intent)

        }
    }

    val b = 9/0

}
