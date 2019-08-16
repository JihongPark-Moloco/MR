package com.example.mr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.markormesher.android_fab.FloatingActionButton
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setInternalOffsetBottom(40.0f) // pixels
        fab.setInternalOffsetRight(40.0f)
        fab.setButtonIconResource(R.drawable.ic_add)
        fab.setButtonBackgroundColour(0xffff9900.toInt())
        fab.setContentCoverColour(0xccffffff.toInt())
        fab.speedDialMenuAdapter = speedDialMenuAdapter

        map_button.setOnClickListener {
            val nextIntent = Intent(this@MainActivity, BusActivity::class.java)
            startActivity(nextIntent)
        }
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
            when(position) {
                0 -> {
                    val auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    val nextIntent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(nextIntent)
                    return true
                }
                else -> {
                    Toast.makeText(this@MainActivity, "$position is selected", Toast.LENGTH_LONG).show()
                    return true
                }
            }
        }

        override fun getBackgroundColour(position: Int): Int = when (position) {
            0 ->  Color.argb(255, 255, 255, 255)
            1 ->  Color.argb(255, 0, 0, 0)
            else -> Color.argb(255, 255, 255, 255)
        }

        override fun fabRotationDegrees(): Float = 135F

    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        System.runFinalization()
        System.exit(0)
    }
}