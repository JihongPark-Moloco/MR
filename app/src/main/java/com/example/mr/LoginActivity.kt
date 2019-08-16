package com.example.mr

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

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

        next.setOnClickListener {
            var email = email_input.text.toString()

            if (email.trim() == "") {
                Toast.makeText(this, "공백이 존재하면 안됩니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if("@" !in email){
                Toast.makeText(this, "도메인이 입력되지 않았습니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var domain = email.substring(email.lastIndexOf("@"))
            if (domain != "@pusan.ac.kr") {
                Toast.makeText(this, "부산대학교 메일만 사용가능합니다\nEX) email@pusan.ac.kr", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val nextIntent = Intent(this, SignInActivity::class.java)
            nextIntent.putExtra("email", email)
            startActivity(nextIntent)
        }

        signUp.setOnClickListener {
            val nextIntent = Intent(this, SignUpActivity::class.java)
            startActivity(nextIntent)
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        System.runFinalization()
        System.exit(0)
    }
}
