package com.example.mr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roughike.bottombar.BottomBar
import androidx.annotation.IdRes
import com.roughike.bottombar.OnTabSelectListener


class BusPopupActivity : Activity() {
    var recyclerView: RecyclerView? = null
    var busAdapter: BusListAdapter? = null
    var button_cam_mr: TextView? = null
    var button_mr_cam: TextView? = null
    var bool_cam_mr = true
    var bottomBar: BottomBar? = null
    var season = -1

    companion object {
        var NORMAL_ON = 0
        var NORMAL_CAM_VAC = 1
        var NORMAL_ALL_VAC = 2
        var HOLI_ALL = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_bus_popup)

        button_mr_cam = findViewById(R.id.button_mr_cam)
        button_cam_mr = findViewById(R.id.button_cam_mr)

        recyclerView = findViewById(R.id.recycler_item_bus)

        val intent = intent
        season = intent.getIntExtra("season", -1)

        change_list(false)

        button_cam_mr!!.setOnClickListener { change_list(true) }
        button_mr_cam!!.setOnClickListener { change_list(false) }
    }

    fun change_list(cam_mr: Boolean) {
        Log.d("bool", "cam_mr is $cam_mr")
        Log.d("bool", "bool_cam_mr is $bool_cam_mr")
        if (bool_cam_mr != cam_mr) {
            if (cam_mr) {
                var db = MyDatabase(this)
                var BusList = db.BusTime(season, cam_mr)
                busAdapter = BusListAdapter(BusList)
                busAdapter!!.openLoadAnimation()
                busAdapter!!.notifyDataSetChanged()
                busAdapter!!.setOnItemClickListener { adapter, view, position ->
                    Log.d("bool", "cam_mr is $position")
                    val intent = Intent()
                    intent.putExtra("FirstTime", BusList[position].first_time)
                    intent.putExtra("SecondTime", BusList[position].second_time)
                    intent.putExtra("Line", BusList[position].line_num)
                    setResult(RESULT_OK, intent)
                    finish()
                }

                recyclerView!!.adapter = busAdapter

                var lm = LinearLayoutManager(this)

                recyclerView!!.layoutManager = lm

                bool_cam_mr = !bool_cam_mr

                button_mr_cam!!.setBackgroundResource(R.color.white!!)
                // button_mr_cam!!.setTextColor(R.color.black!!)
                button_mr_cam!!.setTextColor(0xff000000.toInt())

                button_cam_mr!!.setBackgroundResource(R.color.red!!)
                //button_cam_mr!!.setTextColor(R.color.white!!)
                button_cam_mr!!.setTextColor(0xffffffff.toInt())
            } else {
                var db = MyDatabase(this)
                var BusList = db.BusTime(season, cam_mr)
                busAdapter = BusListAdapter(BusList)
                busAdapter!!.openLoadAnimation()
                busAdapter!!.notifyDataSetChanged()
                busAdapter!!.setOnItemClickListener { adapter, view, position ->
                    Log.d("bool", "cam_mr is $position")
                    val intent = Intent()
                    intent.putExtra("FirstTime", BusList[position].first_time)
                    intent.putExtra("SecondTime", BusList[position].second_time)
                    intent.putExtra("Line", BusList[position].line_num)
                    setResult(RESULT_OK, intent)
                    finish()
                }

                recyclerView!!.adapter = busAdapter

                var lm = LinearLayoutManager(this)

                recyclerView!!.layoutManager = lm

                bool_cam_mr = !bool_cam_mr

                button_mr_cam!!.setBackgroundResource(R.color.red!!)
                //button_mr_cam!!.setTextColor(R.color.white!!)
                button_mr_cam!!.setTextColor(0xffffffff.toInt())

                button_cam_mr!!.setBackgroundResource(R.color.white!!)
                //button_cam_mr!!.setTextColor(R.color.black!!)
                button_cam_mr!!.setTextColor(0xff000000.toInt())
            }
        }
    }
}