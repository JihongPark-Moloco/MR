package com.example.mr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import net.daum.mf.map.api.MapView
import uk.co.markormesher.android_fab.FloatingActionButton
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem


class BusActivity : AppCompatActivity() {
    var season = -1

    companion object{
        var NORMAL_ON = 0
        var NORMAL_CAM_VAC = 1
        var NORMAL_ALL_VAC = 2
        var HOLI_ALL = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)

        val mapView = MapView(this)

        val mapViewContainer = findViewById<ViewGroup>(R.id.map_view)
        mapViewContainer.addView(mapView)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setInternalOffsetBottom(40.0f) // pixels
        fab.setButtonIconResource(R.drawable.ic_add)
        fab.setButtonBackgroundColour(0xffff9900.toInt())
        fab.setContentCoverColour(0xffffffff.toInt())
        fab.speedDialMenuAdapter = speedDialMenuAdapter

        fab.bringToFront()

        val intent = intent
        season = intent.getIntExtra("season", -1)

        select_line()
    }

    private val speedDialMenuAdapter = object: SpeedDialMenuAdapter() {
        override fun getCount(): Int = 2

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem = when (position) {
            0 -> SpeedDialMenuItem(context, R.drawable.ic_send, "Logout")
            1 -> SpeedDialMenuItem(context, R.drawable.ic_contacts, "Item Two")
//            2 -> SpeedDialMenuItem(context, R.drawable.ic_done, "Item Three")
//            3 -> SpeedDialMenuItem(context, R.drawable.ic_cloud, "Item Four")
            else -> throw IllegalArgumentException("No menu item: $position")
        }

        override fun onMenuItemClick(position: Int): Boolean {
            when(position){
                0 -> select_line()
            }
            //Toast.makeText(this@BusActivity, "$position is selected", Toast.LENGTH_LONG).show()
            return true
        }

        override fun getBackgroundColour(position: Int): Int = when (position) {
            0 ->  Color.argb(255, 255, 255, 255)
            1 ->  Color.argb(255, 0, 0, 0)
            else -> Color.argb(0, 0, 0, 0)
        }

        override fun fabRotationDegrees(): Float = 135F

    }

    fun select_line(){
        when(season){
            NORMAL_ON -> {
                val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                nextIntent.putExtra("season", NORMAL_ON)
                startActivityForResult(nextIntent, 2580)
            }
            NORMAL_CAM_VAC ->{
                val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                nextIntent.putExtra("season", NORMAL_CAM_VAC)
                startActivityForResult(nextIntent, 2580)
            }
            NORMAL_ALL_VAC ->{
                val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                nextIntent.putExtra("season", NORMAL_ALL_VAC)
                startActivityForResult(nextIntent, 2580)
            }
            HOLI_ALL -> {
                val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                nextIntent.putExtra("season", HOLI_ALL)
                startActivityForResult(nextIntent, 2580)
            }
            else -> finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                2580 -> Log.d("result", data!!.getStringExtra("line").toString())
            }

        }
    }
}