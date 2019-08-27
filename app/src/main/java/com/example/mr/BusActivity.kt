package com.example.mr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import uk.co.markormesher.android_fab.FloatingActionButton
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem


class BusActivity : AppCompatActivity(), OnMapReadyCallback {
    var season = -1
    var mapViewContainer: ViewGroup? = null
    var naverMap: NaverMap? = null
    var markerArray = mutableListOf<Marker>()
    var line: PathOverlay? = null

    companion object {
        var NORMAL_ON = 0
        var NORMAL_CAM_VAC = 1
        var NORMAL_ALL_VAC = 2
        var HOLI_ALL = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)


        val fab = findViewById<FloatingActionButton>(R.id.fab)
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

    private val speedDialMenuAdapter = object : SpeedDialMenuAdapter() {
        override fun getCount(): Int = 2

        override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem =
            when (position) {
                0 -> SpeedDialMenuItem(context, R.drawable.ic_send, "Logout")
                1 -> SpeedDialMenuItem(context, R.drawable.ic_contacts, "Item Two")
//            2 -> SpeedDialMenuItem(context, R.drawable.ic_done, "Item Three")
//            3 -> SpeedDialMenuItem(context, R.drawable.ic_cloud, "Item Four")
                else -> throw IllegalArgumentException("No menu item: $position")
            }

        override fun onMenuItemClick(position: Int): Boolean {
            when (position) {
                0 -> {
                    clearMap()
                    select_line()
                }
//                1 -> {
//                    var db = MyDatabase(this@BusActivity)
//                    var cur = db.LID
//                    do {
//                        var a = cur.getString(0)
//                        var b = cur.getString(1)
//                        var c = cur.getString(2)
//                        var d = cur.getString(3)
//                        Log.d("db", "$a  $b  $c  $d")
//                    } while (cur.moveToNext())
//                }
            }
            //Toast.makeText(this@BusActivity, "$position is selected", Toast.LENGTH_LONG).show()
            return true
        }

        override fun getBackgroundColour(position: Int): Int = when (position) {
            0 -> Color.argb(255, 255, 255, 255)
            1 -> Color.argb(255, 0, 0, 0)
            else -> Color.argb(0, 0, 0, 0)
        }

        override fun fabRotationDegrees(): Float = 135F

    }

    fun select_line() {
        when (season) {
            NORMAL_ON -> {
                val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                nextIntent.putExtra("season", NORMAL_ON)
                startActivityForResult(nextIntent, 2580)
            }
            NORMAL_CAM_VAC -> {
                val nextIntent = Intent(this@BusActivity, BusPopupActivity::class.java)
                nextIntent.putExtra("season", NORMAL_CAM_VAC)
                startActivityForResult(nextIntent, 2580)
            }
            NORMAL_ALL_VAC -> {
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
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                2580 -> {
                    Log.d("check", "##1")
                    drawPolyLine(
                        data!!.getStringExtra("FirstTime"),
                        data!!.getStringExtra("SecondTime"),
                        data!!.getStringExtra("Line")
                    )
                }
            }
        }
    }

    fun drawPolyLine(first_time: String, second_time: String, line_num: String) {
        var db = MyDatabase(this)
        var LineList = db.BusLine(first_time, second_time, line_num)

        Log.d("check", "##2")
        line = PathOverlay()
        line!!.coords = LineList
        line!!.patternImage = OverlayImage.fromResource(R.mipmap.arrow_path)
        line!!.patternInterval = 20
        line!!.width = 40
        line!!.outlineWidth = 0
        val cameraUpdate =
            CameraUpdate.scrollAndZoomTo(LatLng(35.47305871, 128.77226333), 11.5).animate(
                CameraAnimation.Easing, 1000
            )
        naverMap!!.moveCamera(cameraUpdate)
        line!!.map = naverMap
//        for (i in _1용성_정류장_노선) {
//            var marker = Marker()
//            marker.icon = OverlayImage.fromResource(R.drawable.bus_stop)
//            marker.position = i
//            marker.map = naverMap
//            marker.width = 60
//            marker.height = 60
//            marker.map = naverMap
//            markerArray.add(marker)
//        }
    }

    fun clearMap() {
        for (marker in markerArray) {
            marker.map = null
        }
        line!!.map = null
        markerArray.clear()
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
    }
}