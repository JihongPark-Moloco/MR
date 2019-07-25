package com.example.mr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRoomEnter.setOnClickListener {
            val nextIntent = Intent(this, RoomActivity::class.java)
            nextIntent.putExtra("room_num", roomNum.text.toString())
            startActivity(nextIntent)
        }
/*
        val db = FirebaseFirestore.getInstance()

        // Create a new user with a first and last name
        var user = HashMap<String, Any>()
        user.put("first", "Ada")
        user.put("last", "Lovelace")
        user.put("born", 1815)

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception: java.lang.Exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
            }


        user = HashMap<String, Any>()
        user.put("first", "Alan")
        user.put("middle", "Mathison")
        user.put("last", "Turing")
        user.put("born", 1912)

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully uploaded to the database :) 2", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception: java.lang.Exception ->
                Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
            }
*/

        //val database = FirebaseDatabase.getInstance()
        //val myRef = database.getReference("message")

        //myRef.setValue("Hello, World!")
    }
}
