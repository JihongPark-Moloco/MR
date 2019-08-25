package com.example.mr

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class BusMainActivity :AppCompatActivity() {
    var button_normal_on: TextView? = null
    var button_normal_cam_vac: TextView? = null
    var button_normal_all_vac: TextView? = null
    var button_holi_all: TextView? = null

    companion object{
        var NORMAL_ON = 0
        var NORMAL_CAM_VAC = 1
        var NORMAL_ALL_VAC = 2
        var HOLI_ALL = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_main)

        button_normal_on = findViewById(R.id.button_normal_on)
        button_normal_cam_vac = findViewById(R.id.button_normal_cam_vac)
        button_normal_all_vac = findViewById(R.id.button_normal_all_vac)
        button_holi_all = findViewById(R.id.button_holi_all)

        button_normal_on!!.setOnClickListener {
            val nextIntent = Intent(this@BusMainActivity, BusActivity::class.java)
            nextIntent.putExtra("season", NORMAL_ON)
            startActivityForResult(nextIntent, 2580)
        }
        button_normal_cam_vac!!.setOnClickListener {
            val nextIntent = Intent(this@BusMainActivity, BusActivity::class.java)
            nextIntent.putExtra("season", NORMAL_CAM_VAC)
            startActivityForResult(nextIntent, 2580)
        }
        button_normal_all_vac!!.setOnClickListener {
            val nextIntent = Intent(this@BusMainActivity, BusActivity::class.java)
            nextIntent.putExtra("season", NORMAL_ALL_VAC)
            startActivityForResult(nextIntent, 2580)
        }
        button_holi_all!!.setOnClickListener {
            val nextIntent = Intent(this@BusMainActivity, BusActivity::class.java)
            nextIntent.putExtra("season", HOLI_ALL)
            startActivityForResult(nextIntent, 2580)
        }
    }
}