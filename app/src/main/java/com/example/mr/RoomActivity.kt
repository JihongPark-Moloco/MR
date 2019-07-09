package com.example.mr

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_room.*


class RoomActivity : AppCompatActivity() {

    var msgList = arrayListOf<Message>(
        Message("A001", "Hi", "190710-0100000"),
        Message("A002", "11", "190710-0100000"),
        Message("A001", "22", "190710-0100000"),
        Message("A002", "33", "190710-0100000"),
        Message("A003", "44", "190710-0100000"),
        Message("A003", "55", "190710-0100000"),
        Message("A003", "666", "190710-0100000"),
        Message("A003", "7777", "190710-0100000"),
        Message("A003", "88", "190710-0100000"),
        Message("A003", "9", "190710-0100000"),
        Message("A003", "100", "190710-0100000"),
        Message("A003", "111", "190710-0100000"),
        Message("A003", "222", "190710-0100000"),
        Message("A003", "333", "190710-0100000"),
        Message("A003", "444", "190710-0100000"),
        Message("A003", "555", "190710-0100000"),
        Message("A003", "666", "190710-0100000")
    )
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        recyclerView = findViewById(R.id.recyclerChat)

        val msgAdapter = QuickAdapter(R.layout.rc_item_message_user, msgList)
        msgAdapter.openLoadAnimation()

        recyclerView!!.adapter = msgAdapter
        var lm = LinearLayoutManager(this)
        lm.reverseLayout=true

        recyclerView!!.layoutManager = lm
        recyclerView!!.setHasFixedSize(true)
    }
}