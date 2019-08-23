package com.example.mr

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
        fab.setContentCoverColour(0xccffffff.toInt())
        fab.speedDialMenuAdapter = speedDialMenuAdapter

        fab.bringToFront()
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
                0 -> {
                    val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                    startActivity(nextIntent)
                }
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
}