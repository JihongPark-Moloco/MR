package com.example.mr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class roomNum : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonRoomEnter.setOnClickListener {
            val nextIntent = Intent(this, RoomActivity::class.java)
            nextIntent.putExtra("room_num", roomNum.text.toString())
            startActivity(nextIntent)
        }
    }
}
