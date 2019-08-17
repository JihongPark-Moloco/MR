package com.example.mr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_room.*
import org.json.JSONObject


class RoomActivity : AppCompatActivity() {

    var my_intent: Intent? = null
    val selfID = "ABC008"

    var msgList = arrayListOf<Message>(
    )
    var recyclerView: RecyclerView? = null
    var editWindow: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        my_intent = intent

        //var room_num = my_intent!!.getStringExtra("room_num")
        //var data_ref = database.getReference("Chat")

        recyclerView = findViewById(R.id.recyclerChat)
        editWindow = findViewById(R.id.editWriteMessage)

        val msgAdapter = QuickAdapter(msgList)
        msgAdapter.openLoadAnimation()
        msgAdapter.notifyDataSetChanged()

        recyclerView!!.adapter = msgAdapter
        var lm = LinearLayoutManager(this)
        lm.reverseLayout = true

        recyclerView!!.layoutManager = lm
        recyclerView!!.setHasFixedSize(true)

        val queue = Volley.newRequestQueue(this)
        val url = "http://164.125.234.241/do.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var json = JSONObject(response.replace("<pre>", "").replace("</pre>",""))
                var data = json.getJSONArray("result")
                for (i in 0 until data.length()) {
                    var item = JSONObject(data.get(i).toString())

                    msgAdapter.addData(0, Message(item, selfID))
                    recyclerView!!.scrollToPosition(0)
                }
            },
            Response.ErrorListener { response ->
                //textView.text = "That didn't work!"
            }
        )

        queue.add(stringRequest)


        btnSend.setOnClickListener {
            var msgInput = editWindow!!.text.toString()
            if (msgInput == "") {
                return@setOnClickListener
            }

            //data_ref.child("Room").child(room_num!!).push()
             //   .setValue(Message(selfID, msgInput, "time", selfID).makeMessageData())

            editWindow!!.setText("")
            recyclerView!!.scrollToPosition(0)
            Toast.makeText(this, msgInput, Toast.LENGTH_LONG).show()
        }
    }
}
