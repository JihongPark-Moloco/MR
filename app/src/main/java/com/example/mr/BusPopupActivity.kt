package com.example.mr

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BusPopupActivity : Activity(){
    var recyclerView: RecyclerView? = null
    var busAdapter: BusListAdapter? = null
    var button_cam_mr: TextView? = null
    var button_mr_cam: TextView? = null
    var bool_cam_mr = false

    var busList_mr_cam = arrayListOf(
        Bus("6:40", "6:50", "1용성"),
        Bus("7:05", "7:10", "칠성"),
        Bus("7:15", "7:25", "용성"),
        Bus("8:17", "8:27", "1칠성"),
        Bus("8:45", "8:55", "2부산대"),
        Bus("8:50","8:58","2부산대"),
        Bus("8:56","9:06","6부산대"),
        Bus("9:00","9:10","1-2부산대"),
        Bus("9:35","9:45","1부산대"),
        Bus("9:48","9:58","1부산대"),
        Bus("10:05","10:15","4부산대"),
        Bus("10:22","10:31","1용성"),
        Bus("11:24","11:34","7삼랑진"),
        Bus("11:48","11:58","1부산대"),
        Bus("12:34","12:44","7삼랑진"),
        Bus("12:50","13:00","2부산대"),
        Bus("13:36","13:46","2부산대"),
        Bus("14:17","14:27","2부산대"),
        Bus("14:34","14:44","7삼랑진"),
        Bus("15:00","15:10","1부산대"),
        Bus("15:24","15:34","3부산대"),
        Bus("15:40","15:50","1부산대")
    )
    var busList_cam_mr = arrayListOf(
        Bus("7:05","7:18","용성7"),
        Bus("7:20","7:32","칠성2"),
        Bus("7:40","7:56","용성2"),
        Bus("8:35","8:52","칠성2"),
        Bus("8:56","9:12","부산대1"),
        Bus("9:04","9:20","부산대7"),
        Bus("9:08","9:24","부산대2"),
        Bus("9:20","9:36","부산대1-2"),
        Bus("9:45","10:00","부산대1"),
        Bus("10:00","10:16","부산대7"),
        Bus("10:17","10:28","부산대2"),
        Bus("10:35","10:52","용성2"),
        Bus("12:00","12:16","부산대7-1"),
        Bus("12:20","12:35","삼랑진4"),
        Bus("13:04","13:20","부산대1"),
        Bus("13:25","13:44","삼랑진3"),
        Bus("13:52","14:08","부산대1"),
        Bus("14:40","14:56","부산대1"),
        Bus("15:20","15:32","부산대4-1"),
        Bus("15:28","15:44","삼랑진1"),
        Bus("15:44","16:00","부산대1"),
        Bus("15:56","16:12","부산대2"),
        Bus("16:18","16:28","부산대4-1"),
        Bus("17:00","17:20","칠성1-2"),
        Bus("17:15","17:30","부산대4-1"),
        Bus("17:40","17:56","용성2"),
        Bus("17:54","18:08","부산대7"),
        Bus("18:16","18:32","부산대7-1"),
        Bus("18:40","18:56","삼랑진7"),
        Bus("19:04","19:20","용성2"),
        Bus("19:10","19:24","부산대7-1"),
        Bus("19:50","20:05","부산대4"),
        Bus("20:02","20:18","칠성2"),
        Bus("20:20","20:30","부산대6"),
        Bus("20:50","21:07","부산대2"),
        Bus("21:15","21:30","부산대4-1"),
        Bus("21:30","21:40","밀양역(종점)"),
        Bus("22:08","22:24","부산대2"),
        Bus("22:40","23:00","칠성2"),
        Bus("23:10","23:20","부산대2")
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_bus_popup)

        button_mr_cam = findViewById(R.id.button_mr_cam)
        button_cam_mr = findViewById(R.id.button_cam_mr)

        recyclerView = findViewById(R.id.recycler_item_bus)

        busAdapter = BusListAdapter(busList_mr_cam)
        busAdapter!!.openLoadAnimation()
        busAdapter!!.notifyDataSetChanged()


        recyclerView!!.adapter=busAdapter

        var lm = LinearLayoutManager(this)

        recyclerView!!.layoutManager = lm
        recyclerView!!.setHasFixedSize(true)

        button_cam_mr!!.setOnClickListener {change_list(true)}
        button_mr_cam!!.setOnClickListener {change_list(false)}


    }

    fun change_list(cam_mr:Boolean){
        if (bool_cam_mr != cam_mr){
            if(cam_mr){
                busAdapter = BusListAdapter(busList_cam_mr)
                busAdapter!!.openLoadAnimation()
                busAdapter!!.notifyDataSetChanged()

                recyclerView!!.adapter=busAdapter

                button_mr_cam!!.setBackgroundColor(R.color.white!!)
                button_mr_cam!!.setTextColor(R.color.black!!)

                button_cam_mr!!.setBackgroundColor(R.color.red!!)
                button_cam_mr!!.setTextColor(R.color.white!!)
            } else {
                busAdapter = BusListAdapter(busList_mr_cam)
                busAdapter!!.openLoadAnimation()
                busAdapter!!.notifyDataSetChanged()

                recyclerView!!.adapter = busAdapter

                bool_cam_mr = !bool_cam_mr

                button_mr_cam!!.setBackgroundColor(R.color.red!!)
                button_mr_cam!!.setTextColor(R.color.white!!)

                button_mr_cam!!.setBackgroundColor(R.color.white!!)
                button_mr_cam!!.setTextColor(R.color.black!!)
            }
        }
    }
}