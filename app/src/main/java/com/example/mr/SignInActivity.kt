package com.example.mr

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_in.*


class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)



        var email = intent.extras!!.getString("email")

        back.setOnClickListener {
            finish()
        }

        signInButton.setOnClickListener {
            var passwd = passwd_input.text.toString().trim()

            if(passwd=="") {
                Toast.makeText(this, "공백이 존재하면 안됩니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email!!, passwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user = auth.currentUser
                        if (user!!.isEmailVerified) {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()
                            val nextIntent = Intent(this, MainActivity::class.java)
                            startActivity(nextIntent)
                        } else {
                            Toast.makeText(this, "이메일 인증을 완료해주세요", Toast.LENGTH_LONG).show()
                            auth.signOut()
                        }
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}