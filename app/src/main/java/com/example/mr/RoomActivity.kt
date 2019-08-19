package com.example.mr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_room.*
import org.json.JSONObject


class RoomActivity : AppCompatActivity() {

    var msgAdapter: QuickAdapter? = null
    var my_intent: Intent? = null
    var selfID = "ABC001"
    var room_num = "1"

    var msgList = arrayListOf<Message>()
    var recyclerView: RecyclerView? = null
    var editWindow: EditText? = null
    var queue: RequestQueue? = null

    val bcReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("Firebase Serice", "broadcast get!!")
            chat_update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val auth = FirebaseAuth.getInstance()
        selfID = auth.currentUser!!.uid

        my_intent = intent

        queue = Volley.newRequestQueue(this)

        recyclerView = findViewById(R.id.recyclerChat)
        editWindow = findViewById(R.id.editWriteMessage)

        msgAdapter = QuickAdapter(msgList)
        msgAdapter!!.openLoadAnimation()
        msgAdapter!!.notifyDataSetChanged()

        recyclerView!!.adapter = msgAdapter
        var lm = LinearLayoutManager(this)
        lm.reverseLayout = true

        recyclerView!!.layoutManager = lm
        recyclerView!!.setHasFixedSize(true)

        chat_update()

        LocalBroadcastManager.getInstance(this).registerReceiver(bcReceiver, IntentFilter("doChatUpdate"))

        btnSend.setOnClickListener {
            var msgInput = editWindow!!.text.toString()
            if (msgInput == "") {
                return@setOnClickListener
            }

            val url = "http://164.125.234.241/upload.php?room_num=$room_num&sender=$selfID&msg=$msgInput"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->
                    if (response != "1")
                        Toast.makeText(this, "전송 실패", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { response ->
                    Toast.makeText(this, "전송 실패", Toast.LENGTH_LONG).show()
                }
            )

            queue!!.add(stringRequest)

            editWindow!!.setText("")

            chat_update()
        }
    }

    fun chat_update() {
        var last_time = ""
        if (msgList.size > 0)
            last_time = msgList[0].send_time.toString()

        val url = "http://164.125.234.241/select.php?last_time=$last_time"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var json = JSONObject(response)
                var data = json.getJSONArray("result")
                for (i in 0 until data.length()) {
                    var item = JSONObject(data.get(i).toString())

                    msgAdapter!!.addData(0, Message(item, selfID))
                }
                recyclerView!!.scrollToPosition(0)
            },
            Response.ErrorListener { response ->
                //textView.text = "That didn't work!"
            }
        )

        queue!!.add(stringRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bcReceiver)
    }
}
