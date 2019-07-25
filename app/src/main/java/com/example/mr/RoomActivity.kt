package com.example.mr

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_room.*


class RoomActivity : AppCompatActivity() {

    var my_intent: Intent? = null
    val selfID = "ID001"

    var msgList = arrayListOf<Message>(
    )
    var recyclerView: RecyclerView? = null
    var editWindow: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        my_intent = intent

        var room_num = my_intent!!.getStringExtra("room_num")
        var database = FirebaseDatabase.getInstance()
        var data_ref = database.getReference("Chat")


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


        data_ref.child("Room").child(room_num).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var msgData = p0.getValue<MessageData>(MessageData::class.java)
                //Log.d("Coding", "Received!! " + msgData!!.text.toString())
                msgAdapter.addData(0, Message(msgData!!, selfID = selfID))
                recyclerView!!.scrollToPosition(0)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        btnSend.setOnClickListener {
            var msgInput = editWindow!!.text.toString()
            if (msgInput == "") {
                return@setOnClickListener
            }

            data_ref.child("Room").child(room_num).push()
                .setValue(Message(selfID, msgInput, "time", selfID).makeMessageData())

            editWindow!!.setText("")
            recyclerView!!.scrollToPosition(0)
            Toast.makeText(this, msgInput, Toast.LENGTH_LONG).show()
        }
    }
}
