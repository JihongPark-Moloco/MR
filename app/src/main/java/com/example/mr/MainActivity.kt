package com.example.mr

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.d("Hash key", something)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }

        val auth = FirebaseAuth.getInstance()
        Log.d("User_Id", auth.currentUser?.uid)
        Log.d("User_Id", auth.currentUser?.displayName)
        Log.d("User_Id", auth.currentUser?.email)

        profile.setOnClickListener {
            val nextIntent = Intent(this, BusActivity::class.java)
            nextIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(nextIntent)
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        System.runFinalization()
        System.exit(0)
    }
}